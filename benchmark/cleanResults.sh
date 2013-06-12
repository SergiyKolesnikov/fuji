caseStudyNames=("ZipMe" "EPL" "GPL" "Graph" "GUIDSL" "Notepad" "PKJab" "Prevayler" "Raroscope" "Sudoku" "Violet" "TankWar")

for csname in "${caseStudyNames[@]}"
do
	rm $csname/inttimetypechecker.csv
	rm $csname/inttimetypechecker_featurebased.csv
	rm $csname/exttimetypechecker.csv
	rm $csname/exttimetypechecker_featurebased.csv
	for variantdir in $csname/FeatureConfigs/Config*;
	do
		rm $variantdir/stdout.txt
		rm $variantdir/errout.txt
	done
	for variantdir in $csname/products/Variant*;
	do
		rm $variantdir/stdout.txt
		rm $variantdir/errout.txt
	done
	
done
