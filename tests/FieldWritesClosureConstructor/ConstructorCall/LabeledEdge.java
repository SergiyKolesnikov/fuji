// ConstructorCall
public class LabeledEdge extends Edge {

    private int weight;

    public LabeledEdge(int w) {

        // Schreibender Zugriff erfolgt im Konstruktor von Base/Edge und im
        // Konstruktor refinement von ConstructorRefinement/Edge
        // Daher: super(.., ..)-> Edge(.., ...) -> schreibender Zugriff
        // auf die Instanzvariablen (hier "head" und "tail" in Base/Edge und
        // newHead, newTail in ConstructorRefinement/Edge).
        super(new Node(),new Node());

        weight = w;
    }
}
