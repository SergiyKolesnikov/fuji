//F1
package some;
class A extends Object implements I { // 1. type ref: inheritance
                                      // 2. type ref: interface implimentation
    int j;
    String[] stringArray = new String[1]; // 3. type ref: variable definition
                                          // 4. type ref: array instantiantion
    void refinedInF2() {}

    void methodInF1(Long l, int i) { // 5. type ref: method parameters
        this.j = i; // 'i' is variable access but not a field access and must be
                    // ignored.  'this.j' is a field access and is counted.
    }

    public A methodI(){ // 6. type ref: return type
        return new A();
    }
}

