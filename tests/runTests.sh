#!/bin/bash
# Runs tests.

TESTSCRIPTEXT='.test'
RUNSCRIPTEXT='.run'
failed='\E[37;41m'
ok='\E[37;44m'
reset='\E[0m'

echoFailed() {
    echo -e $failed"$1"$reset
}

echoOK() {
    echo -e $ok"$1"$reset
}

# Find all test cases having a run script.
if [ "$1" ]; then
    RUNS=`find "$1" -maxdepth 1 -iname "*$RUNSCRIPTEXT"`
else
    RUNS=`find . -maxdepth 2 -iname "*$RUNSCRIPTEXT"`
fi

for I in $RUNS; do

    # Compile the test case.
    DIR=`dirname $I`
    RUNSCRIPT=`basename $I`
    RUNNAME=${RUNSCRIPT%"$RUNSCRIPTEXT"}
    TESTSCRIPT="$RUNNAME$TESTSCRIPTEXT"
    ./$TESTSCRIPT

    # Run test case.
    cd "$DIR"
    ./$RUNSCRIPT

    # Compare the output of the test case with the expected output.
    OK=`diff tmp.out "$RUNNAME.ok" 2>&1`
    if [ "$OK" = "" ]; then
	echoOK "$TESTSCRIPT OK"
    else
	echoFailed "$TESTSCRIPT FAILED"
	echo "$OK"
    fi
    cd ..
done
