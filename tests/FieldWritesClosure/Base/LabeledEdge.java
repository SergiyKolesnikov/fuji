
public class LabeledEdge extends Edge { 

    private String label = ""; // dummy and not used for exmaple

    public LabeledEdge(Node h, Node t) {
	super(h,t);
    }

    // Dummy-Konstruktor
    // egal, da _kein_ schreibener Zugriff
    // auf Instanzvariablen erfolgt
    public LabeledEdge() {
	System.out.println(label);
    } 
}
