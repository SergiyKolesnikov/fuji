package com.horstmann.violet.framework;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public class AbstractNode implements com.horstmann.violet.framework.Node {
    @Stub
    public java.awt.geom.Rectangle2D getBounds() {
        return null;
    }
    @Stub
    public void draw(java.awt.Graphics2D g2) {
        return ;
    }
    @Stub
    public java.lang.Object clone() {
        return null;
    }
    @Stub
    public void translate(double dx, double dy) {
        return ;
    }
    @Stub
    public java.awt.geom.Point2D getConnectionPoint(com.horstmann.violet.framework.Direction d) {
        return null;
    }
    @Stub
    public boolean addEdge(com.horstmann.violet.framework.Edge e, java.awt.geom.Point2D p1, java.awt.geom.Point2D p2) {
        return true;
    }
}
