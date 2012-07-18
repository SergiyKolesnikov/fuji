#!/bin/sh
#
# The script compiles variants for the given SPL and a list of configurations.
#
# $1 - basedir of the SPL
#
# $2-$n - configuration files
SCRIPTDIR=$(dirname $(readlink -fn "$0"))
FUJI="$SCRIPTDIR/../../FujiCompiler/build/fuji.jar"

if [ ! -e "$FUJI" ]; then
    echo "Fuji not found ($FUJI)"
fi

BDIR="$1"
shift
for CONF in "$@"; do
    echo -------------------------------------------
    echo "$CONF"
    DESTDIR=$(basename "$CONF")".classes"
    java -jar "$FUJI" -basedir "$BDIR" -d "$DESTDIR" "$CONF"
done
