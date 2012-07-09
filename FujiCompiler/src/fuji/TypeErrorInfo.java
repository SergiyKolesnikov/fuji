package fuji;

import java.util.HashSet;

/** Keeps info about:<ul><li>the IDs of the target features, 
 * that implement a certain type, and</li><li>source file names and line numbers 
 * of accesses of a certain type</li></ul>*/
public class TypeErrorInfo {
	
	/* IDs of the target features, that implement a certain type */
	private HashSet<Integer> toFeatureIDs = new HashSet<Integer>();
	
	/* source file names and line numbers of accesses of a certain type */
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
