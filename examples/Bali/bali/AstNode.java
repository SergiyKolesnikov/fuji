

import Jakarta.util.Util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogRecord;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

//-----------------------------------------------------------------------//
// Kernel classes generated by GenCT (manually modified):
//-----------------------------------------------------------------------//

public abstract class AstNode {
    public void execute() {
        if ( arg != null )
            for ( int n = 0 ; n < arg.length ; ++n )
                if ( arg[n] != null )
                    arg[n].execute() ;
    }
}
