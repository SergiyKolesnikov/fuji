package depdegree;

public class MethodResult {
	private String compilationUnit;
	private int featureID;
	private String signature;
	private int depDegree;
	
	public MethodResult(String cu, int id, String s, int dd) {
		compilationUnit = cu;
		featureID = id;
		signature = s;
		depDegree = dd;
	}
	
	public String getCompilationUnit() {
		return compilationUnit;
	}
	public int getFeatureID() {
		return featureID;
	}
	
	public String getSignature() {
		return signature;
	}
	
	public int getDepDegree() {
		return depDegree;
	}
}
