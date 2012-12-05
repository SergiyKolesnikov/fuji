package fuji;

import java.util.HashSet;

import AST.SimpleSet;

/** Keeps info about:<ul><li>the accesses to implementations of a certain type, 
 * and</li><li>source file names and line numbers 
 * of accesses of a certain type</li></ul> */
public class TypeErrorInfo {
	
	/* Accesses to implementations of a certain type */
	private SimpleSet decls;
	
	/* source file names and line numbers of accesses of a certain type */
	private HashSet<String> accessSourceInfo = new HashSet<String>();
	
	/* is the access going to intern (access without a dot)? */
	private boolean isUndottedAccess;
	
	public TypeErrorInfo(SimpleSet decls, 
			HashSet<String> accessSourceInfo, boolean isUndottedAccess) {
		this.decls = decls;
		this.accessSourceInfo = accessSourceInfo;
		this.isUndottedAccess = isUndottedAccess;
	}

	public SimpleSet getDecls() {
		return decls;
	}

	public void setDecls(SimpleSet decls) {
		this.decls = decls;
	}

	public HashSet<String> getAccessSourceInfo() {
		return accessSourceInfo;
	}

	public void setAccessSourceInfo(HashSet<String> accessSourceInfo) {
		this.accessSourceInfo = accessSourceInfo;
	}

	public boolean isUndottedAccess() {
		return isUndottedAccess;
	}

	public void setUndottedAccess(boolean isUndottedAccess) {
		this.isUndottedAccess = isUndottedAccess;
	}
}
