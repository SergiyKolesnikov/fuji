#!/bin/bash
caseStudyNames=("ZipMe" "EPL" "GPL" "Graph" "GUIDSL" "Notepad" "PKJab" "Prevayler" "Raroscope" "Sudoku" "Violet" "TankWar")

echo "cleaning result dirs"
for csname in "${caseStudyNames[@]}"
do
	rm -f $csname/inttimetypechecker.csv
	rm -f $csname/inttimetypechecker_featurebased.csv
	rm -f $csname/exttimetypechecker.csv
	rm -f $csname/exttimetypechecker_featurebased.csv
	rm -f $csname/familyerrout.txt 
	rm -f $csname/familystdout.txt 
	for variantdir in $csname/FeatureConfigs/Config*;
	do
		rm -f $variantdir/stdout.txt
		rm -f $variantdir/errout.txt
	done
	for variantdir in $csname/products/Variant*;
	do
		rm -f $variantdir/stdout.txt
		rm -f $variantdir/errout.txt
	done
done
echo "done cleaning"
