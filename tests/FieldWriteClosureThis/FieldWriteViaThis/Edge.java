public class Edge { 

    private int w = 0;


    public Edge() {
	EdgeWriter ew = new EdgeWriter();
	ew.writeEdge(this);
	
    }

    public void setW(int x) {
	this.w = x;
    }

}
