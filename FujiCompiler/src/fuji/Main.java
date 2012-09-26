/**
 * 
 */
package fuji;

import static fuji.Main.OptionName.*;

import java.io.File;
import java.io.FileNotFoundException;
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

import de.ovgu.featureide.fm.core.Feature;
import de.ovgu.featureide.fm.core.FeatureModel;
import de.ovgu.featureide.fm.core.io.UnsupportedModelException;
import de.ovgu.featureide.fm.core.io.guidsl.GuidslReader;

import AST.CompilationUnit;
import AST.ComposingVisitor;
import AST.Program;

import java.lang.management.*;

/**
 * The main fuji class. Manages all the work.
 * 
 * @author kolesnik
 */
public class Main implements CompositionContext {

	private CommandLine cmd;
	private List<String> backboneCompilerArgs; // JastAddJ arguments
	private ComposingVisitor composingVisitor;
	private SPLStructure spl;
	private boolean generateClassFiles = true;
	private Collection<String> processedCUs = new ArrayList<String>();

	/* Feature-Model */
	private static FeatureModel model = new FeatureModel();
	
	private static long startCpuTimeNano = 0L;
	private static long taskCpuTimeNano = 0L;

	/**
	 * Starts fuji and processes exceptions.
	 * 
	 * @param args
	 *            command line arguments.
	 */
	public static void main(String[] args) {
		startCpuTimeNano = getCpuTime();
		try {		
			new Main(args, null);
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
		taskCpuTimeNano = getCpuTime() - startCpuTimeNano;
		System.out.println(taskCpuTimeNano);
	}

	/**
	 * Default constructor. Manages all the fuji's work.
	 * 
	 * @param args
	 *            command line args.
	 * @param featuresList
	 *            list of features to be composed. The order of features in this
	 *            list specifies the order of composition of the corresponding
	 *            feature modules.
	 * 
	 * @throws WrongArgumentException
	 * @throws ParseException
	 * @throws SyntacticErrorException
	 * @throws FeatureDirNotFoundException
	 * @throws IOException
	 * @throws SemanticErrorException
	 * @throws CompilerWarningException
	 */
	public Main(String[] args, List<String> featuresList)
			throws WrongArgumentException, ParseException, IOException,
			FeatureDirNotFoundException, SyntacticErrorException,
			SemanticErrorException, CompilerWarningException {

		/*
		 * The flag controls the construction of the SPL structure
		 * representation. If true, only one dependency graph containing all the
		 * groups will be created. The interrelations between the role groups
		 * are disregarded.
		 * 
		 * TODO: remove the whole thing with dependency graph generation because
		 * it is not needed.
		 */
		boolean useSingleDependencyGraph = true;

		/* = Fuji-features-file */
		ArrayList<Feature> features = new ArrayList<Feature>();
		List<String> featureNames = new ArrayList<String>();

		@SuppressWarnings("unchecked")
		Collection errors = new ArrayList();
		@SuppressWarnings("unchecked")
		Collection warnings = new ArrayList();

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

		/*
		 * Check the features file or feature model file in GUIDSL format
		 * (typechecker mode) argument. If in programmatic mode features file
		 * argument can be omitted. TODO: edit comment
		 */
		String featuresFileOrFeatureModelFilePathname = null;
		if (((cmd.getArgList().size() == 0) && !cmd.hasOption(PROG_MODE))
				|| (cmd.getArgList().size() > 1)) {
			throw new WrongArgumentException(
					"Invalid option or no features file or feature model file "
							+ "is specified. " + cmd.getArgList() + "\n");
		} else if (cmd.getArgList().size() == 1) {
			featuresFileOrFeatureModelFilePathname = cmd.getArgs()[0];
		} else if (cmd.getArgList().size() == 0) {
			// fuji is in programmatic mode.
		} else {
			throw new RuntimeException(
					"Could not process the command-line options correctly.");
		}

		/* Select composition strategy. */
		if (cmd.hasOption(EXT_INTROS) || cmd.hasOption(EXT_REFS)) {
			composingVisitor = new AST.ComposingVisitorRSF();
			generateClassFiles = false;
			useSingleDependencyGraph = true;
		} else {
			composingVisitor = new AST.ComposingVisitorNormal();
		}

		String basedir = cmd.getOptionValue(BASEDIR,
				System.getProperty("user.dir"));

		/*
		 * Decide where the features list or the feature model file comes from.
		 */
		if (featuresFileOrFeatureModelFilePathname != null) {

			if (cmd.hasOption(TYPECHECKER)) {
				/* lese FM ein */
				File guidsl_file = new File(
						featuresFileOrFeatureModelFilePathname);
				GuidslReader reader = new GuidslReader(model);
				try {
					reader.readFromFile(guidsl_file);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (UnsupportedModelException e) {
					e.printStackTrace();
				}

				features.addAll(model.getConcreteFeatures());

				for (Feature f : features) {
					featureNames.add(f.getName());
				}

				spl = new SPLStructure(basedir, featureNames,
						useSingleDependencyGraph);
			} else {

				/* Use features file */
				spl = new SPLStructure(basedir,
						featuresFileOrFeatureModelFilePathname,
						useSingleDependencyGraph);
			}

		} else {

			/*
			 * Take the features list from the parameter supplied to the
			 * constructor.
			 */
			spl = new SPLStructure(basedir, featuresList,
					useSingleDependencyGraph);
		}

		backboneCompilerArgs = constructBackboneCompilerArgs();

		if (!cmd.hasOption(PROG_MODE)) {
			Composition composition = new Composition(this);
			
			Iterator<Program> astIter = composition.getASTIterator();
			Program ast = astIter.next();

			if (cmd.hasOption(TYPECHECKER)) {
				ast.splErrorCheck(model, errors, warnings);
			} else {
				// ast.errorCheck(errors, warnings);
				processAST(composition);
			}

			/* throw Semantic Errors */
			if (!errors.isEmpty()) {
				StringBuilder message = new StringBuilder();
				message.append("\n");
				for (Object o : errors) {
					message.append(o + "\n");
				}
				throw new SemanticErrorException(message.toString());
			}

			/* throw Compiler Warnings */
			if (!warnings.isEmpty() && !cmd.hasOption(NOWARN)) {
				StringBuilder message = new StringBuilder();
				message.append("\n");
				for (Object o : warnings) {
					message.append(o + "\n");
				}
				throw new CompilerWarningException(message.toString());
			}
		}
	}

	@SuppressWarnings("static-access")
	private Options initOptions() {
		Options ops = new Options();
		ops.addOption(OptionBuilder.hasArg().withArgName("path")
				.withDescription("Override location of bootstrap class files")
				.create(BOOTCLASSPATH));
		ops.addOption(OptionBuilder
				.hasArg()
				.withArgName("path")
				.withDescription(
						"Specify where to find user class files and "
								+ "annotation processors").withLongOpt("cp")
				.create(CLASSPATH));
		ops.addOption(OptionBuilder
				.hasArg()
				.withArgName("directory")
				.withDescription("Specify where to place generated class files")
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
		ops.addOption(OptionBuilder
				.hasArg()
				.withArgName("directory")
				.withDescription(
						"Make source to source translation "
								+ "and write generated java code to the "
								+ "specified directory").create(SRC));
		ops.addOption(OptionBuilder.withDescription(
				"Instantiate fuji in programmatic mode.  This "
						+ "mode is used to control fuji from "
						+ "another program and not by using command-line.")
				.create(PROG_MODE));
		ops.addOption(OptionBuilder.withDescription(
				"Instantiate fuji in typechecker mode. A file containing "
						+ "the feature module is expected instead of the file "
						+ "containing a list of features.").create(TYPECHECKER));

		return ops;
	}

	/**
	 * Construct command-line arguments for the backbone compiler (currently
	 * JastAddJ) using command-line arguments supplied to fuji.
	 */
	private ArrayList<String> constructBackboneCompilerArgs() {
		ArrayList<String> args = new ArrayList<String>();
		if (cmd.hasOption(BOOTCLASSPATH)) {
			args.add("-" + BOOTCLASSPATH);
			args.add(cmd.getOptionValue(BOOTCLASSPATH));
		}

		String classpath = "";
		if (cmd.hasOption(D)) {
			args.add("-" + D);
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
			args.add(d);
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
		args.add("-" + CLASSPATH);
		args.add(classpath);

		if (cmd.hasOption(EXTDIRS)) {
			args.add("-" + EXTDIRS);
			args.add(cmd.getOptionValue(EXTDIRS));
		}
		if (cmd.hasOption(SOURCEPATH)) {
			args.add("-" + SOURCEPATH);
			args.add(cmd.getOptionValue(SOURCEPATH));
		}

		/* Additional flags, that are not user definable options. */
		if (cmd.hasOption(EXT_REFS)) {
			args.add(AST.IntrosRefsUtil.ALLOW_MULTIPLE_DECLARATIONS);
		}
		return args;
	}

	/**
	 * Process the AST of the variant according to the user options.
	 * 
	 * @throws SyntacticErrorException
	 * @throws WrongArgumentException
	 * @throws IOException
	 * @throws SemanticErrorException
	 * @throws CompilerWarningException
	 */
	public void processAST(Composition composition) throws IOException,
			WrongArgumentException, SyntacticErrorException,
			SemanticErrorException, CompilerWarningException {
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

	/**
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
			 * TODO: collect syntactical errors for _all_ CUs before
			 * composition. See also bug revealed by
			 * ReportParseError2Compile.test
			 */
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
			
//			if (errors.isEmpty()) {
//
//				/*
//				 * Write source code for the compilation unit. This must be done
//				 * before cu.transformation() is called.
//				 */
//				if (cmd.hasOption(SRC) && cu.fromRole()) {
//					generateSourcefile(cmd.getOptionValue(SRC), cu);
//				}
//				if (cmd.hasOption(EXT_ACCESSCOUNT)) {
//					// TODO
//				}
//				// cu.transformation(); // BENCHMARK
//				// cu.generateClassfile(); // BENCHMARK
//			}
		}

		processedCUs.add(cu.pathName());
	}

	/**
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
	 * Get backbone compiler (currently JastAddJ) arguments (options) that were
	 * extracted from the fujis command line.
	 * 
	 * @return compiler arguments (options).
	 */
	public String[] getBackboneCompilerArgs() {
		return backboneCompilerArgs.toArray(new String[backboneCompilerArgs
				.size()]);
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
	 * Returns an object representing the SPL structure.
	 * 
	 * @return object representing SPL structure.
	 */
	public SPLStructure getSPLStructure() {
		return spl;
	}

	/**
	 * A factory method for Composition objects.
	 * 
	 * @param compContext
	 *            a CompositionContext to initialize the Composition instance
	 *            with.
	 * 
	 * @return a instance of Composition object initialized with the
	 *         corresponding composition context.
	 */
	public Composition getComposition(CompositionContext compContext) {
		return new Composition(compContext);
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
		public static final String PROG_MODE = "progmode";

		/* TYPECHECKER */
		public static final String TYPECHECKER = "typechecker";
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
		return "2012-09-26"; /* TYPECHECKER */
	}
	
	/* for benchmarks: 
	 * http://nadeausoftware.com/
	 * articles/2008/03/java_tip_how_get_cpu_and_user_time_benchmarking
	 * #TimingasinglethreadedtaskusingCPUsystemandusertime
	 */
	 
	/** Get CPU time in nanoseconds. */
	public static long getCpuTime( ) {
	    ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
	    return bean.isCurrentThreadCpuTimeSupported( ) ?
	        bean.getCurrentThreadCpuTime( ) : 0L;
	}
	 
	/** Get user time in nanoseconds. */
	public static long getUserTime( ) {
	    ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
	    return bean.isCurrentThreadCpuTimeSupported( ) ?
	        bean.getCurrentThreadUserTime( ) : 0L;
	}

	/** Get system time in nanoseconds. */
	public static long getSystemTime( ) {
	    ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
	    return bean.isCurrentThreadCpuTimeSupported( ) ?
	        (bean.getCurrentThreadCpuTime( ) - bean.getCurrentThreadUserTime( )) : 0L;
	}
}
