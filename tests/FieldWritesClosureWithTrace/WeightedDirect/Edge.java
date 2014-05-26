
public class Edge { 

    
    private Weight weight;

    public Edge(int weight) {
        
	// 1. Fall: Schreibender Zugriff auf Instanzvariable
	this.weight = new Weight(weight);

	// "egal": Lesender Zugriff auf Instanzvariable
        System.out.println(this.weight.toString());
	
    }
}
