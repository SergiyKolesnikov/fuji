package com.horstmann.violet.framework;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public interface Node extends java.lang.Cloneable, java.io.Serializable {
    @Stub
    java.awt.geom.Point2D getConnectionPoint(com.horstmann.violet.framework.Direction d);
    @Stub
    java.awt.geom.Rectangle2D getBounds();
}
