#!/bin/bash

caseStudyNames=("ZipMe" "EPL" "GPL" "Graph" "GUIDSL" "Notepad" "PKJab" "Prevayler" "Raroscope" "Sudoku" "Violet" "TankWar")
caseStudyFeaturePaths=( \
	"./subjectSystems/ZipMe/features/" \
 	"./subjectSystems/EPL/" \
 	"./subjectSystems/GPL/" \
 	"./subjectSystems/Graph/" \
 	"./subjectSystems/GUIDSL/" \
 	"./subjectSystems/Notepad/" \
 	"./subjectSystems/PKJab/" \
 	"./subjectSystems/Prevayler/" \
 	"./subjectSystems/Raroscope/" \
 	"./subjectSystems/Sudoku/" \
 	"./subjectSystems/Violet/" \
 	"./subjectSystems/TankWar/" \
)

numRepetitions=10

for repetition in `seq 1 $numRepetitions`
do
	rm -f results_repetition$repetition.tar.gz
	rm -rf result_repetition$repetition
done
for repetition in `seq 1 $numRepetitions`
do
	echo "repetition$repetition"
	date #print current date/time
	
	./cleanResults.sh
	
	i=0
	for csname in "${caseStudyNames[@]}"
	do
		path=${caseStudyFeaturePaths[$i]}
		echo "name:$csname path:$path"
		./TypeCheckAllFeaturesExtTimerSep.sh $csname $path
		./TypeCheckAllProductsExtTimerSep.sh $csname $path
		
		i=$((i+1))
	done

	./saveResults.sh
	mv resultBackup results_repetition$repetition
	tar -czf results_repetition$repetition.tar.gz results_repetition$repetition
done
