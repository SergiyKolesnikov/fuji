package fuji;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;

import de.ovgu.featureide.fm.core.Feature;
import de.ovgu.featureide.fm.core.FeatureModel;
import de.ovgu.featureide.fm.core.io.UnsupportedModelException;
import de.ovgu.featureide.fm.core.io.guidsl.GuidslReader;

import AST.CompilationUnit;

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

    private String basedirPathname; // canonical pathname

    /* Feature module pathenames in the order they are listed in features file. */
    private List<String> featureModulePathnames; // canonical pathnames

    private FeatureModel featureModel;

    private Map<String, RoleGroup> roleGroups;

    /**
     * SPLStructure constructor.
     * 
     * @param bdPathname
     *            absolute pathname of the SPL's root directory
     * @param featureModelPathname
     *            absolute pathname of the feature model file
     * @param featuresFilePathname
     *            absolute pathname of the features file
     * @throws IOException
     * @throws FeatureDirNotFoundException
     * @throws SyntacticErrorException
     * @throws UnsupportedModelException
     * @throws IllegalArgumentException 
     */
    public SPLStructure(String bdPathname, String featureModelPathname,
            String featuresFilePathname)
            throws IOException, FeatureDirNotFoundException,
            SyntacticErrorException, UnsupportedModelException, IllegalArgumentException {

        if (featureModelPathname == null && featuresFilePathname == null) {
            throw new IllegalArgumentException(
                    "The pathname of the feature model and the features file may not be both null.");
        }
        basedirPathname = new File(bdPathname).getCanonicalPath();
        if (featureModelPathname != null) {

            /* Read in the feature model. */
            File guidsl_file = new File(featureModelPathname);
            featureModel = new FeatureModel();
            GuidslReader reader = new GuidslReader(featureModel);
            reader.readFromFile(guidsl_file);
        }
        if (featuresFilePathname == null) {
            featureModulePathnames = parseFeatureList(basedirPathname,
                    featureNamesFromModel(featureModel));
        } else {
            featureModulePathnames = parseFeautresFile(basedirPathname,
                    new File(featuresFilePathname).getCanonicalPath());
        }
        initSPLStructure();
    }

    /**
     * SPLStructure constructor.
     * 
     * @param bdPathname
     *            absolute pathname of the SPL's root directory.
     * @param featureModel
     *            a feature model
     * @param featuresList
     *            a list of features to be composed.
     * @throws IOException
     * @throws FeatureDirNotFoundException
     * @throws SyntacticErrorException
     * @throws IllegalArgumentException 
     */
    public SPLStructure(String bdPathname, FeatureModel featureModel,
            List<String> featuresList)
            throws IOException, FeatureDirNotFoundException,
            SyntacticErrorException, IllegalArgumentException {

        if (featureModel == null && featuresList == null) {
            throw new IllegalArgumentException(
                    "The feature model and the feature list may not be both null.");
        }
        basedirPathname = new File(bdPathname).getCanonicalPath();
        this.featureModel = featureModel;
        if (featuresList == null) {
            featuresList = featureNamesFromModel(this.featureModel); 
        }
        featureModulePathnames = parseFeatureList(basedirPathname, featuresList);
        initSPLStructure();
    }

    /**
     * Functionality common to all constructors.
     */
    private void initSPLStructure() throws IOException,
            FeatureDirNotFoundException, SyntacticErrorException {
        roleGroups = createRoleGroups(featureModulePathnames);
    }

    /**
     * Returns dependency graphs of the SPL. A dependency graph contains all the
     * role groups that are referencing or are being referenced by a role group
     * form the graph.
     * 
     * @return a list of dependency graphs ordered by the number of nodes (role
     *         groups). A graph with least nodes first.
     */
    public Collection<RoleGroup> getRoleGropus() {
        return Collections.unmodifiableCollection(roleGroups.values());
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

    public FeatureModel getFeatureModel() {
        return featureModel;
    }
    
    /**
     * Parse the file containing the feature choice.
     * 
     * @param bdPathname
     *            canonical pathname to the SPL's root dir.
     * 
     * @param ffPathname
     *            absolute pathname to the .features file.
     * 
     * @return list of strings representing canonical pathnames of project's
     *         feature directories.
     */
    private List<String> parseFeautresFile(String bdPathname, String ffPathname)
            throws FeatureDirNotFoundException, IOException {

        ArrayList<String> fmPathnames = new ArrayList<String>();
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
                fmPathnames.add(featureModulePathname);
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
        if (fmPathnames.size() == 0) {
            throw new FeatureDirNotFoundException(
                    "Feature file does not specify feature direcotries.");
        }
        return fmPathnames;
    }

    /**
     * Parse the file containing the feature choice.
     * 
     * @param bdPathname
     *            canonical pathname to the SPL's root dir.
     * 
     * @param featuresList
     *            a list of features selected in this configureation.
     * 
     * @return list of strings representing canonical pathnames of project's
     *         feature directories.
     * @throws FeatureDirNotFoundException
     */
    private List<String> parseFeatureList(String bdPathname,
            List<String> featuresList) throws FeatureDirNotFoundException {
        if (featuresList == null || featuresList.size() == 0) {
            throw new FeatureDirNotFoundException(
                    "Feature list is empty or null.");
        }

        /* Feature module canonical pathnames will be stored in this list. */
        ArrayList<String> fmPathnames = new ArrayList<String>();

        /* Feature module pathnames pointing to not existing directories. */
        ArrayList<String> notDirs = new ArrayList<String>();

        for (String fmName : featuresList) {

            /* Check if the specified feature dir exists. */
            String featureModulePathname = bdPathname + File.separator + fmName;
            if (new File(featureModulePathname).isDirectory()) {
                fmPathnames.add(featureModulePathname);
            } else {
                notDirs.add(fmName);
            }
        }
        if (!notDirs.isEmpty()) {
            String message = "The following directoies specified in the features list"
                    + " do not exist in the project's base directory:\n";
            for (String s : notDirs) {
                message += s + "\n";
            }
            throw new FeatureDirNotFoundException(message);
        }
        return fmPathnames;
    }

    /**
     * Generates a list of names of <em>concrete</em> features from a given
     * feature model.
     * 
     * @param fm
     *            feature model from which the method takes the feature names
     * @return a list of feature names
     */
    private List<String> featureNamesFromModel(FeatureModel fm) {
        List<String> featuresList = new ArrayList<String>();
        for (Feature f : fm.getConcreteFeatures()) {
            featuresList.add(f.getName());
        }
        return featuresList;
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
         * @deprecated Creates a list of pathnames of the refinement roles
         *             relative to their feature module dirs. The list have the
         *             order of feature modules in the features file.
         * 
         * @return the list of relative refinement role pathnames.
         */
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
