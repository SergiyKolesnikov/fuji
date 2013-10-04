#!/bin/sh
grep '^family' ../resultBackup_with_optimization_rev1132/*/inttimetypechecker.csv | awk 'BEGIN {sum=0}; {sum=sum+$4}; END{print "Family based: " sum}'
