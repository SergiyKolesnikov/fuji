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

i=0
for csname in "${caseStudyNames[@]}"
do
	path=${caseStudyFeaturePaths[$i]}
	echo "name:$csname path:$path"
	./TypeCheckAllFeaturesExtTimerSep.sh $csname $path
	./TypeCheckAllProductsExtTimerSep.sh $csname $path
	i=$((i+1))
done
