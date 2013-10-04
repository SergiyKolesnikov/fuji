package com.horstmann.violet.framework;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public abstract class AbstractNode implements com.horstmann.violet.framework.Node {
    @Stub
    public java.awt.geom.Rectangle2D getBounds() {
        return null;
    }
    @Stub
    public void addChild(com.horstmann.violet.framework.Node node) {
        return ;
    }
    @Stub
    public void addChild(int index, com.horstmann.violet.framework.Node node) {
        return ;
    }
    @Stub
    public void draw(java.awt.Graphics2D g2) {
        return ;
    }
    @Stub
    public java.util.List getChildren() {
        return null;
    }
    @Stub
    public java.awt.geom.Point2D getConnectionPoint(com.horstmann.violet.framework.Direction d) {
        return null;
    }
}
