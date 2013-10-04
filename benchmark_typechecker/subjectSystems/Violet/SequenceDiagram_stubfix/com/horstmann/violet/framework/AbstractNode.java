package com.horstmann.violet.framework;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public class AbstractNode implements com.horstmann.violet.framework.Node {
    @Stub
    public java.util.List getChildren() {
        return null;
    }
    @Stub
    public com.horstmann.violet.framework.Node getParent() {
        return null;
    }
    @Stub
    public void draw(java.awt.Graphics2D g2) {
        return ;
    }
    @Stub
    public void layout(com.horstmann.violet.framework.Graph g, java.awt.Graphics2D g2, com.horstmann.violet.framework.Grid grid) {
        return ;
    }
    @Stub
    public java.awt.geom.Point2D getConnectionPoint(com.horstmann.violet.framework.Direction d) {
        return null;
    }
    @Stub
    public void translate(double dx, double dy) {
        return ;
    }
    @Stub
    public java.awt.geom.Rectangle2D getBounds() {
        return null;
    }
    @Stub
    public void addChild(int index, com.horstmann.violet.framework.Node node) {
        return ;
    }
    @Stub
    public boolean contains(java.awt.geom.Point2D aPoint) {
        return true;
    }
    @Stub
    public void removeChild(com.horstmann.violet.framework.Node node) {
        return ;
    }
}
