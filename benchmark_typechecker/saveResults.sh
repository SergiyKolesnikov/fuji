#!/bin/bash
caseStudyNames=("ZipMe" "EPL" "GPL" "Graph" "GUIDSL" "Notepad" "PKJab" "Prevayler" "Raroscope" "Sudoku" "Violet" "TankWar")

rm -rf resultBackup
mkdir resultBackup
for csname in "${caseStudyNames[@]}"
do
		mkdir resultBackup/$csname
		mkdir resultBackup/$csname/FeatureConfigs
		mkdir resultBackup/$csname/products
		if [ -f "$csname/inttimetypechecker.csv" ]
		then
			echo "copying product based $csname"
			cp $csname/inttimetypechecker.csv resultBackup/$csname/inttimetypechecker.csv
			cp $csname/exttimetypechecker.csv resultBackup/$csname/exttimetypechecker.csv
			for variantdir in $csname/products/Variant*;
			do
				mkdir resultBackup/$variantdir
				cp $variantdir/stdout.txt resultBackup/$variantdir
				cp $variantdir/errout.txt resultBackup/$variantdir
			done
		else
			echo "could not copy product based $csname; file not found"
		fi
		if [ -f "$csname/inttimetypechecker_featurebased.csv" ]
		then
			echo "copying feature based $csname"
			cp $csname/inttimetypechecker_featurebased.csv resultBackup/$csname/inttimetypechecker_featurebased.csv
			cp $csname/exttimetypechecker_featurebased.csv resultBackup/$csname/exttimetypechecker_featurebased.csv
			for variantdir in $csname/FeatureConfigs/Config*;
			do
				mkdir resultBackup/$variantdir
				cp $variantdir/stdout.txt resultBackup/$variantdir
				cp $variantdir/errout.txt resultBackup/$variantdir
			done
		else
			echo "could not copy feature based $csname; file not found"
		fi
		mkdir resultBackup/$csname/family/
		if [ -f "$csname/familyerrout.txt" ]
		then
			echo "copying family based $csname"
			cp $csname/familyerrout.txt resultBackup/$csname/family/familyerrout.txt
			cp $csname/familystdout.txt resultBackup/$csname/family/familystdout.txt
		else
			echo "could not copy family based $csname; file not found"
		fi
done
