#!/bin/bash
# Run tests

# for all folders:
# call java -jar FujiProject/FujiCompiler/build/fuji.jar -fopRefs -typechecker -basedir $folder $folder/model.m
# diff output with expectedErrors.txt -> diff = 0 -> OK, otherwise -> TestFailed
# call java -jar FujiProject/FujiCompiler/build/fuji.jar -fopRefs -typechecker -basedir $folder $folder/model-ok.m
# there should be no output -> OK, otherwise -> TestFailed

# outputFolderName: OK or TestFailed