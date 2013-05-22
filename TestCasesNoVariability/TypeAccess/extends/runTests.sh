#!/bin/bash
# Run tests

# indentation of output by one space
# start path at previous folder

# added "../"
PATH_TO_FUJI_JAR="../../../FujiCompiler/build/fuji.jar"

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
for Dir in [0-9][0-9]*; do

  # 01. Model with errors
  # ---------------------

  # Run type checker on test case, save error output
  java -jar $PATH_TO_FUJI_JAR \
       -fopRefs \
       -typechecker \
       -basedir $Dir \
       $Dir/model.m 2> $Dir/tmp.out

  # Clean output -> start path with "/$PrevFolderName" 
  # sed "s/^\/.*\(\/[^\/.]*\/[^\/.]*\/[^\/.]*\/[^\/.]*\)/\1/" $Dir/tmp.out > $Dir/tmp2.out
  CURRENTFOLDER=`basename $PWD`
  PREVFOLDER=`basename $(dirname $PWD)`
  sed "s/^.*\(\/$PREVFOLDER\/.*\)/\1/" $Dir/tmp.out > $Dir/tmp2.out

  # Compare the cleaned output of the test case with the expected output.
  OK=`diff "$Dir/tmp2.out" "$Dir/expectedErrors.txt"`
  if [ "$OK" == "" ]; then
    echoOK " $PREVFOLDER/$CURRENTFOLDER/$Dir - model with errors: OK   \t"
  else
    # are the errors just in different order?
    # last colon of file name and line number output could be in different line
    # if there is more than one line of file name and line number output 
    # -> remove last colon in line (after a number)
    sed "s/\(^.*[0-9]\):/\1/" $Dir/tmp2.out > $Dir/tmp2tmp.out
    sed "s/\(^.*[0-9]\):/\1/" $Dir/expectedErrors.txt > $Dir/expectedtmp.txt
    # sort them and compare
    sort $Dir/tmp2tmp.out > $Dir/tmp2Sorted.out
    sort $Dir/expectedtmp.txt > $Dir/expectedSorted.txt
    OKSorted=`diff "$Dir/tmp2Sorted.out" "$Dir/expectedSorted.txt"`
    if [ "$OKSorted" == "" ]; then
      echoOK " $PREVFOLDER/$CURRENTFOLDER/$Dir - model with errors: OK   \t"
    else
      echoFailed " $PREVFOLDER/$CURRENTFOLDER/$Dir - model with errors: FAILED\n"
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
    echoOK "$PREVFOLDER/$CURRENTFOLDER/$Dir - corrected model: OK\n"
  else
    echoFailed "$PREVFOLDER/$CURRENTFOLDER/$Dir - corrected model: FAILED, check $Dir/tmp3.out for errors\n"
  fi
done