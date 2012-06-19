package fuji;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import AST.CompilationUnit;
import AST.Program;
import de.ovgu.featureide.fm.core.Feature;
import de.ovgu.featureide.fm.core.FeatureModel;
import de.ovgu.featureide.fm.core.io.UnsupportedModelException;
import de.ovgu.featureide.fm.core.io.guidsl.GuidslReader;

public class TestSuite {

	private static Main fuji;
	
	/* TEST */
	@SuppressWarnings("unchecked")
	public static Collection errors = new ArrayList();
	@SuppressWarnings("unchecked")
	public static Collection warnings = new ArrayList();
	/* /TEST */

	/* Feature-Model */
	private static FeatureModel model = new FeatureModel();
	
	private static String folder = "16/04";
	private static String path = "TestCases/";
	private static String modelName = "model.m";

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
		
		/* TODO: mit groeszerem Beispiel testen, 
		 * ob alle gewuneschten Features hierdurch richtig eingelesen werden,
		 * dh. ohne Base, ... */
		features.addAll(model.getConcreteFeatures());
		for (Feature f : features) {
			/* System.out.println(f.getName()); */
			featureNames.add(f.getName());
		}

		// Initialisiere fuji.
		fuji = new Main(params, featureNames);

		// Bekomme den AST.
		Composition comp = fuji.getComposition(fuji);
		Iterator<Program> astIter = comp.getASTIterator();
		Program ast = astIter.next();

		/* TEST */
		@SuppressWarnings("unchecked")
		Iterator<CompilationUnit> iter = ast.compilationUnitIterator();
		while (iter.hasNext()) {
			CompilationUnit cu = iter.next();
			if (cu.fromSource()) {
				
				 /* Check for static semantic errors. */
				cu.errorCheck(errors, warnings);
				cu.splErrorCheck(model, errors, warnings);
			}
		}
		
		if (!errors.isEmpty()) {
			StringBuilder message = new StringBuilder();
			for (Object o : errors) {
				message.append(o + "\n");
			}
			throw new SemanticErrorException(message.toString());
		}
		if (!warnings.isEmpty()) {
			StringBuilder message = new StringBuilder();
			for (Object o : warnings) {
				message.append(o + "\n");
			}
			throw new CompilerWarningException(message.toString());
		}
		/* /TEST */
	}
}
