class A {
    void aMethod(java.util.List<String> l, Object o) {}

    class Inner {
        int innerField = 0;
        Object innerMethod() {
            return new Object() {
                public int[] anonymousField;
                public void anonymousMethod() {}
            };
        }
    }

    static class Nested {
        int nestedField = 1;
        void nestedMethod() {
            class Local {
                public int localField;
                public void localMethod() {}
            }
        }
    }

    A (Object o) {}
}
