
public class Edge { 


   private int dummyWeightValue = 0;

   public Edge(int weight, Node h) {

       // 4. Fall: schreibender Zugriff auf Instanzvariable ueber Original-Call
       this.setHead(h);
	
       // egal, da lesender Zugriff 
       System.out.println(""+this.getHead());

   }

    // 3. Fall: schreibender Zugriff ueber Original-Call
    public void setHead(Node h) {
	original(h);
    }

    // egal, da lesender Zugriff
    public Node getHead() {
	return original();
    }

}
