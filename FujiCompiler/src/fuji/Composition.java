package fuji;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import AST.CompilationUnit;
import AST.Options;
import AST.Program;

import fuji.SPLStructure.RoleGroup;

/**
 * Composition is a strategy context for composing visitors that may implement
 * different composition strategies. It manages the composing process of the
 * SPL. Composing algorithm is implemented in a concrete visitor strategy.
 */
public class Composition {

    private CompositionContext compositionContext;

    /**
     * Composition constructor.
     * 
     * @param compContext
     *            an instance of CompositionContext implementation that provides
     *            all the data and methods needed by this strategy context
     *            (e.g.: spl structure, factory method for
     *            concrete strategies).
     * @param spl
     *            a representation of an SPL to be composed.
     */
    public Composition(CompositionContext compContext) {
        compositionContext = compContext;
    }

    /**
     * Return an iterator over the ASTs build for the SPL's dependency graphs.
     * ASTs for graphs with least dependencies are returned first.
     * 
     * @return an iterator over the ASTs build for the SPL's dependency graphs.
     */
    public ASTIterator getASTIterator() {
        return this.new ASTIterator();
    }

    /*
     * Initializes an AST and returns it.
     */
    private Program initAST() {
        Program program = new Program();
        program.state().reset();
        program.initBytecodeReader(new AST.BytecodeParser());
        program.initJavaParser(new AST.JavaParser() {
            public CompilationUnit parse(java.io.InputStream is, String fileName)
                    throws java.io.IOException, beaver.Parser.Exception {
                return new parser.JavaParser().parse(is, fileName);
            }
        });
        initBackboneCompilerOptions(program);
        program.options().addOptions(
                compositionContext.getBackboneCompilerArgs());
        return program;
    }

    private void initBackboneCompilerOptions(Program program) {
        Options options = program.options();
        options.initOptions();
        options.addKeyValueOption("-" + Main.OptionName.CLASSPATH);
        options.addKeyValueOption("-" + Main.OptionName.BOOTCLASSPATH);
        options.addKeyValueOption("-" + Main.OptionName.EXTDIRS);
        options.addKeyValueOption("-" + Main.OptionName.SOURCEPATH);
        options.addKeyValueOption("-" + Main.OptionName.D);
        options.addKeyOption(AST.IntrosRefsUtil.ALLOW_MULTIPLE_DECLARATIONS);
    }

    /*
     * Compose the given role group. The resulting compilation unit will be a
     * part of the given AST.
     */
    private void composeRoleGroup(Program ast, RoleGroup rg) {

        Collection<String> refinementPaths = rg.calculateRefinementPathnames();

        for (String path : refinementPaths) {
            ast.addSourceFile(path);
        }

        CompilationUnit baseCU = null;
        Collection<CompilationUnit> refCUs = new ArrayList<CompilationUnit>();
        @SuppressWarnings("unchecked")
        Iterator<CompilationUnit> cuIter = ast.compilationUnitIterator();

        /* Get CompilationUnits for the roles. */
        while (cuIter.hasNext()) {
            CompilationUnit cu = cuIter.next();
            if (cu.fromSource()) {
                cu.setFromRole(true);
                try {
                    cu.setFeatureID(compositionContext.getSPLStructure()
                            .determineFeatureID(cu));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (cu.pathName() != null
                        && cu.pathName().equals(rg.getBasePathname())) {
                    baseCU = cu;
                } else if (refinementPaths.contains(cu.pathName())) {
                    refCUs.add(cu);
                }
            }
        }

        /* Compose refinements. */
        for (CompilationUnit refCU : refCUs) {
            if (!baseCU.accept(compositionContext.getComposingVisitor(), refCU))
                throw new RuntimeException("Composition of '"
                        + baseCU.relativeName() + "' and '"
                        + refCU.relativeName() + " failed!");
        }
    }

    /**
     * This iterator triggers the generation of an AST for each dependency graph
     * and iterates over this ASTs.
     */
    private class ASTIterator implements Iterator<Program> {

        private Iterator<Collection<RoleGroup>> graphIterator;

        /**
         * Default constructor.
         */
        public ASTIterator() {
            graphIterator = compositionContext.getSPLStructure()
                    .getDependencyGraphs().iterator();
        }

        @Override
        public boolean hasNext() {
            return graphIterator.hasNext();
        }

        /**
         * Triggers the generation of an AST for the next dependency graph and
         * returns the generated AST. During generation all the base roles in
         * the next dependency graph get a CompilationUnit and this is composed
         * with CompilationUnits of the corresponding refining roles. The
         * CompilationUnits for the refining roles stay in the AST but their
         * fromSource flag is set to false so the fromSource() returns false
         * too. Thus they can be excluded from bytecode generation process by
         * checking the fromSource property.
         * 
         * @return an AST for the next dependency graph.
         */
        @Override
        public Program next() {
            Collection<RoleGroup> graph = graphIterator.next();
            Program ast = initAST();

            /*
             * Add base roles to the AST.
             * 
             * NOTE: base roles must be added to the AST before any refinement
             * to guarantee correct name resolution and such.
             */
            Collection<RoleGroup> rgsToCompose = new ArrayList<RoleGroup>();
            for (RoleGroup rg : graph) {
                if (rg.composed) {
                    continue;
                }
                rgsToCompose.add(rg);
                // ast.addSourceFile(rg.getBaseRelativePathname());
                ast.addSourceFile(rg.getBasePathname());
            }

            /* Compose base roles with their refinements. */
            for (RoleGroup rg : rgsToCompose) {
                composeRoleGroup(ast, rg);
                rg.composed = true;
            }
            return ast;
        }

        /**
         * This operation is not supported.
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
