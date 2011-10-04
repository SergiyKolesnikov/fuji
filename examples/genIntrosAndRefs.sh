#!/bin/sh

gen() {
    FUJI="../../FujiCompiler/build/fuji-nomod.jar"
    echo $1
    cd $1
    java -Xmx4000m -jar "$FUJI" -cp ".:$2" -fopIntroduces "$1"All.features \
	>../"$1".intros
    java -Xmx4000m -jar "$FUJI" -fopRefs "$1"All.features \
	>../"$1".refs
    cd ..
}

gen AJStats
gen BerkeleyDB-fuji-compilable "../lib/cide.jar"
gen EPL
gen GPL
gen GameOfLife "../lib/junit.jar"
gen GUIDSL "../lib/jakarta.jar:../lib/jdom.jar:../lib/sat4j.jar"
gen Notepad
gen PKJab
gen Prevayler "../lib/cide.jar:../lib/skaringa.jar"
gen TankWar
gen Violet
gen ZipMe
