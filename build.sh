#!/bin/bash
# Render build script
set -e

echo "Building the application..."
./mvnw clean package -DskipTests -B

echo "Build complete. Starting application..."
java -jar target/*.jar
