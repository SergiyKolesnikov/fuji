import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
import java.lang.System;
import java.io.PrintStream;
public class Main {
    @Stub
    public static boolean original(char o) {
        return true;
    }
    @Stub
    public static void original() {
        return ;
    }
}
