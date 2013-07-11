package GPL;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public interface EdgeIfc {
    @Stub
    int getWeight();
    @Stub
    GPL.Vertex getOtherVertex(GPL.Vertex vertex);
    @Stub
    void adjustAdorns(GPL.EdgeIfc the_edge);
}
