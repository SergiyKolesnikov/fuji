package test;

import AST.BytecodeParser;
import AST.CompilationUnit;
import AST.Frontend;
import AST.JavaParser;

public class DotGraphDumper extends Frontend {
	public static void main(String args[]) {
		dumpGraph(args);
	}

	public static boolean dumpGraph(String args[]) {
		boolean result = new DotGraphDumper().process(
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
		return result;
	}

	protected void processNoErrors(CompilationUnit unit) {
		//The body to be examined must be first in the input file
		System.out.println("digraph {");
		System.out.print(unit.getTypeDecl(0).getBodyDecl(0).dumpDotGraph());
		System.out.println("}");
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
