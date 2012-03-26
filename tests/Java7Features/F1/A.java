import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

class A {
    public static void main(String[] argv) {

        // Strings in switch
        String s = "foo";
        switch (s) {
        case "foo":
            break;
        case "bar":
            break;
        default:
        }

        // Binary integral literals
        int i = 1234_5678;

        // Underscores in numeric literals
        int j = 0B10;

        // Multi-catch and more precise rethrow
        try {
            if (true) {
                throw new IOException();
            } else {
                throw new RuntimeException();
            }
        } catch (IOException|RuntimeException ex) {}

        // More precise rethrow
        try {
            morePreciseRethrow();
        } catch (Exception ex) {}

        // The try-with-resources Statement
        // Commented out because requires JRE 7.
        // try (SomeAutoCloseable sac = new SomeAutoCloseable()) {
        //         sac.doSmth();
        //     } catch (Exception ex) {}

        // Type Inference for Generic Instance Creation
        Map<String, List<String>> myMap = new HashMap<>();

        // Improved Compiler Warnings and Errors When Using Non-Reifiable Formal
        // Parameters with Varargs Methods
        // TODO
    }

    static void morePreciseRethrow() throws IOException, RuntimeException {
        try {
            if (true) {
                throw new IOException();
            } else {
                throw new RuntimeException();
            }
        } catch (Exception ex) {
            throw ex;
        }
    }
}
