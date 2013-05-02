#!/bin/bash

caseStudyNames=("ZipMe" "EPL" "GPL" "Graph" "GUIDSL" "Notepad" "PKJab" "Prevayler" "Raroscope" "Sudoku" "TankWar" "Violet")
caseStudyFeaturePaths=(
	"../examples/ZipMe/configs/" \
 	"../examples/EPL/" \
 	"../examples/GPL/" \
 	"../examples/Graph/" \
 	"../examples/GUIDSL/" \
 	"../examples/Notepad/" \
 	"../examples/PKJab/" \
 	"../examples/Prevayler/" \
 	"../examples/Raroscope/" \
 	"../examples/Sudoku/" \
 	"../examples/TankWar/" \
 	"../examples/Violet/"
)

i=0
for csname in "${caseStudyNames[@]}";
do
	echo "$csname"
	path=${caseStudyFeaturePaths[$i]}
	if [ ! -d "$csname/FeatureConfigs" ];
	then
		mkdir "$csname/FeatureConfigs"
	fi
	if [ ! -f "$path${csname}All.features" ];
	then
		echo "did not find All.features file: $path${csname}All.features"
	else
		FILE=`cat "$path${csname}All.features"`
		for feature in $FILE;
		do
			echo -e "\t$feature"
			if [ ! -d "$csname/FeatureConfigs/Config$feature" ];
			then
				mkdir "$csname/FeatureConfigs/Config$feature"
			fi
			echo -e "${feature}_stubfix\n${feature}" > "$csname/FeatureConfigs/Config$feature/Config$feature.model"
		done
	fi
	i=$((i+1))
done
