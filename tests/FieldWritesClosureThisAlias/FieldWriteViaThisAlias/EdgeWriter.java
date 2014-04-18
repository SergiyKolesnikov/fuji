public class EdgeWriter { 

    public void writeEdge(Object o) {
	Object obj = (Object) o;
	write(obj);
    }

    public void write(Object o) {
	if(o instanceof Edge) {
	    Edge e = (Edge) o;
	    e.setW(99);
	}
    }
    
}
