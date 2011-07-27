// F2
package GPL;
import java.util.Iterator;
import java.util.LinkedList;

public class Vertex {
    public LinkedList f = new LinkedList();
    public void m() {
        new VertexIter() {
            Iterator i = f.iterator();
        };
    }
}
