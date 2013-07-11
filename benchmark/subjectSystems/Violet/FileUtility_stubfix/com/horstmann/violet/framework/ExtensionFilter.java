package com.horstmann.violet.framework;

import java.io.File;

import de.uni_passau.spl.bytecodecomposer.stubs.Stub;

public class ExtensionFilter extends javax.swing.filechooser.FileFilter {
	@Stub
	public boolean accept(File f) {
		return false;
	}
	@Stub
	public String getDescription() {
		return null;
	}
}
