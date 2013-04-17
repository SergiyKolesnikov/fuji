#!/bin/bash

(( CpuTimeCompositionAll=0 ))
(( CpuTimeTypeCheckAll=0 ))
(( CpuTimeCompositionCurrent=0 ))
(( CpuTimeTypeCheckCurrent=0 ))

echo 'variant	usertime	systemtime' > $1/exttimetypechecker.txt

echo 'variant	ASTcomp	typecheck' > $1/inttimetypechecker.txt

# re-generate fuji.jar
cd ../FujiCompiler/
ant jar
cd ../benchmark/
cp ../FujiCompiler/build/fuji.jar fuji.jar

for variantdir in $1/products/Variant*
do
  for variantmodel in $variantdir/Variant*.model
  do
	variantname=`basename $variantmodel`
    #(( CpuTimeCompositionCurrent=0 ))
    #(( CpuTimeTypeCheckCurrent=0 ))
	/usr/bin/time \
			-f $variantname\\t%U\\t%S \
			-o $1/exttimetypechecker.txt \
			--append \
		 java -jar fuji.jar \
         -fopRefs \
         -typechecker \
         -basedir ../examples/$1/features \
         $variantmodel \
         > $variantdir/stdout.txt \
         2> $variantdir/errout.txt
    TimeCompositionCurrent=$(grep "Time_AST_construction_ms: " $variantdir/stdout.txt | egrep -o "[0-9]+")
    TimeTypeCheckCurrent=$(grep "Time_typecheck_ms: " $variantdir/stdout.txt | egrep -o "[0-9]+")
    echo "$variantmodel done. AST: $((TimeCompositionCurrent)) ms, spl error check: $((TimeTypeCheckCurrent)) ms."
    echo "$variantname	$TimeCompositionCurrent	$TimeTypeCheckCurrent" >> $1/inttimetypechecker.txt
    
  done
done
#echo "construct AST:   $((CpuTimeCompositionAll)) ms"
#echo "spl error check: $((CpuTimeTypeCheckAll)) ms"
