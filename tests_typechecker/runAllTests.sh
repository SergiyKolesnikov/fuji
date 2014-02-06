#!/bin/bash
# Run all tests

# for all folders: call runTests.sh

#checking input-parameters
if [ $# -gt 1 ]; then
    echo "wrong number of arguments!"
    echo "    usage: sh runAllTests.sh [-csv]"
    exit
fi

TESTCASES="FalsePositives \
           FieldAccess \
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
  
  if [ "$1" == "-csv" ]; then
    ./runTests.sh -csv
  else
    ./runTests.sh
  fi
  cd ..
done
echo "All tests done."
