caseStudyNames=("ZipMe" "EPL" "GPL" "Graph" "GUIDSL" "Notepad" "PKJab" "Prevayler" "Raroscope" "Sudoku" "TankWar" "Violet")
caseStudyFeaturePaths=(
	"../examples/ZipMe/features/" \
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
for csname in "${caseStudyNames[@]}"
do
	path=${caseStudyFeaturePaths[$i]}
	echo "name:$csname path:$path"
	./TypeCheckAllProductsExtTimerSep.sh $csname $path
	i=$((i+1))
done
