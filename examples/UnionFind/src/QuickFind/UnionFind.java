
public   class  UnionFind {
	
/*@spec_public*/   private int[] id;

	    // id[i] = parent of i
/*@spec_public*/   private int count;

	   // number of components
    
    // instantiate N isolated components 0 through N-1
    /*@
       requires N > 0;
       ensures count == N; 
	   ensures (\forall int i; 0 <= i && i < N;
id[i] == i);
     */
    public UnionFind(int N) {
        count = N;
        id = new int[N];
        for (int i = 0; i < N; i++)
        id[i] = i;
    }

	
    
    // return number of connected components
    /*@
      ensures \result == count;
     */
    public int count() {
        return count;
    }

	
	

	

	

	    // are elements p and q in the same component?
		/*@
		 	ensures \original_clause;
		 	ensures \result == (id[p] == id[q]);
		 @*/
	    public /*@pure@*/ boolean connected(int p, int q) {
	        return id[p] == id[q];
	    }

	

	   
	    /*@
	       \original_spec
	           
	     */
	    public void union  (int p, int q) {
	        if (connected(p, q)) return;
	        int pid = id[p];
	        for (int i = 0; i < id.length; i++)
	            if (id[i] == pid) id[i] = id[q]; 
	        count--;
	    }

	
	    public static void main(String[] args) {
	     	
	    	In in = new In("TestData/mediumUF.txt");
	        int N = in.readInt();
	        Stopwatch s = new Stopwatch();
	        UnionFind uf = new UnionFind(N);
	        
	        // read in a sequence of pairs of integers (each in the range 0 to N-1),
	         // calling find() for each pair: If the members of the pair are not already
	        // call union() and print the pair.
	        while (!in.isEmpty()) {
	            int p = in.readInt();
	            int q = in.readInt();
	            if (uf.connected(p, q)) continue;
	            uf.union(p, q);
	            //StdOut.println(p + " " + q);
	        }
	        StdOut.println(s.elapsedTime() + " time");
	        uf.count();
	        StdOut.println(uf.count() + " components");
	   
	    }


}
