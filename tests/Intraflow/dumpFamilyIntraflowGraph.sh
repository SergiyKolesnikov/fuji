#!/bin/sh
set -e
FUJI="java -jar ../../FujiCompiler/build/fuji.jar"
eval "$FUJI" -intraflow -compstrategy family Test.features >Intraflow.dot
dot -Tpdf Intraflow.dot -O
evince Intraflow.dot.*pdf &
