#!/bin/sh

gen() {
    FUJI="../../FujiCompiler/build/fuji.jar"
    CONFIGDIR=${3:-"."}"/"
    BASEDIR=${4:-"."}
    echo $1
    cd $1
    for I in "$CONFIGDIR"*All.features; do
	java -Xmx4000m -jar "$FUJI" -basedir "$BASEDIR" -cp ".:$2" \
	    -fopIntroduces "$I" >../`basename "$I"`.intros
	java -Xmx4000m -jar "$FUJI" -basedir "$BASEDIR" -cp ".:$2" \
	    -fopRefs "$I" >../`basename "$I"`.refs
    done
    cd ..
}

gen AHEAD "../lib/jakarta.jar:../lib/ant.jar"
gen AJStats
gen Bali "../lib/jakarta.jar"
gen BerkeleyDB-fuji-compilable "../lib/cide.jar"
gen EPL
gen GPL
gen GameOfLife "../lib/junit.jar"
gen GUIDSL "../lib/jakarta.jar:../lib/jdom.jar:../lib/sat4j.jar"
gen MobileMedia8-fuji-compilable "../lib/cide.jar:../lib/midpapi21.jar:../lib/cldcapi11.jar:../lib/wma20.jar:../lib/mmapi.jar"
gen Notepad
gen PKJab
gen Prevayler "../lib/cide.jar:../lib/skaringa.jar"
gen Raroscope
gen Sudoku
gen TankWar
gen Violet
gen ZipMe "" configs features
