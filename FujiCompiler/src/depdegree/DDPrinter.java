package depdegree;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DDPrinter {
	
	private final static char sep = ';';
	
	public static ArrayList<MethodResult> depDegrees = new ArrayList<MethodResult>();

	public static void printByCU() {
		sortDepDegrees();
		
		int featureID = -1;
		String cuQualifier = "";
		String filesep = "";

		for (MethodResult r : depDegrees) {
			if (!cuQualifier.equals(r.getCompilationUnit())) {
				cuQualifier = r.getCompilationUnit();
				featureID = -1;
				System.out.println(filesep + "###  " + cuQualifier + "  ###");
				filesep = "\n\n";
			}
			if (featureID != r.getFeatureID()) {
				featureID = r.getFeatureID();
				System.out.println("\n\tFeature " + featureID + ":");
			}
			System.out.println("\t  " + r.getSignature() + " || DD: "
					+ r.getDepDegree());
		}
		
		/** Output to a text file
		 */
		/*File file = new File("depdegree.txt");
		try {
			FileWriter output = new FileWriter(file);
			for (MethodResult r : depDegrees) {
				if (!cuQualifier.equals(r.getCompilationUnit())) {
					cuQualifier = r.getCompilationUnit();
					featureID = -1;
					output.write(filesep + "###  " + cuQualifier + "  ###\n");
					filesep = "\n\n";
				}
				if (featureID != r.getFeatureID()) {
					featureID = r.getFeatureID();
					output.write("\n\tFeature " + featureID + ":\n");
				}
				output.write("\t  " + r.getSignature() + " || DD: "
						+ r.getDepDegree() + "\n");
				output.flush();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	/*
	 * public static void printByFeature() { Collections.sort(depDegrees, new
	 * Comparator<MethodResult>() {
	 * 
	 * @Override // this comparator imposes orderings that are inconsistent with
	 * // equals public int compare(MethodResult o1, MethodResult o2) {
	 * 
	 * int comp; if ((comp = o1.getFeatureID() - o2.getFeatureID()) != 0) {
	 * return comp; } else if ((comp = o1.getCompilationUnit().compareTo(
	 * o2.getCompilationUnit())) != 0) { return comp; } else if ((comp =
	 * o2.getDepDegree() - o1.getDepDegree()) != 0) { return comp; } else {
	 * return o1.getSignature().compareTo(o2.getSignature()); } } });
	 * 
	 * int featureID = -1; String cuQualifier = ""; String sep = ""; for
	 * (MethodResult r : depDegrees) { if (featureID != r.getFeatureID()) {
	 * featureID = r.getFeatureID(); cuQualifier = ""; System.out.println(sep +
	 * "###  Feature " + featureID + "  ###"); sep = "\n\n"; } if
	 * (!cuQualifier.equals(r.getCompilationUnit())) { cuQualifier =
	 * r.getCompilationUnit(); System.out.println("\n\t" + cuQualifier + ":"); }
	 * System.out.println("\t  " + r.getSignature() + " || DD: " +
	 * r.getDepDegree()); } }
	 */
	public static void printCSV() {
		sortDepDegrees();
		
		File file = new File("depdegree.csv");
		try {
			FileWriter output = new FileWriter(file);
			output.write("FILE" + sep + "FEATUREID" + sep + "METHOD" + sep + "DEPDEGREE\n");
			for (MethodResult r : depDegrees) {
				output.write(r.getCompilationUnit() + sep + r.getFeatureID()
						+ sep + r.getSignature() + sep + r.getDepDegree()
						+ "\n");
				output.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void sortDepDegrees() {
		Collections.sort(depDegrees, new Comparator<MethodResult>() {
			@Override
			// this comparator imposes orderings that are inconsistent with
			// equals
			public int compare(MethodResult o1, MethodResult o2) {
				int comp;
				if ((comp = o1.getCompilationUnit().compareTo(
						o2.getCompilationUnit())) != 0) {
					return comp;
				} else if ((comp = o1.getFeatureID() - o2.getFeatureID()) != 0) {
					return comp;
				} else if ((comp = o2.getDepDegree() - o1.getDepDegree()) != 0) {
					return comp;
				} else {
					return o1.getSignature().compareTo(o2.getSignature());
				}
			}
		});
	}
}
