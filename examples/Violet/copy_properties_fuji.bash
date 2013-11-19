#!/bin/sh
. ../../scripts/global_vars
if [ -d "$TMPCLASSOUT" ]; then 
    /bin/cp -rf violet_resources/* "$TMPCLASSOUT"
else
    echo "No classes directory found.  Compile with -d $TMPCLASSOUT"
fi
