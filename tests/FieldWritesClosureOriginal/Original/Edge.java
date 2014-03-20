// Original
public class Edge {

   private Node head = null;

   public Edge(int weight, Node h) {

       // Fall: schreibender Zugriff auf Instanzvariable ueber Original-Call
       this.setHead(h);

       // egal, da lesender Zugriff
       System.out.println(""+this.getHead());

   }

    // Fall: schreibender Zugriff ueber Original-Call
    public void setHead(Node h) {
        original(h);
    }

    // egal, da lesender Zugriff
    public Node getHead() {
        return original();
    }

}
