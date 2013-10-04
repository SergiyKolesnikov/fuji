package org.jastadd.jastaddj.flow;

import AST.*;

import java.util.*;
import java.io.*;

class JavaDeadAssignChecker extends Frontend {

	private static boolean takeTime = false;
	private static long totalTime = 0;

	public static void main(String args[]) {

		int time = -1;
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-time")) {
				time = i;
				takeTime = true;
				break;
			}	
		}
		
		if (time > -1) {
			String[] newArgs = new String[args.length-1];
			System.arraycopy(args,0,newArgs,0,time);
			System.arraycopy(args,time+1,newArgs,time,args.length-time-1);
			compile(newArgs);
		} else { 
			compile(args);
		}
	}

	public static boolean compile(String args[]) {
		boolean result = new JavaDeadAssignChecker().process(
				args,
				new BytecodeParser(),
				new JavaParser() {
					@Override
					public CompilationUnit parse(java.io.InputStream is,
							String fileName) throws java.io.IOException,
							beaver.Parser.Exception {

						return new parser.JavaParser().parse(is, fileName);
					}
				});
		if (takeTime) {
			System.out.println("Total time used for dead assignment analysis : " + 
				totalTime/1000 + "." + totalTime%1000 + "s");
		}
		return result;
	}

	protected void processNoErrors(CompilationUnit unit) {

		if (takeTime) {
			long startTime = System.currentTimeMillis();
			SmallSet<CFGNode> result = unit.deadAssignments();
			long dt = System.currentTimeMillis() - startTime;
			totalTime += dt;
		} else {
			for (CFGNode node : unit.deadAssignments()) {
				String packageName = node.hostType().fullName();
				int index = packageName.indexOf(".Anonymous");
				if (index != -1) {
					packageName = packageName.substring(0, index);
				}
				int kind = 1;
				if (node instanceof VariableDeclaration &&
					!((VariableDeclaration)node).hasInit()) {
					kind = 0;
				}
				String header = "DAE[" + kind + "][" + packageName + 
					":" + ((ASTNode)node).location() + "]:\n" + node;
				System.out.println(header);
			}
		}
  	}

	@Override
	protected String name() {
		return "JastAddJ-IntraFlow";
	}

	@Override
	protected String version() {
		return "To be added";
	}
}
