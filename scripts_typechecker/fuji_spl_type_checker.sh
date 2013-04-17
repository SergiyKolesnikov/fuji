#!/bin/bash
java -jar ../FujiCompiler/build/fuji.jar \
     -basedir $1 \
     -fopRefs \
     -typechecker \
     $1/*.model
