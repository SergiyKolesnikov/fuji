class Main<T extends Integer> {} // Fehlt ref auf Integer

class A<T> {
    void f1(A<? extends Long> a) {
        A<String> aa;
    }
}

class B<T> extends A<Boolean> implements I<Character> {
    void f2(A<? super Float> a) {
        A<int[]> aa;
    }
}

interface I<T> {}

interface II<T extends Byte> extends I<Short> {}
