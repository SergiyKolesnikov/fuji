public class Node {

    private String name;

    public Node(String name) {
        this.name = name;
    }

    public class Edge {
        private Node head;
        private Node tail;

        public Edge(Node h, Node t) {
            head = h;
            tail = t;
        }

        public void setHead(Node h) {
            head = h;
        }

        public Node getHead() {
            return head;
        }
    }
}
