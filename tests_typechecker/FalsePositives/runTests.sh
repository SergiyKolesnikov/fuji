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

# For all folders in this folder:
for Dir in `find -maxdepth 1 -mindepth 1 -type d`; do

  # Run type checker on test case, save error output
  java -jar $PATH_TO_FUJI_JAR \
       -compstrategy family \
       -typechecker \
       -basedir $Dir \
       $Dir/model.m 2> $Dir/tmp.out

  # Clean output -> start path with "/$PrevFolderName" 
  CURRENTFOLDER=`basename $PWD`
  PREVFOLDER=`basename $(dirname $PWD)`
  sed "s:$PWD:/$PREVFOLDER/$CURRENTFOLDER:g" $Dir/tmp.out > $Dir/tmp2.out

  # Compare the cleaned output of the test case with the expected output.
  OK=`diff "$Dir/tmp2.out" "$Dir/expectedErrors.txt"`
  if [ "$OK" == "" ]; then
    echoOK " $PREVFOLDER/$CURRENTFOLDER/$Dir OK   \t"
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
      echoOK " $PREVFOLDER/$CURRENTFOLDER/$Dir OK   \t"
    else
      echoFailed " $PREVFOLDER/$CURRENTFOLDER/$Dir FAILED\n"
      echo "$OK"
    fi
  fi
  echo
done
