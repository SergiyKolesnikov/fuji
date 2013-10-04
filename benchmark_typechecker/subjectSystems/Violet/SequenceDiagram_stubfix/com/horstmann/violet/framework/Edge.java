package com.horstmann.violet.framework;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public interface Edge {
    @Stub
    com.horstmann.violet.framework.Node getEnd();
    @Stub
    void draw(java.awt.Graphics2D g2);
    @Stub
    java.awt.geom.Rectangle2D getBounds(java.awt.Graphics2D g2);
    @Stub
    com.horstmann.violet.framework.Node getStart();
    @Stub
    void connect(com.horstmann.violet.framework.Node aStart, com.horstmann.violet.framework.Node anEnd);
}
