public class Main implements UnInterface {
    public UnInterface x;

    Main() {
        this.x = this;
    }

    public int eval() {
        return 42;
    }

    public static void main(String[] args) {
        System.out.println(new Main().x.eval());
    }
}
