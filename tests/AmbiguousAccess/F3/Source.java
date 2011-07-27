//F3
class Source {
    public static void main(String[] argv) {
        new Source().m();
    }

    void m() {
        /*
         * 'field' is AmbiguousAccess: package/field
         * It can't be resolved by looking into F2.Target as it doesn't contain
         * the 'target' variable.
         */
        String s = target.field.toString();
        System.out.println(this.i);
        System.out.println(s);
        System.out.println(target.field.toString());
    }
}
