
public class LabeledEdge extends Edge { 

    private int weight; // dummy, not used in example

    public LabeledEdge(int w) {

	// 3. Fall: schreibender Zugriff erfolgt im Konstruktor von Node
        // Daher: super(.., ..)-> Edge(.., ...) -> schreibender Zugriff
        // auf die Instanzvariablen (hier "head" und "tail" in Edge.java
	super(new Node(),new Node());

	// dieser Aufruf ist "harmlos" bzw. egal
        // da hier im Super-Konstruktor keine Instanzvariablen geschrieben
        // werden
	super();

	weight = w;
    }
}
