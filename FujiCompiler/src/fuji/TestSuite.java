package fuji;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import AST.Program;
import de.ovgu.featureide.fm.core.Feature;
import de.ovgu.featureide.fm.core.FeatureModel;
import de.ovgu.featureide.fm.core.io.UnsupportedModelException;
import de.ovgu.featureide.fm.core.io.guidsl.GuidslReader;

/* TestSuite */
public class TestSuite {

	private static Main fuji;

	@SuppressWarnings("unchecked")
	public static Collection errors = new ArrayList();
	@SuppressWarnings("unchecked")
	public static Collection warnings = new ArrayList();

	/* Feature-Model */
	private static FeatureModel model = new FeatureModel();
	
	/* TestCase-Nr. */
	private static String folder = "EPL";
	private static String path = "examples/";
	private static String modelName = "EPL.model";
	
//	private static String folder = "other/04";
//	private static String path = "TestCases/";
//	private static String modelName = "model.m";

	public static void main(String[] args) throws Exception {

		/* = Fuji-features-file */
		ArrayList<Feature> features = new ArrayList<Feature>();
		List<String> featureNames = new ArrayList<String>();

		/* lese FM ein */
		File guidsl_file = new File(path + folder + "/" + modelName);

		GuidslReader reader = new GuidslReader(model);
		try {
			reader.readFromFile(guidsl_file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedModelException e) {
			e.printStackTrace();
		}

		// Bereite Parameter für fuji vor.
		String[] params = {
				"-progmode",
				"-basedir",
				path + folder,
				"-fopRefs" };
		
		features.addAll(model.getConcreteFeatures());
		
		for (Feature f : features) {
			featureNames.add(f.getName());
		}

		// Initialisiere fuji.
		fuji = new Main(params, featureNames);

		// Bekomme den AST.
		Composition comp = fuji.getComposition(fuji);
		Iterator<Program> astIter = comp.getASTIterator();
		Program ast = astIter.next();
		
		// ast.errorCheck(errors, warnings);
		ast.splErrorCheck(model, errors, warnings);
		
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
		if (!warnings.isEmpty()) {
			StringBuilder message = new StringBuilder();
			message.append("\n");
			for (Object o : warnings) {
				message.append(o + "\n");
			}
			throw new CompilerWarningException(message.toString());
		}
	}
}
