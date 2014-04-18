
public class Edge {

    private int w = 0;

    public Edge() {
	EdgeW edgeW = new EdgeW();
        edgeW.write(this);
    }

    public void setW(int x) {
	this.w = x;
    }

    private class EdgeW {
        public void write(Edge e) {
	    e.setW(99);
        }
    }
}
