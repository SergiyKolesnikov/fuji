#!/bin/bash

DIR=`pwd`

LOG="$DIR/plotLog.txt"
EXT=".pdf"

rm -rf $LOG
find . -name "*"$EXT -exec rm -rf {} \;

for SPL in `ls -I '*.*' .`; do # | grep EPL
	echo "::::::::: $SPL"

	cd "$SPL"
	
	R --vanilla --silent < ../../plot.r >> $LOG

	cd "$DIR"
done


find . -name "*"$EXT -exec mv {} . \;
