package GPL;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public interface EdgeIfc {
    @Stub
    GPL.Vertex getStart();
    @Stub
    GPL.Vertex getOtherVertex(GPL.Vertex vertex);
    @Stub
    void adjustAdorns(GPL.EdgeIfc the_edge);
    @Stub
    GPL.Vertex getEnd();
    @Stub
    void setWeight(int weight);
    @Stub
    void display();
}
