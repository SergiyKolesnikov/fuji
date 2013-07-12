#!/bin/sh 

# Prepares benchmark results from resultBackup* (saveResults.sh) dirs for
# publishing.  

#Usage: ./prepareResults.sh outDir

BAKDIR="resultBackup_optimized_rev1148"
BAKDIR_NOOPT="resultBackup_family-based_no_optimization_rev1102"
OUTDIR=$1

if [ ! -d "$BAKDIR" ]; then
    echo "Error: Cannot find results backup directory: $BAKDIR"
    exit
fi

if [ ! -d "$BAKDIR_NOOPT" ]; then 
    echo "Error: Cannot find results backup directory: $BAKDIR_NOOPT"
    exit
fi

if [ -z "$1" ]; then
    echo "Error: Specify an output directory."
    exit
fi

if [ ! -d "$1" ]; then 
    echo "Error: Cannot find output directory: $1"
    exit
fi

set -e

# Prepare measurements.
MSMOUTDIR="measurements"
for SPLBAKDIR in `find -L "$BAKDIR" -mindepth 1 -maxdepth 1 -type d`; do
    SPLNAME=`basename "$SPLBAKDIR"`

    echo "Prepare measurements for $SPLNAME:..."
    SPLMSMOUT="$OUTDIR/$MSMOUTDIR/$SPLNAME"
    mkdir -p "$SPLMSMOUT"
    MSM_PDandFM="inttimetypechecker.csv"
    MSM_FT="inttimetypechecker_featurebased.csv"
    head -n -1 "$SPLBAKDIR/$MSM_PDandFM" > "$SPLMSMOUT/product-based.csv"
    head -n 1 "$SPLBAKDIR/$MSM_PDandFM" > "$SPLMSMOUT/family-based.csv"
    tail -n 1 "$SPLBAKDIR/$MSM_PDandFM" >> "$SPLMSMOUT/family-based.csv"
    cp -rf "$SPLBAKDIR/$MSM_FT" "$SPLMSMOUT/feature-based.csv"
    
    # Family-based without optimization.
    head -n 1 "$BAKDIR_NOOPT/$SPLNAME/$MSM_PDandFM" > "$SPLMSMOUT/family-based_without_optimization.csv"
    tail -n 1 "$BAKDIR_NOOPT/$SPLNAME/$MSM_PDandFM" >> "$SPLMSMOUT/family-based_without_optimization.csv"

done
echo "Prepare byte-code composition measurements..."
cp -rf "R_script_GPCE13/bytecode_typecheck_complete.csv" "$OUTDIR/$MSMOUTDIR/bytecode_composition.csv"


# Prepare configurations.
CNFOUTDIR="configurations"
for SPLBAKDIR in `find -L "$BAKDIR" -mindepth 1 -maxdepth 1 -type d`; do
    SPLNAME=`basename "$SPLBAKDIR"`
    
    echo "Prepare configurations for $SPLNAME:..."
    SPLCNFOUT="$OUTDIR/$CNFOUTDIR/$SPLNAME"

    # Product based.
    mkdir -p "$SPLCNFOUT/product-based"
    cp -rf "$SPLNAME/products/Variant"*/Variant*.model "$SPLCNFOUT/product-based"

    # Feature based.
    mkdir -p "$SPLCNFOUT/feature-based"
    cp -rf "$SPLNAME/FeatureConfigs/"*/*model "$SPLCNFOUT/feature-based"

    # Family based.
    cp -rf "$SPLNAME/model.m" "$SPLCNFOUT/family-based.model"

done

# Prepare subject systems.
SYSTEMOUTDIR="systems"
SPLSYSOUT="$OUTDIR/$SYSTEMOUTDIR/"
mkdir -p "$SPLSYSOUT"
for SPLBAKDIR in `find -L "$BAKDIR" -mindepth 1 -maxdepth 1 -type d`; do
    SPLNAME=`basename "$SPLBAKDIR"`

    echo "Prepare subject system $SPLNAME:..."
    cp -rf "subjectSystems/$SPLNAME" "$SPLSYSOUT"
done

touch "$OUTDIR/README"
cat >"$OUTDIR/README" <<EOF
measurements: measurement data in milliseconds
configurations: configurations (feature-models) used for each measurement
systems: subject systems
EOF

echo "Done."
