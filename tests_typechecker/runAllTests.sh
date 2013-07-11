#!/bin/bash
# Run all tests

# for all folders: call runTests.sh

TESTCASES="FieldAccess \
           MethodAccess \
           TypeAccess \
           OptInfoAssignmentWithCast \
           OptInfoAssignmentWithCast2Steps \
           OptInfoExtends \
           OptInfoExtends2Steps \
           OptInfoImplements \
           OptInfoImplements2Steps \
           OptInfoMethodInSupertypeAccess \
           OptInfoMethodInSupertypeAccess2Steps \
           OptInfoNewAssignmentWithCast \
           OptInfoNewAssignmentWithCast2Steps \
           OptInfoParameterCast \
           OptInfoParameterCast2Steps \
           OptInfoParameterCastOriginal \
           OptInfoSuperAndSuperConstructorAccess \
           OptInfoUpAndDownCast"

for TESTCASE in $TESTCASES; do
  echo "Testing $TESTCASE ..."
  cd $TESTCASE
  ./runTests.sh
  cd ..
done
echo "All tests done."