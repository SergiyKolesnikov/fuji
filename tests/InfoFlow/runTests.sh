#!/bin/bash
# Runs tests.

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
RUNS=`ls -d */`

for I in $RUNS; do

	RUNNAME=${I%/}
    # Run test case.
    cd "$RUNNAME"
	
	# Try to compile
	/bin/rm -rf classes >/dev/null 2>&1
	java -jar ../../../FujiCompiler/build/fuji.jar -d classes all.features > tmp.out 2>&1
	OK=`diff -N -B emptyfile tmp.out`
	if [ "$OK" != "" ]; then
		echoFailed "$RUNNAME COMPILATION FAILED"
	else 
		# Calculate information flow
	    java -jar ../../../FujiCompiler/build/fuji.jar -if all.features > tmp.out 2>&1

	    # Compare the output of the test case with the expected output.
	    OK=`diff -B tmp.out "$RUNNAME.ok"`
	    if [ "$OK" == "" ]; then
		echoOK "$RUNNAME OK"
	    else
		echoFailed "$RUNNAME FAILED"
		echo "$OK"
		fi
	fi
	/bin/rm -rf classes >/dev/null 2>&1
    cd ..
done
