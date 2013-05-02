package com.horstmann.violet.framework;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
import java.lang.Cloneable;
import java.io.Serializable;
public interface Edge extends java.lang.Cloneable, java.io.Serializable {
    @Stub
    com.horstmann.violet.framework.Node getEnd();
    @Stub
    void draw(java.awt.Graphics2D g2);
    @Stub
    java.awt.geom.Rectangle2D getBounds(java.awt.Graphics2D g2);
    @Stub
    java.awt.geom.Line2D getConnectionPoints();
    @Stub
    com.horstmann.violet.framework.Node getStart();
    @Stub
    boolean contains(java.awt.geom.Point2D aPoint);
    @Stub
    void connect(com.horstmann.violet.framework.Node aStart, com.horstmann.violet.framework.Node anEnd);
    @Stub
    java.lang.Object clone();
}
