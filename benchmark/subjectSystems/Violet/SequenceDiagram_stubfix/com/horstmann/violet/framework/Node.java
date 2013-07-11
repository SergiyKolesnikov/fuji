package com.horstmann.violet.framework;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public interface Node {
    @Stub
    boolean contains(java.awt.geom.Point2D aPoint);
    @Stub
    java.awt.geom.Point2D getConnectionPoint(com.horstmann.violet.framework.Direction d);
    @Stub
    void draw(java.awt.Graphics2D g2);
    @Stub
    com.horstmann.violet.framework.Node getParent();
    @Stub
    void translate(double dx, double dy);
    @Stub
    java.awt.geom.Rectangle2D getBounds();
    @Stub
    java.util.List getChildren();
    @Stub
    void layout(com.horstmann.violet.framework.Graph g, java.awt.Graphics2D g2, com.horstmann.violet.framework.Grid grid);
}
