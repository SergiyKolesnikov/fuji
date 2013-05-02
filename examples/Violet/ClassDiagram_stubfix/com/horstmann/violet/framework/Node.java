package com.horstmann.violet.framework;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public interface Node {
    @Stub
    java.awt.geom.Rectangle2D getBounds();
    @Stub
    void layout(com.horstmann.violet.framework.Graph g, java.awt.Graphics2D g2, com.horstmann.violet.framework.Grid grid);
}
