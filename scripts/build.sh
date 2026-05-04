#!/usr/bin/env bash
set -e

CP="libs/kotlin-stdlib.jar:libs/kotlin-reflect-1.7.10.jar:libs/kotlinx-cli-jvm-0.3.6.jar:libs/h2-2.2.224.jar"

kotlinc *.kt -cp "$CP" -d app.jar
echo "Сборка успешна: app.jar создан"