import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
import java.util.Random;
public class Board implements java.lang.Cloneable, java.io.Serializable {
    @Stub
    public Field[] board;
    @Stub
    public Field getField(Structure struct, int structNr, int element) {
        return null;
    }
    @Stub
    public boolean trySetField(Structure str, int strIndex, int element, Field f) {
        return true;
    }
    @Stub
    public int getIndex(Structure str, int nr, int ele) {
        return 0;
    }
    @Stub
    public java.lang.Object clone() throws CloneNotSupportedException {
        return null;
    }
}
