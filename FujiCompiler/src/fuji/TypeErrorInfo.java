package fuji;

import java.util.HashSet;

public class TypeErrorInfo {
	
	private HashSet<Integer> toFeatureIDs = new HashSet<Integer>();
	
	private HashSet<String> accessSourceInfo = new HashSet<String>();
	
	public TypeErrorInfo(HashSet<Integer> toFeatureIDs, 
			HashSet<String> accessSourceInfo) {
		this.toFeatureIDs = toFeatureIDs;
		this.accessSourceInfo = accessSourceInfo;
	}

	public HashSet<Integer> getToFeatureIDs() {
		return toFeatureIDs;
	}

	public void setToFeatureIDs(HashSet<Integer> toFeatureIDs) {
		this.toFeatureIDs = toFeatureIDs;
	}

	public HashSet<String> getAccessSourceInfo() {
		return accessSourceInfo;
	}

	public void setAccessSourceInfo(HashSet<String> accessSourceInfo) {
		this.accessSourceInfo = accessSourceInfo;
	}
}
