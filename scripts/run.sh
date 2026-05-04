#!/usr/bin/env bash
set -e

CP="app.jar:libs/kotlin-stdlib.jar:libs/kotlin-reflect-1.7.10.jar:libs/kotlinx-cli-jvm-0.3.6.jar:libs/h2-2.2.224.jar"

java -cp "$CP" AppKt "$@"