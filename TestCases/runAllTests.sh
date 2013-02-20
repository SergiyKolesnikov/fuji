#!/bin/bash
# Run all tests

# for all folders: call runTests.sh

TESTCASES="ExtendsImplements \
           FieldAccess"

for TESTCASE in $TESTCASES; do
  echo "Testing $TESTCASE ..."
  cd $TESTCASE
  ./runTests.sh
  cd ..
done
echo "All tests done."