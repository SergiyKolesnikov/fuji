#!/bin/sh
if [ -d classes ]; then 
    /bin/cp -rf violet_resources/* classes
else
    echo "No classes directory found.  Compile with '-d classes' option."

fi
