#!/usr/bin/env bash

FILE="./app/build.gradle.ktx"

function getProperty {
    echo $(cat $GRADLE_FILE | grep "const val $1" | cut -d'=' -f2)
}

MAJOR=$(getProperty "major")
MINOR=$(getProperty "minor")
PATCH=$(getProperty "patch")

echo "VERSION=$MAJOR.$MINOR.$PAT" >> "$GITHUB_ENV"
