#!/bin/sh

# $1 is case study name
# $2 is relative path to the features directory

(( CpuTimeCompositionAll=0 ))
(( CpuTimeTypeCheckAll=0 ))
(( CpuTimeCompositionCurrent=0 ))
(( CpuTimeTypeCheckCurrent=0 ))

echo 'variant	usertime	systemtime' > $1/exttimetypechecker.csv

echo 'variant	ASTcomp	typecheck	errors' > $1/inttimetypechecker.csv

# re-generate fuji.jar
cd ../FujiCompiler/;
ant jar;
cd ../benchmark/;
cp ../FujiCompiler/build/fuji.jar fuji.jar;

#classpath
if [ ! -f "$2/classpathjars.txt" ]
then
  extraClasspath=""
else
	extraClasspath="-cp ."
	FILE=`cat "$2/classpathjars.txt"`
	for jarfile in $FILE;
	do
		extraClasspath="$extraClasspath:./../examples/lib/$jarfile"
	done
	echo "classpath: $extraClasspath"
fi

# run typecheck on variants
for variantdir in $1/products/Variant*
do
  for variantmodel in $variantdir/Variant*.model
  do
	variantname=`basename $variantmodel`
	/usr/bin/time \
			-f $variantname\\t%U\\t%S \
			-o $1/exttimetypechecker.csv \
			--append \
			--quiet \
		 java -jar fuji.jar \
		 $extraClasspath \
		 -timer \
		 -novariability \
         -fopRefs \
         -typechecker \
         -basedir $2 \
         $variantmodel \
         > $variantdir/stdout.txt \
         2> $variantdir/errout.txt
    TimeCompositionCurrent=$(grep "Time_AST_construction_ms: " $variantdir/stdout.txt | egrep -o "[0-9]+")
    FoundErrors=$(grep "Found_Errors: " $variantdir/stdout.txt | egrep -o "[0-9]+")
    TimeTypeCheckCurrent=$(grep "Time_typecheck_ms: " $variantdir/stdout.txt | egrep -o "[0-9]+")
    echo "$variantmodel done. AST: $((TimeCompositionCurrent)) ms, spl error check: $((TimeTypeCheckCurrent)) ms, errors: $((FoundErrors))."
    echo "$variantname	$TimeCompositionCurrent	$TimeTypeCheckCurrent	$FoundErrors" >> $1/inttimetypechecker.csv
  done
done

echo "typechecking family"
# time option --quiet means do not report program status (error or normal termination)
 /usr/bin/time \
			-f family\\t%U\\t%S \
			-o $1/exttimetypechecker.csv \
			--append \
			--quiet \
	 java -jar fuji.jar \
	 -timer \
	 -fopRefs \
     -typechecker \
     -basedir $2 \
     $1/model.m \
     > $1/familystdout.txt \
     2> $1/familyerrout.txt
     
 TimeCompositionCurrent=$(grep "Time_AST_construction_ms: " $1/familystdout.txt | egrep -o "[0-9]+")
 FoundErrors=$(grep "Found_Errors: " $1/familystdout.txt | egrep -o "[0-9]+")
 TimeTypeCheckCurrent=$(grep "Time_typecheck_ms: " $1/familystdout.txt | egrep -o "[0-9]+")
 echo "family done. AST: $((TimeCompositionCurrent)) ms, spl error check: $((TimeTypeCheckCurrent)) ms, errors: $((FoundErrors))."
 echo "family	$TimeCompositionCurrent	$TimeTypeCheckCurrent	$FoundErrors" >> $1/inttimetypechecker.csv
