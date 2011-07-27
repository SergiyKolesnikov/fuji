package Graph;

class Edge {
    Color color = new Color(4711);

    void print() {
        Color.setDisplayColor(color);
        original();
    }
}
