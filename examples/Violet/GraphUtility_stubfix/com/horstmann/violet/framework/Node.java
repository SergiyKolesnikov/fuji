package com.horstmann.violet.framework;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
import java.lang.Cloneable;
import java.io.Serializable;
public interface Node extends java.lang.Cloneable, java.io.Serializable {
    @Stub
    java.awt.geom.Point2D getConnectionPoint(com.horstmann.violet.framework.Direction d);
    @Stub
    com.horstmann.violet.framework.Node getParent();
    @Stub
    void removeNode(com.horstmann.violet.framework.Graph g, com.horstmann.violet.framework.Node n);
    @Stub
    void removeChild(com.horstmann.violet.framework.Node node);
    @Stub
    void setParent(com.horstmann.violet.framework.Node node);
    @Stub
    void addChild(int index, com.horstmann.violet.framework.Node node);
    @Stub
    java.util.List getChildren();
    @Stub
    void removeEdge(com.horstmann.violet.framework.Graph g, com.horstmann.violet.framework.Edge e);
    @Stub
    boolean addEdge(com.horstmann.violet.framework.Edge e, java.awt.geom.Point2D p1, java.awt.geom.Point2D p2);
    @Stub
    boolean contains(java.awt.geom.Point2D aPoint);
    @Stub
    void draw(java.awt.Graphics2D g2);
    @Stub
    void translate(double dx, double dy);
    @Stub
    java.lang.Object clone();
    @Stub
    boolean addNode(com.horstmann.violet.framework.Node n, java.awt.geom.Point2D p);
    @Stub
    java.awt.geom.Rectangle2D getBounds();
    @Stub
    void layout(com.horstmann.violet.framework.Graph g, java.awt.Graphics2D g2, com.horstmann.violet.framework.Grid grid);
}
