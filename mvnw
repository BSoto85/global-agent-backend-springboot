#!/bin/sh
# Maven Wrapper startup script
set -e

MAVEN_OPTS="${MAVEN_OPTS:--Xmx512m}"
export MAVEN_OPTS

WRAPPER_JAR=".mvn/wrapper/maven-wrapper.jar"
WRAPPER_PROPERTIES=".mvn/wrapper/maven-wrapper.properties"

MAVEN_VERSION=$(grep "distributionUrl" "$WRAPPER_PROPERTIES" | sed 's/.*apache-maven-\(.*\)-bin.zip/\1/')

if [ ! -f "$WRAPPER_JAR" ]; then
    echo "Downloading Maven Wrapper..."
    WRAPPER_URL=$(grep "wrapperUrl" "$WRAPPER_PROPERTIES" | sed 's/wrapperUrl=//')
    curl -fsSL "$WRAPPER_URL" -o "$WRAPPER_JAR"
fi

java -jar "$WRAPPER_JAR" "$@"
