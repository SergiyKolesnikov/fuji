#!/bin/bash
# Run all tests

#checking input-parameters
if [ $# -gt 1 ]; then
    echo "wrong number of arguments!"
    echo "    usage: sh runAllTests.sh [-csv]"
    exit
fi

# for all folders: call runTests.sh

TESTCASES="cast \
           constructor \
           extends \
           generics \
           implements \
           innerClasses \
           parameter \
           return \
           tryCatchThrows"

for TESTCASE in $TESTCASES; do
  echo " Testing $TESTCASE ..."
  cd $TESTCASE
  if [ "$1" == "-csv" ]; then
    ./runTests.sh -csv
  else
    ./runTests.sh
  fi
  cd ..
done
echo " All TypeAccess tests done."
