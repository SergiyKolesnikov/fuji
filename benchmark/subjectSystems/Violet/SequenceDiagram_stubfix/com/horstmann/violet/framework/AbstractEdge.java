package com.horstmann.violet.framework;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public abstract class AbstractEdge implements com.horstmann.violet.framework.Edge {
    @Stub
    public com.horstmann.violet.framework.Node getEnd() {
        return null;
    }
    @Stub
    public void connect(com.horstmann.violet.framework.Node aStart, com.horstmann.violet.framework.Node anEnd) {
        return ;
    }
    @Stub
    public com.horstmann.violet.framework.Node getStart() {
        return null;
    }
    @Stub
    public void draw(java.awt.Graphics2D g2) {
        return ;
    }
    @Stub
    public java.awt.geom.Rectangle2D getBounds(java.awt.Graphics2D g2) {
        return null;
    }
}
