#!/bin/bash
# Build script for SeedFinding Java runner
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
BUILD_DIR="$SCRIPT_DIR/build"
SRC_DIR="$SCRIPT_DIR/src"
LIB_DIR="$BUILD_DIR/lib"

mkdir -p "$BUILD_DIR" "$LIB_DIR"

# Classpath: all JARs in libs
CP="./libs/*"

echo "Compiling SeedFinderRunner.java..."
javac -cp "$CP" -d "$BUILD_DIR" "$SRC_DIR/SeedFinderRunner.java"

# Create classpath file for runtime
# Use relative paths so it works in both local and Docker
REL_CP="$SCRIPT_DIR/libs/*:$BUILD_DIR"
echo "$REL_CP" > "$BUILD_DIR/classpath.txt"

echo "Build complete."
