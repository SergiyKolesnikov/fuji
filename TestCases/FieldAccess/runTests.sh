#!/bin/bash
# Run tests

PATH_TO_FUJI_JAR="../../FujiCompiler/build/fuji.jar"

failed='\033[0;31m'
ok='\033[0;32m'
reset='\033[0m'

echoFailed() {
  echo -ne $failed"$1"$reset
}

echoOK() {
  echo -ne $ok"$1"$reset
}

# For all numbered folders in this folder:
for Dir in $(basename $(find . -depth 1 -type d -name "[0-9][0-9]*")); do

  # 01. Model with errors
  # ---------------------

  # Run type checker on test case, save error output
  java -jar $PATH_TO_FUJI_JAR \
       -fopRefs \
       -typechecker \
       -basedir $Dir \
       $Dir/model.m 2> $Dir/tmp.out

  # Clean output -> start path with "/$CurrentFolderName" 
  # sed "s/^\/.*\(\/[^\/.]*\/[^\/.]*\/[^\/.]*\/[^\/.]*\)/\1/" $Dir/tmp.out > $Dir/tmp2.out
  CURRENTFOLDER=`basename $PWD`
  sed "s/^.*\(\/$CURRENTFOLDER\/.*\)/\1/" $Dir/tmp.out > $Dir/tmp2.out

  # Compare the cleaned output of the test case with the expected output.
  OK=`diff "$Dir/tmp2.out" "$Dir/expectedErrors.txt"`
  if [ "$OK" == "" ]; then
    echoOK "$CURRENTFOLDER/$Dir - model with errors: OK   \t"
  else
    # are the errors just in different order?
    # sort them and compare
    sort $Dir/tmp2.out > $Dir/tmp2Sorted.out
    sort $Dir/expectedErrors.txt > $Dir/expectedSorted.txt
    OKSorted=`diff "$Dir/tmp2Sorted.out" "$Dir/expectedSorted.txt"`
    if [ "$OKSorted" == "" ]; then
      echoOK "$CURRENTFOLDER/$Dir - model with errors: OK   \t"
    else
      echoFailed "$CURRENTFOLDER/$Dir - model with errors: FAILED\n"
      echo "$OK"
    fi
  fi

  # 02. Corrected model (without errors)
  # ------------------------------------

  # Run type checker on test case, save error output
  java -jar $PATH_TO_FUJI_JAR \
       -fopRefs \
       -typechecker \
       -basedir $Dir \
       $Dir/model-ok.m 2> $Dir/tmp3.out

  # Test if file is empty
  if [ ! -s $Dir/tmp3.out ]; then
    echoOK "$CURRENTFOLDER/$Dir - corrected model: OK\n"
  else
    echoFailed "$CURRENTFOLDER/$Dir - corrected model: FAILED, check $Dir/tmp3.out for errors\n"
  fi
done