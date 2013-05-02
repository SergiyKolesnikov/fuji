import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
import java.lang.Object;
import java.lang.Cloneable;
import java.lang.CloneNotSupportedException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.lang.ClassNotFoundException;
public class Board implements java.lang.Cloneable, java.io.Serializable {
    @Stub
    public Field[] board;
}
