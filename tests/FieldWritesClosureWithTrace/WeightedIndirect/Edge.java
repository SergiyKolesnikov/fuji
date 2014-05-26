
public class Edge { 

    
    private Weight weight;

    public Edge() {
        
	// 2. Fall: Method Access -> Schreibender Zugriff auf Instanzvariable
	m1(0);

	// "egal": Method Access -> Lesender Zugriff auf Instanzvariable
        m3(0);
	
   }

    public void m1(int w) {
	m2(w);
    }

    public void m2(int w) {
		this.weight = new Weight(w);
    }

    public void m3(int w) {
	m4(w);
    }

    public void m4(int w) {
	System.out.println(""+this.weight.toString()+w);
    }
}
