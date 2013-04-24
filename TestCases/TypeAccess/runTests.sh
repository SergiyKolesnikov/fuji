#!/bin/bash
# Run all tests

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
  ./runTests.sh
  cd ..
done
echo " All TypeAccess tests done."
