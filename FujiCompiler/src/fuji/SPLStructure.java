package fuji;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;

import AST.CompilationUnit;
import AST.Program;

/**
 * Represents the structure of an SPL, has full information about features,
 * roles and their relations.
 */
public class SPLStructure {

    /* Comment mark in the feature file. */
    private static final String FEATUREFILE_COMMENT = "\\s*#.*";

    /* File extension of the feature classes. */
    private static final Pattern SOURCE_FILE_EXT_PAT = Pattern.compile(
            ".*\\.java$", Pattern.CASE_INSENSITIVE);

    /* JastAddJ classpath option name. */
    public static final String JAJOPT_CP = "-" + Main.OptionName.CLASSPATH;

    private String basedirPathname; // canonical pathname

    /* Feature module pathenames in the order they are listed in features file. */
    private List<String> featureModulePathnames; // canonical pathnames

    private String[] classpathArg = new String[2];
    private Map<String, RoleGroup> roleGroups;
    private List<Collection<RoleGroup>> dependecyGraphs;

    /**
     * SPLStructure constructor.
     * 
     * @param basedirPathname
     *            absolute pathname of the SPL's root directory
     * @param featuresFilePathname
     *            absolute pathname of the features file.
     * @param singleDependencyGraph
     *            put all the role groups in one dependency graph regardless
     *            their actual interrelations.
     * @throws IOException
     * @throws FeatureDirNotFoundException
     * @throws SyntacticErrorException
     */
    public SPLStructure(String basedirPathname, String featuresFilePathname,
            boolean singleDependencyGraph) throws IOException,
            FeatureDirNotFoundException, SyntacticErrorException {

        this.basedirPathname = new File(basedirPathname).getCanonicalPath();
        featureModulePathnames = parseFeautresFile(this.basedirPathname,
                new File(featuresFilePathname).getCanonicalPath());
        classpathArg = constructClasspathArg(this.basedirPathname,
                featureModulePathnames);
        roleGroups = createRoleGroups(featureModulePathnames);
        if (singleDependencyGraph) {
            ArrayList<RoleGroup> graph = new ArrayList<RoleGroup>();
            for (RoleGroup rg : roleGroups.values()) {
                graph.add(rg);
            }
            dependecyGraphs = new ArrayList<Collection<RoleGroup>>();
            dependecyGraphs.add(graph);
        } else {
            dependecyGraphs = createDependecyGraphs(roleGroups);
        }
    }

    /**
     * Returns dependency graphs of the SPL. A dependency graph contains all the
     * role groups that are referencing or are being referenced by a role group
     * form the graph.
     * 
     * @return a list of dependency graphs ordered by the number of nodes (role
     *         groups). A graph with least nodes first.
     */
    public List<Collection<RoleGroup>> getDependencyGraphs() {
        return Collections.unmodifiableList(dependecyGraphs);
    }

    /**
     * Returns basedir absolute pathname.
     * 
     * @return basedir absolute pathname.
     */
    public String getBasedirPathname() {
        return basedirPathname;
    }

    /**
     * Returns feature module absolute pathnames of the SPL.
     * 
     * @return an unmodifiable list of strings representing canonical feature
     *         module pathnames of the SPL.
     */
    public List<String> getFeatureModulePathnames() {
        return Collections.unmodifiableList(featureModulePathnames);
    }

    /**
     * Determine feature ID for the absolute or relative path of a role
     * represented by the compilation unit.
     * 
     * @param cu
     *            compilation unit representing a role.
     * 
     * @return the feature ID of the role. -1 if there is no a corresponding
     *         feature module for the given compilation unit.
     * 
     * @throws IOException
     *             If an I/O error occurs, which is possible because the
     *             construction of the canonical pathname for the argument
     *             pathname may require filesystem queries.
     */
    public int determineFeatureID(CompilationUnit cu) throws IOException {

        /* Determine the pathname of the cu's feature module. */
        File cuFile = new File(cu.pathName());
        String cuPackageAndFile = cu.packageName();
        if (!cuPackageAndFile.isEmpty()) {
            cuPackageAndFile += File.separator;
        }
        cuPackageAndFile += cuFile.getName();
        String cuCanPathName = cuFile.getCanonicalPath();
        String cuFeatureModulePathname = cuCanPathName.substring(0,
                cuCanPathName.length() - (cuPackageAndFile.length() + 1));

        /*
         * Determine the index of the cu's feature module, which is the feature
         * ID of the cu.
         */
        for (int i = 0; i < featureModulePathnames.size(); ++i) {
            if (cuFeatureModulePathname.equals(featureModulePathnames.get(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Given a canonical pathname of an SPL's source file, determine if it is a
     * base role.
     * 
     * @param pathname
     *            canonical pathname of an SPL's source file.
     * @return <code>true</code> if the file is a base role, <code>false</code>
     *         otherwise.
     */
    public boolean isBaseRoleSourcefile(String pathname) {
        return roleGroups.keySet().contains(pathname);
    }

    /*
     * Parse the file containing the feature choice.
     * 
     * @param bdPathname canonical pathname to the SPL's root dir.
     * 
     * @param ffPathname absolute pathname to the .features file.
     * 
     * @return list of strings representing canonical pathnames of project's
     * feature directories.
     */
    private List<String> parseFeautresFile(String bdPathname, String ffPathname)
            throws FeatureDirNotFoundException, IOException {

        ArrayList<String> featureModulePathnames = new ArrayList<String>();
        ArrayList<String> notDirs = new ArrayList<String>();
        Pattern commentPattern = Pattern.compile(FEATUREFILE_COMMENT,
                Pattern.CASE_INSENSITIVE);
        BufferedReader in = new BufferedReader(new FileReader(ffPathname));
        String line;
        while ((line = in.readLine()) != null) {
            line = line.trim();
            if (commentPattern.matcher(line).matches() || line.isEmpty())
                continue;

            /* Check if the specified feature dir exists. */
            String featureModulePathname = bdPathname + File.separator + line;
            if (new File(featureModulePathname).isDirectory()) {
                featureModulePathnames.add(featureModulePathname);
            } else {
                notDirs.add(line);
            }
        }
        in.close();
        if (!notDirs.isEmpty()) {
            String message = "The following directoies specified in "
                    + ffPathname
                    + " do not exist in the project's base directory:\n";
            for (String s : notDirs) {
                message += s + "\n";
            }
            throw new FeatureDirNotFoundException(message);
        }
        if (featureModulePathnames.size() == 0) {
            throw new FeatureDirNotFoundException(
                    "Feature file does not specify feature direcotries.");
        }
        return featureModulePathnames;
    }

    /*
     * Construct classpath argument for initProgram()
     * 
     * @param bdPathname path to the SPL's root dir.
     * 
     * @param fmPathnames list paths to the SPL's feature modules.
     * 
     * @return the constructed classpath argument array.
     */
    private String[] constructClasspathArg(String bdPathname,
            List<String> fmPathnames) {

        String[] cpArg = new String[2];
        cpArg[0] = JAJOPT_CP;
        cpArg[1] = bdPathname;

        /*
         * NOTE: The order of feature module pathnames in the classpath is
         * important for the dependency calculation algorithm in
         * calculateDependencies() (see the NOTE their) and must correspond to
         * the order of features given in the features file. This condition is
         * satisfied by default due to the fmPathnames preserving the order of
         * features file entries.
         */
        for (String s : fmPathnames) {
            cpArg[1] += ":" + s;
        }
        return cpArg;
    }

    /*
     * Process all the .java files in the feature modules and organize roles in
     * role groups. A role group represents a base role and all its refinements
     * for the given feature selection.
     * 
     * @param fmPathnames canonical pathnames of the feature modules.
     * 
     * @return a map of the base role absolute pathname to the corresponding
     * role group.
     */
    private Map<String, RoleGroup> createRoleGroups(List<String> fmPathnames) {

        LinkedHashMap<String, RoleGroup> groups = new LinkedHashMap<String, RoleGroup>();
        for (String fmPathname : fmPathnames) {
            Stack<File> dirs = new Stack<File>();
            File startdir = new File(fmPathname);
            dirs.push(startdir);

            /* Process all the files in the subdirs of the startdir. */
            while (dirs.size() > 0) {
                for (File file : dirs.pop().listFiles()) {
                    if (file.isDirectory()) {
                        dirs.push(file);
                    } else if (SOURCE_FILE_EXT_PAT.matcher(file.getName())
                            .matches()) {
                        String filePathname = file.getPath();

                        /* file's pathname relative to the feature module dir. */
                        String baseSuffix = filePathname.substring(fmPathname
                                .length() + 1);
                        if (groups.containsKey(baseSuffix)) {
                            RoleGroup rg = groups.get(baseSuffix);
                            rg.refiningFeatureModules.add(fmPathname);
                        } else {
                            RoleGroup baseRG = new RoleGroup(fmPathname,
                                    filePathname);
                            groups.put(baseSuffix, baseRG);
                        }
                    }
                }
            }
        }
        LinkedHashMap<String, RoleGroup> returnGroups = new LinkedHashMap<String, RoleGroup>();
        for (RoleGroup rg : groups.values()) {
            returnGroups.put(rg.basePathname, rg);
        }
        return returnGroups;
    }

    /*
     * Creates dependency graphs for the given role groups. A dependency graph
     * contains all the role groups that are referencing or are being referenced
     * by a role group form the graph.
     * 
     * @param rgs role groups to create dependency graphs for.
     * 
     * @return a list of dependency graphs ordered by the number of nodes (role
     * groups). A graph with least nodes first.
     */
    private List<Collection<RoleGroup>> createDependecyGraphs(
            Map<String, RoleGroup> rgs) throws SyntacticErrorException {

        calculateDependencies(rgs);

        /* Sort role groups: RGs with less dependencies first. */
        List<RoleGroup> sortedRGs = new ArrayList<RoleGroup>(rgs.values());
        Collections.sort(sortedRGs, (new Comparator<RoleGroup>() {
            @Override
            public int compare(RoleGroup o1, RoleGroup o2) {
                return o1.dependencies.size() - o2.dependencies.size();
            }
        }));

        /* Create graphs. */
        List<Collection<RoleGroup>> graphs = new ArrayList<Collection<RoleGroup>>();
        for (RoleGroup rg : sortedRGs) {
            Collection<RoleGroup> aGraph = new ArrayList<RoleGroup>();
            aGraph.add(rg);
            for (String dep : rg.dependencies) {
                aGraph.add(rgs.get(dep));
            }
            graphs.add(aGraph);
        }
        return graphs;
    }

    /*
     * Calculates dependencies for the given role groups.
     * 
     * @param rgs a map from a base role absolute pathname to the corresponding
     * role group.
     */
    private void calculateDependencies(Map<String, RoleGroup> rgs)
            throws SyntacticErrorException {

        /* Compute transitive dependencies. */
        for (RoleGroup currentRG : rgs.values()) {
            Program program = initAST(); // TODO try to init once and reuse
            program.addSourceFile(currentRG.basePathname);
            for (String path : currentRG.calculateRefinementRelativePathnames())
                program.addSourceFile(path);
            @SuppressWarnings("unchecked")
            Iterator<CompilationUnit> iter = program.compilationUnitIterator();
            while (iter.hasNext()) {
                CompilationUnit currentCU = iter.next();
                if (currentCU.fromSource()) {
                    @SuppressWarnings("unchecked")
                    Collection parseErrors = currentCU.parseErrors();
                    if (!parseErrors.isEmpty()) {
                        StringBuilder message = new StringBuilder();
                        for (Object o : parseErrors) {
                            message.append(o + "\n");
                        }
                        throw new SyntacticErrorException(message.toString());
                    }

                    /*
                     * NOTE: This will load the source files for all the types
                     * reference in the current CU. The SPL's roles, for which
                     * the source files are loaded, are guaranteed to be the
                     * base roles. This is because the classpath contains
                     * pahtnames of the feature modules in the order they are
                     * given in features file (see constructClasspathArg()). So
                     * the classpath is searched in this order too and the base
                     * role of a class will be definitely found before any
                     * refinement role.
                     */
                    currentCU.toString();

                    /*
                     * currentCU.pathName() is the pathname of a _base_ role,
                     * see the NOTE above.
                     */
                    RoleGroup depRG = rgs.get(currentCU.pathName());
                    if (depRG != null
                            && !currentRG.basePathname
                                    .equals(depRG.basePathname)
                            && !currentRG.dependencies
                                    .contains(depRG.basePathname)) {
                        program.addSourceFileIfNew(depRG.basePathname);
                        for (String path : depRG
                                .calculateRefinementRelativePathnames()) {
                            program.addSourceFileIfNew(path);
                        }
                        currentRG.dependencies.add(depRG.basePathname);
                    }
                }
            }
        }

        /* Compute transitive dependencies. */
        // TODO remove this
        // for (RoleGroup currentRG : rgs.values()) {
        // currentRG.tDependencies.addAll(currentRG.dDependencies);
        // int setSize;
        // do {
        // setSize = currentRG.tDependencies.size();
        // // TODO iterates over the whole collection each time. Optimize!
        // ArrayList<String> tmpCol = new ArrayList<String>();
        // for (String path : currentRG.tDependencies) {
        // tmpCol.addAll(rgs.get(path).dDependencies);
        // }
        // currentRG.tDependencies.addAll(tmpCol);
        // } while (setSize != currentRG.tDependencies.size());
        // }
    }

    /*
     * Initialize an AST.
     */
    private Program initAST() {
        Program program = new Program();
        program.state().reset();
        program.initBytecodeReader(new AST.BytecodeParser());
        program.initJavaParser(new AST.JavaParser() {
            public CompilationUnit parse(java.io.InputStream is, String fileName)
                    throws java.io.IOException, beaver.Parser.Exception {
                return new parser.JavaParser().parse(is, fileName);
            }
        });
        AST.Options options = program.options();
        options.initOptions();
        options.addKeyValueOption(JAJOPT_CP);
        program.options().addOptions(classpathArg);
        return program;
    }

    /**
     * RoleGroup encapsulates a base role and all its refinement roles, provides
     * some utility methods.
     */
    public class RoleGroup {
        /* Canonical pathname of the feature module containing the base role. */
        private String baseFeatureModulePathname;

        /* Canonical pathname of the base role. */
        private String basePathname;

        /*
         * A list of the canonical pathnames of the feature modules containing
         * refinement roles.
         */
        private List<String> refiningFeatureModules = new ArrayList<String>();

        /*
         * Role groups that are referenced (directly or indirectly) in the code
         * of this role group (i.e. this role gruop's direct and transitive
         * dependencies).
         */
        private HashSet<String> dependencies = new HashSet<String>();

        /**
         * Flag: true, if a compilation unit for the role group has been
         * composed already.
         */
        public boolean composed = false;

        /**
         * RoleGroup constructor.
         * 
         * @param baseFMPathname
         *            canonical pathname of the feature module of the base role.
         * @param base
         *            canonical pathname of the base role.
         */
        public RoleGroup(String baseFMPathname, String base) {
            baseFeatureModulePathname = baseFMPathname;
            basePathname = base;
        }

        /**
         * Returns the canonical pathname of the base role.
         * 
         * @return the canonical pathname of the base role.
         */
        public String getBasePathname() {
            return basePathname;
        }

        /**
         * Creates a list of pathnames of the refinement roles relative to their
         * feature module dirs. The list have the order of feature modules in
         * the features file.
         * 
         * @return the list of relative refinement role pathnames.
         */
        // TODO: is it used? Comment out, run tests and examples.
        public List<String> calculateRefinementRelativePathnames() {
            List<String> pathnames = new ArrayList<String>();
            String baseSuffix = basePathname
                    .substring(baseFeatureModulePathname.length() + 1);
            for (String fmPathname : refiningFeatureModules) {
                String relativePathname = (fmPathname + File.separator + baseSuffix)
                        .substring(basedirPathname.length() + 1);
                pathnames.add(relativePathname);
            }
            return pathnames;
        }

        /**
         * Creates a list of pathnames of the refinement roles relative to their
         * feature module dirs. The list have the order of feature modules in
         * the features file.
         * 
         * @return the list of relative refinement role pathnames.
         */
        public List<String> calculateRefinementPathnames() {
            List<String> pathnames = new ArrayList<String>();
            String baseSuffix = basePathname
                    .substring(baseFeatureModulePathname.length() + 1);
            for (String fmPathname : refiningFeatureModules) {
                fmPathname = (fmPathname + File.separator + baseSuffix);
                pathnames.add(fmPathname);
            }
            return pathnames;
        }

        // TODO is it used? Comment out, run tests and examples.
        @Override
        public boolean equals(Object o) {
            if (o instanceof RoleGroup || o instanceof String) {
                return o.toString().equals(toString());
            }
            return false;
        }

        @Override
        public String toString() {
            return basePathname;
        }
    }
}
