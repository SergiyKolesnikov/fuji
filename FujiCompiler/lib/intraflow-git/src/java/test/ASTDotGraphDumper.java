package test;

import AST.BytecodeParser;
import AST.CompilationUnit;
import AST.Frontend;
import AST.JavaParser;

public class ASTDotGraphDumper extends Frontend {
	public static void main(String args[]) {
		dumpGraph(args);
	}

	public static boolean dumpGraph(String args[]) {
		boolean result = new ASTDotGraphDumper().process(
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
		System.out.println(unit.dumpASTDotGraph());
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
