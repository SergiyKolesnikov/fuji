/**
 * 
 */
package fuji;

import static fuji.Main.OptionName.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import AST.CompilationUnit;
import AST.ComposingVisitor;
import AST.Program;

/**
 * The main fuji class. Manages all the work.
 * 
 * @author kolesnik
 */
public class Main {

    private CommandLine cmd;
    private List<String> compilerArgs; // JastAddJ arguments
    private ComposingVisitor composingVisitor;
    private SPLStructure spl;
    private boolean generateClassFiles = true;
    private Collection<String> processedCUs = new ArrayList<String>();

    /**
     * Starts fuji and processes exceptions.
     * 
     * @param args
     *            command line arguments.
     */
    public static void main(String[] args) {
        try {
            new Main(args);
        } catch (WrongArgumentException e) {
            printError(e.getMessage());
        } catch (ParseException e) {
            printError(e.getMessage());
        } catch (IOException e) {
            printError(e.getMessage());
        } catch (FeatureDirNotFoundException e) {
            printError(e.getMessage());
        } catch (SyntacticErrorException e) {
            printError(e.getMessage());
        } catch (SemanticErrorException e) {
            printError(e.getMessage());
        } catch (CompilerWarningException e) {
            System.err.println(e.getMessage());
        } catch (CompositionErrorException e) {
            printError(e.getMessage() + "\n");
        }
    }

    /**
     * Default constructor. Manages all the fuji's work.
     * 
     * @param args
     *            command line args.
     * 
     * @throws WrongArgumentException
     * @throws ParseException
     * @throws SyntacticErrorException
     * @throws FeatureDirNotFoundException
     * @throws IOException
     * @throws SemanticErrorException
     * @throws CompilerWarningException
     */
    public Main(String[] args) throws WrongArgumentException, ParseException,
            IOException, FeatureDirNotFoundException, SyntacticErrorException,
            SemanticErrorException, CompilerWarningException {

        /*
         * The flag controls the construction of the SPL structure
         * representation. If true, only one dependency graph containing all the
         * groups will be created. The interrelations between the role groups
         * are disregarded.
         */
        boolean useSingleDependencyGraph = true; // DEBUG default should be
        // 'false'

        /* Initialize options and parse the command line. */
        Options options = initOptions();
        cmd = new GnuParser().parse(options, args, true);

        /* help, version */
        if (cmd.hasOption(HELP)) {
            new HelpFormatter()
                    .printHelp(72, toolName(), "", options, "", true);
            return;
        } else if (cmd.hasOption(VERSION)) {
            System.out.println(toolName() + " v." + version() + " "
                    + projectURL());
            return;
        }

        /* Check the features file argument. */
        if (cmd.getArgList().size() != 1) {
            throw new WrongArgumentException(
                    "Exactly one features file must be specified. "
                            + cmd.getArgList() + "\n");
        }

        /* Select composition strategy. */
        if (cmd.hasOption(EXT_INTROS) || cmd.hasOption(EXT_REFS)) {
            composingVisitor = new AST.ComposingVisitorRSF();
            generateClassFiles = false;
            useSingleDependencyGraph = true;
        } else {
            composingVisitor = new AST.ComposingVisitorNormal();
        }

        /* Process SPL structure, parse sources and build ASTs. */
        String basedir = cmd.getOptionValue(BASEDIR, System
                .getProperty("user.dir"));
        spl = new SPLStructure(basedir, cmd.getArgs()[0],
                useSingleDependencyGraph);
        constructCompilerArgs();
        Composition composition = new Composition(this, spl);
        Iterator<Program> astIter = composition.getASTIterator();

        /* Process the ASTs according to the user specified options. */
        @SuppressWarnings("unchecked")
        Collection errors = new ArrayList();
        @SuppressWarnings("unchecked")
        Collection warnings = new ArrayList();
        while (astIter.hasNext()) {
            Program ast = astIter.next();
            @SuppressWarnings("unchecked")
            Iterator<CompilationUnit> iter = ast.compilationUnitIterator();
            while (iter.hasNext()) {
                CompilationUnit cu = iter.next();
                if (cu.fromSource()) {
                    processCU(cu, errors, warnings);
                }
            }
        }
        if (!errors.isEmpty()) {
            StringBuilder message = new StringBuilder();
            for (Object o : errors) {
                message.append(o + "\n");
            }
            throw new SemanticErrorException(message.toString());
        }
        if (!warnings.isEmpty() && !cmd.hasOption(NOWARN)) {
            StringBuilder message = new StringBuilder();
            for (Object o : warnings) {
                message.append(o + "\n");
            }
            throw new CompilerWarningException(message.toString());
        }
    }

    @SuppressWarnings("static-access")
    private Options initOptions() {
        Options ops = new Options();
        ops.addOption(OptionBuilder.hasArg().withArgName("path")
                .withDescription("Override location of bootstrap class files")
                .create(BOOTCLASSPATH));
        ops.addOption(OptionBuilder.hasArg().withArgName("path")
                .withDescription(
                        "Specify where to find user class files and "
                                + "annotation processors").withLongOpt("cp")
                .create(CLASSPATH));
        ops
                .addOption(OptionBuilder.hasArg().withArgName("directory")
                        .withDescription(
                                "Specify where to place generated class files")
                        .create(D));
        ops.addOption(OptionBuilder.hasArg().withArgName("dirs")
                .withDescription("Override location of installed extensions")
                .create(EXTDIRS));
        ops.addOption(OptionBuilder.withDescription(
                "Print a synopsis of standard options").create(HELP));
        ops.addOption(OptionBuilder.withDescription("Generate no warnings")
                .create(NOWARN));
        ops.addOption(OptionBuilder.hasArg().withArgName("path")
                .withDescription("Specify where to find input source files")
                .create(SOURCEPATH));
        ops.addOption(OptionBuilder.withDescription("Version information")
                .create(VERSION));
        ops.addOption(OptionBuilder.hasArg().withArgName("directory")
                .withDescription("Specyfy where to find feature modules")
                .create(BASEDIR));
        ops.addOption(OptionBuilder.withDescription("Pring access statistics")
                .create(EXT_ACCESSCOUNT));
        ops.addOption(OptionBuilder.withDescription(
                "Print introduces relations").create(EXT_INTROS));
        ops.addOption(OptionBuilder.withDescription("Print ref relations")
                .create(EXT_REFS));
        ops.addOption(OptionBuilder.hasArg().withArgName("directory")
                .withDescription(
                        "Make source to source translation "
                                + "and write generated java code to the "
                                + "specified directory").create(SRC));
        return ops;
    }

    private void constructCompilerArgs() {
        compilerArgs = new ArrayList<String>();
        if (cmd.hasOption(BOOTCLASSPATH)) {
            compilerArgs.add("-" + BOOTCLASSPATH);
            compilerArgs.add(cmd.getOptionValue(BOOTCLASSPATH));
        }

        String classpath = "";
        if (cmd.hasOption(D)) {
            compilerArgs.add("-" + D);
            String d = cmd.getOptionValue(D);

            /*
             * If the destination directory for generated class files is set,
             * add it to the class path.
             * 
             * Reason: if class A uses class B and B was composed and compiled
             * already, the composed and compiled class from the -d directory
             * will be retrieved for A compilation and not some incomplete base
             * or refinement class.
             * 
             * NOTE: It is not default javac behaviour.
             */
            classpath += ":" + d;
            compilerArgs.add(d);
        }
        for (String pathname : spl.getFeatureModulePathnames()) {
            classpath += ":" + pathname;
        }
        if (cmd.hasOption(BASEDIR)) {
            classpath += ":" + cmd.getOptionValue(BASEDIR);
        }
        classpath += ":.";
        if (cmd.hasOption(CLASSPATH)) {
            classpath += ":" + cmd.getOptionValue(CLASSPATH);
        }
        compilerArgs.add("-" + CLASSPATH);
        compilerArgs.add(classpath);

        if (cmd.hasOption(EXTDIRS)) {
            compilerArgs.add("-" + EXTDIRS);
            compilerArgs.add(cmd.getOptionValue(EXTDIRS));
        }
        if (cmd.hasOption(SOURCEPATH)) {
            compilerArgs.add("-" + SOURCEPATH);
            compilerArgs.add(cmd.getOptionValue(SOURCEPATH));
        }

        /* Additional flags, that are not user definable options. */
        if (cmd.hasOption(EXT_REFS)) {
            compilerArgs.add(AST.IntrosRefsUtil.ALLOW_MULTIPLE_DECLARATIONS);
        }
    }

    /*
     * Process a CompilationUnit according to the user options. And remember
     * processed CUs.
     */
    @SuppressWarnings("unchecked")
    private void processCU(CompilationUnit cu, Collection errors,
            Collection warnings) throws IOException, WrongArgumentException,
            SyntacticErrorException {

        if (cmd.hasOption(EXT_INTROS)
                && spl.isBaseRoleSourcefile(cu.pathName())
                && !processedCUs.contains(cu.pathName())) {

            cu.printIntros(spl.getFeatureModulePathnames());
        }
        if (cmd.hasOption(EXT_REFS) && spl.isBaseRoleSourcefile(cu.pathName())
                && !processedCUs.contains(cu.pathName())) {

            cu.printRefs(spl.getFeatureModulePathnames());
        }
        if (generateClassFiles) {

            /*
             * Check for syntactical errors. (They have been checked in
             * SPLStructure before while creating dependency graphs.
             * 
             * TODO: collect syntactical errors for _all_ CUs first and then
             * throw the SyntacticErrorException. See also bug revealed by
             * ReportParseError2Compile.test
             */
            @SuppressWarnings("unchecked")
            Collection parseErrors = cu.parseErrors();
            if (!parseErrors.isEmpty()) {
                StringBuilder message = new StringBuilder();
                for (Object o : parseErrors) {
                    message.append(o + "\n");
                }
                throw new SyntacticErrorException(message.toString());
            }

            /*
             * Check for static semantic errors.
             */
            cu.errorCheck(errors, warnings);
            if (errors.isEmpty()) {

                /*
                 * Write source code for the compilation unit. This must be done
                 * before cu.transformation() is called.
                 */
                if (cmd.hasOption(SRC) && cu.fromRole()) {
                    generateSourcefile(cmd.getOptionValue(SRC), cu);
                }
                if (cmd.hasOption(EXT_ACCESSCOUNT)) {
                    // TODO
                }
                cu.transformation();
                cu.generateClassfile();
            }
        }

        processedCUs.add(cu.pathName());
    }

    /*
     * Generate source code from the compilation unit and write it to a file in
     * the destination directory.
     * 
     * NOTE: Enum constructors are not printed (JastAddJ bug
     * http://bugs.jastadd.org/cgi-bin/bugzilla/show_bug.cgi?id=42 ).
     * 
     * NOTE: do not run cu.transformation() before this. It would add some
     * JastAddJ specific nodes to the AST that would make the generated code
     * invalid.
     */
    private void generateSourcefile(String destination, CompilationUnit cu)
            throws IOException, WrongArgumentException {
        String destinationDir = new File(destination).getCanonicalPath();
        if (!spl.getBasedirPathname().contains(destinationDir)) {
            cu.generateSourcefile(destinationDir);
        } else {
            throw new WrongArgumentException(
                    "Destination directory for generated ("
                            + destinationDir
                            + ") source files coincide with a feature module directory.");
        }
    }

    /**
     * Get compiler arguments (options) that were extracted from the fujis
     * command line.
     * 
     * @return compiler arguments (options).
     */
    public String[] getCompilerArgs() {
        return compilerArgs.toArray(new String[compilerArgs.size()]);
    }

    /**
     * Returns composition strategy visitor to be used in the AST composition
     * process.
     * 
     * @return composition strategy visitor.
     */
    public ComposingVisitor getComposingVisitor() {
        return composingVisitor;
    }

    /**
     * Enumerates all the options accepted by fuji.
     * 
     * @author kolesnik
     */
    public static class OptionName {
        public static final String BOOTCLASSPATH = "bootclasspath"; //
        public static final String CLASSPATH = "classpath"; //
        public static final String D = "d"; //
        public static final String EXTDIRS = "extdirs"; //
        public static final String HELP = "help"; //
        public static final String NOWARN = "nowarn";
        public static final String SOURCEPATH = "sourcepath"; //
        public static final String VERSION = "version";

        /* Not JastAddJ options. */
        public static final String BASEDIR = "basedir";
        public static final String EXT_ACCESSCOUNT = "fopStatistic"; //
        public static final String EXT_INTROS = "fopIntroduces"; //
        public static final String EXT_REFS = "fopRefs"; //
        public static final String SRC = "src";
    }

    private static void printError(String message) {
        System.err.println("Errors:");
        System.err.print(message);
    }

    private static String toolName() {
        return "fuji";
    }

    private static String projectURL() {
        return "http://www.fosd.de/fuji";
    }

    private static String version() {
        return "2011-10-18";
    }
}

