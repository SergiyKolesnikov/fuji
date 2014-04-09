package p1.p2;

public class Node {

    public String[] name;

    public Node() {
        this.name = new String[1]; // write
        init();
        String n = name[0];
        Node name = new Node();
        name.name[0] = n; // write
        n = name.name[0];
    }

    public void init() {
        name[0] = "init"; // write
    }
}
