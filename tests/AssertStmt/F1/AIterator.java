import java.util.Iterator;

class AIterator implements Iterator {

    public static void main(String[] argv) {
        new AIterator();
    }

    public Integer next() {
        assert true;
        return null;
    }

    public boolean hasNext() { return true; }
    public void remove() {}
}
