

import Jakarta.util.*;
import java.io.*;
import java.util.*;

class OptTerm {

    public void visit( Visitor v ) {
        
        v.action( this );
    }

}
