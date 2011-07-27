package Graph;

class Graph {
    public static void main(String[] args) {
        original(args);
        System.out.println("========= ColorGraph ========");
        Graph g = new Graph();
        g.add(new Node(5), new Node(6));
        g.add(new Node(7), new Node(8));
        g.print();
        System.out.println();
    }
}
