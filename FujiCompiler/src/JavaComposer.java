import AST.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;
import java.util.regex.Pattern;

import fuji.FeatureDirNotFoundException;
import fuji.SyntacticErrorException;
import fuji.WrongArgumentException;

public class JavaComposer {

    // File extension of feature file.
    private static final Pattern FEATUREFILE_EXT_PAT = Pattern.compile(
            ".*\\.features$", Pattern.CASE_INSENSITIVE);

    // Comment mark in the feature file.
    private static final String FEATUREFILE_COMMENT = "\\s*#.*";

    // File extension of the feature classes.
    private static final Pattern SOURCE_FILE_EXT_PAT = Pattern.compile(
            ".*\\.java$", Pattern.CASE_INSENSITIVE);

    /* Command line arguments of the JavaComposer. */
    private String[] arguments;

    /* Root directory of the SPLs project */
    private String basedirPathname;

    /*
     * List of the feature dir names in the order they appear in the .features
     * file.
     */
    private List<String> featureModulePathnames;

    /* Visitor pattern is used to compose ASTs. */
    private ComposingVisitor composeVisitor = null;

    /*
     * Flag denoting, if the actual compilation (creating .class-files) is done.
     */
    private boolean compilationDisabled = false;

    /*
     * Holds all names of types, that have been already processed by some stat.
     * method. Prevents repetitive processing.
     */
    private Collection<String> statProcessedTypes = new ArrayList<String>();
    private HashMap<String, FOPAccessCount> fopStat = new HashMap<String, FOPAccessCount>();

    /* Constants for command line arguments. */

    /* Denotes if usage message should be printed. */
    public static final String HELP_OPT = "-help";

    /* Denotes if fuji's version should be printed. */
    public static final String VER_OPT = "-version";

    /* Denotes if Statistics about member accesses should be generated. */
    public static final String MODSTAT_OPT = AccessAnalyzer.MODSTAT_OPT;

    /* Denotes if 'introduces' relations should be generated. */
    public static final String INTROSTAT_OPT =null;// = FOPrsfUtil.INTROSTAT_OPT;

    /* Denotes if 'refs' relations should be generated. */
    public static final String REFSTAT_OPT=null;// = FOPrsfUtil.REFSTAT_OPT;

    /* Denotes classpath. */
    public static final String CP_OPT = "-classpath";

    /* Denotes destination directory for generated .class files. */
    public static final String DESTDIR_OPT = "-d";

    /* Denotes the root directory of the SPL project. */
    public static final String BASEDIR_OPT = "-basedir";

    public static void main(String args[]) {
        new JavaComposer(args);
    }

    private JavaComposer(String[] args) {
        
//      new TypeAccess().t
//        new Modifiers().
        
        new MethodAccess().inStaticContext();
        try {
            arguments = parseArgs(args);
            if (arguments != null)
                process(args);
        } catch (IOException e) {
            printError(e.getMessage());
        } catch (FeatureDirNotFoundException e) {
            printError(e.getMessage());
        } catch (WrongArgumentException e) {
            printError(e.getMessage() + "\n");
            printUsage();
        } catch (SyntacticErrorException e) {
            System.err.print(e.getMessage());
        }
    }

    private void process(String[] args) throws SyntacticErrorException {
        final Map<String, RoleGroup> roleGropus = getRoleGroups();
        if (roleGropus.isEmpty())
            return;

        try {
            /* Compute transitive dependencies for each feature type. */
            for (RoleGroup roleGroup : roleGropus.values()) {
                roleGroup.transDependencies(roleGropus);
            }
        } catch (SyntacticErrorException e) {
            return;
        }

        /* Sort role groups: RGs with less transitive deps first. */
        List<RoleGroup> sortedRGs = new ArrayList<JavaComposer.RoleGroup>(
                roleGropus.values());
        Collections.sort(sortedRGs, (new Comparator<RoleGroup>() {
            @Override
            public int compare(RoleGroup o1, RoleGroup o2) {
                try {
                    return o1.transDependencies(roleGropus).size()
                            - o2.transDependencies(roleGropus).size();
                } catch (SyntacticErrorException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        }));

        for (RoleGroup fc : sortedRGs) {
            if (fc.composed) {
                /* The feature class has been composed already. */
                continue;
            }

            /*
             * Create a set of related fcs, i.e. current fc + all transitively
             * referenced fcs.
             */
            Set<RoleGroup> relatedRGs = new HashSet<RoleGroup>();
            relatedRGs.add(fc);
            for (String dep : fc.transDependencies(roleGropus)) {
                RoleGroup depFC = roleGropus.get(dep);

                /* Prevent repetitive composition and compilation. */
                if (!depFC.composed || compilationDisabled)
                    relatedRGs.add(depFC);
            }

            /*
             * Instantiate CUs for all the related FCs and compose the
             * corresponding refinements.
             */
            Program program = initProgram();
            for (RoleGroup relRG : relatedRGs) {
                program.addSourceFile(relRG.baseRelativePathname);
            }

            for (RoleGroup relRG : relatedRGs) {

                /*
                 * Instantiate CUs for all the refinements of the currently
                 * processed FC.
                 */
                for (String refPath : relRG.refinementRelativePathnames()
                        .values()) {
                    program.addSourceFile(refPath);
                }

                /* Compose refinements. */
                composeRefinements(program, relRG);
                relRG.composed = true;
            }
            runStat(program);
            if (!compilationDisabled)
                checkAndCompile(program);

            // TODO refactor and move to runStat
            if (program.options().hasOption(MODSTAT_OPT))
                outputStatistic();
        }
    }

    private void composeRefinements(Program program, RoleGroup fc) {
        // if (fc.refiningFeatureModules.isEmpty()) {
        // /* no refinements => can be seen as already composed. */
        // return;
        // }

        /* Get CompilationUnit for the relFC. */
        CompilationUnit baseCU = null;
        Collection<CompilationUnit> refCUs = new ArrayList<CompilationUnit>();
        Collection<String> refinementPaths = fc.refinementRelativePathnames()
                .values();
        @SuppressWarnings("unchecked")
        Iterator<CompilationUnit> cuIter = program.compilationUnitIterator();
        while (cuIter.hasNext()) {
            CompilationUnit cu = cuIter.next();
            if (cu.fromSource()) {
                for (int i = 0; i < featureModulePathnames.size(); ++i) {
                    if (cu.pathName().startsWith(featureModulePathnames.get(i))) {
                        cu.setFeatureID(i);
                    }
                }
                if (cu.relativeName() != null
                        && cu.relativeName().equals(fc.baseRelativePathname)) {
                    baseCU = cu;
                } else if (refinementPaths.contains(cu.relativeName())) {
                    refCUs.add(cu);
                }
            }
        }

        for (CompilationUnit refCU : refCUs) {
            if (!baseCU.accept(composeVisitor, refCU))
                throw new RuntimeException("Composition failed!");
        }
    }

    private Program initProgram() {
        if (arguments == null) {
            throw new RuntimeException("Can not initialize Program instance.");
        }
        Program program = new Program();
        program.state().reset();
        program.initBytecodeReader(new BytecodeParser());
        initParser(program);
        initOptions(program);
        program.options().addOptions(arguments);
        return program;
    }

    /*
     * Run statistic generating methods on a program.
     */
    private void runStat(Program program) {

        /* Check if it worth bothering. */
        if (!(program.options().hasOption(INTROSTAT_OPT) || program.options()
                .hasOption(REFSTAT_OPT)))
            return;

        @SuppressWarnings("unchecked")
        Iterator<CompilationUnit> iter = program.compilationUnitIterator();
        while (iter.hasNext()) {
            CompilationUnit cu = iter.next();
            if (cu.fromSource()
                    && !statProcessedTypes.contains(cu.classQName())) {

                /* Check for statistics options, if present print out stat. */
                if (program.options().hasOption(INTROSTAT_OPT)) {
                    //cu.printIntroduces(featureModulePathnames);
                }
                if (program.options().hasOption(REFSTAT_OPT)) {
                    cu.printRefs(featureModulePathnames);
                }
                if (program.options().hasOption(INTROSTAT_OPT)
                        || program.options().hasOption(REFSTAT_OPT)) {
                    statProcessedTypes.add(cu.classQName());
                }
            }
        }
    }

    /* Run error checks and compile the CU. */
    private void checkAndCompile(Program program) {

        @SuppressWarnings("unchecked")
        Iterator<CompilationUnit> iter = program.compilationUnitIterator();
        while (iter.hasNext()) {
            CompilationUnit cu = iter.next();
            if (cu.fromSource()) {

                /*
                 * Check for static semantic errors. Parse errors are checked in
                 * FCContainer.directDependencies().
                 */
                @SuppressWarnings("unchecked")
                Collection errors = new LinkedList();
                @SuppressWarnings("unchecked")
                Collection warnings = new LinkedList();
                cu.errorCheck(errors, warnings);

                /*
                 * Check for statistics options, if present print out stat and
                 * return without compilation.
                 * 
                 * TODO: 1. rewrite fopStatistic (make it independent form
                 * errorCheck like fopIntroduces and fopRefs). 2. move its
                 * execution to runStat().
                 */
                if (program.options().hasOption(MODSTAT_OPT)) {
                    collectStatistic(cu.getFOPAccs());
                    continue;
                }
                if (!errors.isEmpty()) {

                    /* Static semantic errors found, process them. */
                    processErrors(errors);
                    continue;
                } else {
                    processWarnings(warnings);
                    cu.transformation();
                    cu.generateClassfile();
                }
            }
        }
    }

    /*
     * Process JavaComposer command line arguments. Includes parsing .features
     * file, updating classpath, setting destination directory for .class files.
     * 
     * @param args non-empty array of arguments.
     * 
     * @return prepared array of arguments.
     * 
     * @throws IOException
     */
    private String[] parseArgs(String[] args)
            throws FeatureDirNotFoundException, WrongArgumentException,
            IOException {

        LinkedList<String> newArgs = new LinkedList<String>(java.util.Arrays
                .asList(args));
        if (newArgs.contains(HELP_OPT)) {
            printUsage();
            return null;
        } else if (newArgs.contains(VER_OPT)) {
            printVersion();
            return null;
        } else if (args.length == 0
                || !FEATUREFILE_EXT_PAT.matcher(newArgs.getLast()).matches()) {
            throw new WrongArgumentException(
                    "Features file must be specified.  The file must have '.features' extension.");
        }

        /* Choose a composing visitor. */
        if (newArgs.contains(REFSTAT_OPT) || newArgs.contains(INTROSTAT_OPT)) {
            composeVisitor = new ComposingVisitorRSF();
            compilationDisabled = true;
        } else {
            composeVisitor = new ComposingVisitorNormal();
        }

        /* BASEDIR */
        basedirPathname = System.getProperty("user.dir");
        if (newArgs.contains(BASEDIR_OPT)) {
            int idx = newArgs.indexOf(BASEDIR_OPT);
            basedirPathname = newArgs.get(idx + 1);
        }

        /* Process .features file. */
        String featuresFilePathname = newArgs.removeLast();
        featureModulePathnames = parseFeautresFile(basedirPathname,
                featuresFilePathname);

        /*
         * TODO add the root dir of the processed SPL to the classpath. Take the
         * current working dir if the -basedir option is not sepcified.
         */

        /* Add paths of feature dirs to the classpath. */
        int classPathIndex = newArgs.indexOf(CP_OPT);
        if (classPathIndex != -1) {
            ++classPathIndex;
        } else {
            newArgs.add(CP_OPT);
            newArgs.add("");
            classPathIndex = newArgs.size() - 1;
        }
        String classPath = newArgs.get(classPathIndex);
        classPath += ":" + basedirPathname;
        for (String path : featureModulePathnames)
            classPath += ":" + path;
        newArgs.set(classPathIndex, classPath);

        /*
         * If the destination directory for generated class files is set, add it
         * to the class path.
         * 
         * Reason: if class A uses class B and B was composed and compiled
         * already, the composed and compiled class from the -d directory will
         * be retrieved for A compilation and not some incomplete base or
         * refinement class.
         * 
         * XXX It is not default javac behaviour.
         */
        int dIndex = newArgs.indexOf(DESTDIR_OPT);
        if (dIndex != -1) {
            classPath = newArgs.get(dIndex + 1) + ":" + classPath;
            newArgs.set(classPathIndex, classPath);
        }
        // new fuji.SPLStructure(basedirPathname, featuresFilePathname);
        return newArgs.toArray(new String[0]);
    }

    /*
     * Parse .features file.
     * 
     * @param ffPathname pathname to the .features file.
     * 
     * @param bdPathname pathname to the root dir of the SPL.
     * 
     * @return list of strings representing names of project's feature
     * directories.
     */
    private List<String> parseFeautresFile(String bdPathname, String ffPathname)
            throws IOException, FeatureDirNotFoundException {

        String basedirPathname = new File(bdPathname).getCanonicalPath();
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
            String featureModulePathname = basedirPathname + File.separator
                    + line;
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
     * Process all the .java files in the feature modules and organize roles in
     * role groups. A role group represents a base role and all its refinements
     * for the given feature selection.
     * 
     * @return a map of the base role absolute pathname to the corresponding
     * role group.
     */
    private Map<String, RoleGroup> getRoleGroups() {

        LinkedHashMap<String, RoleGroup> groups = new LinkedHashMap<String, RoleGroup>();
        for (String fmPathname : featureModulePathnames) {
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
                        // file's pathname relative to the feature module dir.
                        String baseSuffix = filePathname.substring(fmPathname
                                .length() + 1);
                        if (groups.containsKey(baseSuffix)) {
                            RoleGroup rg = groups.get(baseSuffix);
                            rg.refiningFeatureModules.put(
                                    featureModulePathnames.indexOf(fmPathname),
                                    fmPathname);
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
     * Initialize parser for the program.
     * 
     * @param p program to init parser for.
     */
    private void initParser(Program p) {
        p.initJavaParser(new JavaParser() {
            public CompilationUnit parse(java.io.InputStream is, String fileName)
                    throws java.io.IOException, beaver.Parser.Exception {
                return new parser.JavaParser().parse(is, fileName);
            }
        });
    }

    /*
     * Initialize options for the program.
     * 
     * @param p program to init options for.
     */
    private void initOptions(Program p) {
        Options options = p.options();
        options.initOptions();
        options.addKeyOption("-print");
        options.addKeyOption("-g");
        options.addKeyOption("-g:none");
        options.addKeyOption("-g:lines,vars,source");
        options.addKeyOption("-nowarn");
        options.addKeyOption("-verbose");
        options.addKeyOption("-deprecation");
        options.addKeyValueOption("-sourcepath");
        options.addKeyValueOption("-bootclasspath");
        options.addKeyValueOption("-extdirs");
        options.addKeyValueOption("-encoding");
        options.addKeyValueOption("-source");
        options.addKeyValueOption("-target");
        options.addKeyOption("-O");
        options.addKeyOption("-J-Xmx128M");
        options.addKeyValueOption("-classpath");
        options.addKeyValueOption("-d");
        options.addKeyOption("-help");
        options.addKeyOption("-version");
    }

    @SuppressWarnings("unchecked")
    private void processErrors(Collection errors) {
        System.err.println("Errors:");
        for (Iterator iter2 = errors.iterator(); iter2.hasNext();) {
            System.err.println(iter2.next());
        }
    }

    @SuppressWarnings("unchecked")
    private void processWarnings(Collection warnings) {
        for (Iterator iter2 = warnings.iterator(); iter2.hasNext();) {
            System.err.println(iter2.next());
        }
    }

    private static void printError(String message) {
        System.err.println("Error: " + message);
    }

    private static void printUsage() {
        printVersion();
        StringBuilder message = new StringBuilder();
        message.append("\nUsage: java -jar " + name()
                + " [options] <features_file>\n");
        message
                .append("-verbose \t\t Output messages about what the compiler is doing\n");
        message.append(CP_OPT
                + " <path> \t Specify where to find user class files\n");
        message
                .append("-sourcepath <path> \t Specify where to find input source files\n");
        message
                .append("-bootclasspath <path> \t Override location of bootstrap class files\n");
        message
                .append("-extdirs <dirs> \t Override location of installed extensions\n");
        message
                .append(DESTDIR_OPT
                        + " <directory> \t\t Specifies where to place generated class files\n");
        message.append(HELP_OPT
                + " \t\t\t Print a synopsis of standard options\n");
        message
                .append(BASEDIR_OPT
                        + " <directory> \t\t Specifies the path to the root directory of the SPL\n");
        message.append(VER_OPT + " \t\t Print version information\n");
        message.append(MODSTAT_OPT + " \t\t Print access statistics.\n");
        message.append(INTROSTAT_OPT + " \t\t Print 'introduces' relations\n");
        message.append(REFSTAT_OPT + " \t\t Print 'refs' relations\n");
        System.out.println(message);
    }

    private static void printVersion() {
        System.out.println(toolName() + " v." + version() + " " + projectURL());
    }

    private static String toolName() {
        return "Fuji";
    }

    private static String name() {
        return "fuji.jar";
    }

    private static String projectURL() {
        return "http://www.fosd.de/fuji";
    }

    private void collectStatistic(Map<String, FOPAccessCount> stat) {
        for (String key : stat.keySet()) {
            FOPAccessCount acc = stat.get(key);
            if (fopStat.get(key) == null) {
                fopStat.put(key, acc);
            } else {
                fopStat.get(key).addAllAccess(acc);
            }
        }
    }

    private void outputStatistic() {
        // System.out.println(fopStat.keySet().size());
        StringBuilder sb = new StringBuilder();
        for (String key : fopStat.keySet()) {
            FOPAccessCount acc = fopStat.get(key);
            sb.append(acc.getMemberQName()).append(";");
            sb.append(acc.getMemberOrigModifier()).append(";");
            sb.append(acc.getMinOOPModifier()).append(" ").append(
                    acc.getMinFOPModifier()).append(";");
            Map<String, Integer> counts = acc.getAccessCount();
            for (String mod : counts.keySet()) {
                sb.append(counts.get(mod)).append(";");
            }
            sb.append("\n");
        }
        System.out
                .println("Name;Original OOP Modifier;Minimal OOP+FOP Modifier;package feature;package program;package subsequent;private feature;private program;private subsequent;protected feature;protected program;protected subsequent;public feature;public program;public subsequent;");
        System.out.print(sb);
    }

    /*
     * RoleGroup encapsulates a base role and all its refinement roles, provides
     * some utility methods.
     */
    private class RoleGroup {

        /* Pathname of the feature module containing the base role. */
        public String baseFeatureModulePathname;

        /* Pathname of the base role. */
        public String basePathname;

        /* Pathname of the base role relative to the working directory . */
        public String baseRelativePathname;

        /*
         * Maps a feature ID to the pathname of the corresponding feature module
         * containing refinement role.
         */
        public SortedMap<Integer, String> refiningFeatureModules = new TreeMap<Integer, String>();

        /*
         * Role groups that are referenced by the Compilation Unit for this role
         * group.
         */
        private HashSet<String> dDependencies = new HashSet<String>();
        private boolean dDependencies_computed = false;

        /* All dependencies that follow from directDependencies (transitive). */
        private HashSet<String> tDependencies = new HashSet<String>();
        private boolean tDependencies_computed = false;

        /* Flag: true, if the role group has been composed already. */
        public boolean composed = false;

        public RoleGroup(String baseFMPathname, String base) {
            baseFeatureModulePathname = baseFMPathname;
            basePathname = base;
            baseRelativePathname = base.substring(System
                    .getProperty("user.dir").length() + 1);
        }

        /*
         * Maps feature ID of a feature module containing a refinement role to
         * the pathname of this role.
         */
        public SortedMap<Integer, String> refinementRelativePathnames() {
            SortedMap<Integer, String> pathnames = new TreeMap<Integer, String>();
            String baseSuffix = basePathname
                    .substring(baseFeatureModulePathname.length() + 1);
            for (Entry<Integer, String> e : refiningFeatureModules.entrySet()) {
                String relativePathname = (e.getValue() + File.separator + baseSuffix)
                        .substring(basedirPathname.length() + 1);
                pathnames.put(e.getKey(), relativePathname);
            }
            return pathnames;
        }

        /*
         * Calculate direct dependencies of the role group.
         */
        private Set<String> directDependencies(Map<String, RoleGroup> roleGroups)
                throws SyntacticErrorException {
            if (!dDependencies_computed) {
                Program program = initProgram();
                program.addSourceFileIfNew(baseRelativePathname);
                for (String path : refinementRelativePathnames().values())
                    program.addSourceFileIfNew(path);
                @SuppressWarnings("unchecked")
                Iterator<CompilationUnit> iter = program
                        .compilationUnitIterator();
                @SuppressWarnings("unchecked")
                Collection parseErrors = null;
                while (iter.hasNext()) {
                    CompilationUnit cu = iter.next();
                    parseErrors = cu.parseErrors();
                    if (!parseErrors.isEmpty())
                        processErrors(parseErrors);
                    if (cu.classQName() != null) {
                        cu.toString(); // makes all referenced types to load.
                        RoleGroup depRG = roleGroups.get(cu.pathName());
                        if (depRG != null
                                && !basePathname.equals(depRG.basePathname)
                                && !dDependencies.contains(depRG.basePathname)) {
                            program
                                    .addSourceFileIfNew(depRG.baseRelativePathname);
                            for (String path : depRG
                                    .refinementRelativePathnames().values())
                                program.addSourceFileIfNew(path);
                            dDependencies.add(depRG.basePathname);
                        }
                    }
                }
                dDependencies_computed = true;
                if (parseErrors != null && !parseErrors.isEmpty())
                    throw new SyntacticErrorException();
            }
            return Collections.unmodifiableSet(dDependencies);
        }

        /*
         * Calculate transitive dependencies based on direct ones.
         */
        public Set<String> transDependencies(Map<String, RoleGroup> roleGroups)
                throws SyntacticErrorException {
            if (!tDependencies_computed) {
                directDependencies(roleGroups);

                /* Get all transitive feature types dependencies. */
                int setSize;
                tDependencies.addAll(dDependencies);
                do {
                    setSize = tDependencies.size();
                    // TODO iterates over the whole collection each time.
                    // Optimize!
                    ArrayList<String> tmpCol = new ArrayList<String>();
                    for (String type : tDependencies) {
                        tmpCol.addAll(roleGroups.get(type).directDependencies(
                                roleGroups));
                    }
                    tDependencies.addAll(tmpCol);
                } while (setSize != tDependencies.size());
                tDependencies_computed = true;
            }
            return Collections.unmodifiableSet(tDependencies);
        }

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

    private static String version() {
        return "2010-12-15";
    }
}
