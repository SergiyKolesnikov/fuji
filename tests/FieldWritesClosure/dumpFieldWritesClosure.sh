#!/bin/sh
set -e
FUJI="java -jar ../../FujiCompiler/build/fuji.jar"
eval "$FUJI" -compstrategy family -constWrites Test.features
