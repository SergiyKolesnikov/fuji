package fuji;

import AST.ComposingVisitor;

/**
 * An implementation of this interface provides all the data and methods needed
 * by the {@link Composition} strategy context (e.g.: spl structure, factory
 * method for concrete strategies).
 */
public interface CompositionContext {
    String[] getBackboneCompilerArgs();

    SPLStructure getSPLStructure();

    ComposingVisitor getComposingVisitor();
}
