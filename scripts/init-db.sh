#!/usr/bin/env bash
set -e

DB_PATH="./scripts/appdb"
H2_JAR="./libs/h2-2.2.224.jar"

./scripts/build.sh

chmod +x scripts/gen-accounts.sh
./scripts/gen-accounts.sh

java -cp "$H2_JAR" org.h2.tools.RunScript \
  -url "jdbc:h2:file:$DB_PATH;AUTO_SERVER=TRUE" \
  -user "sa" \
  -password "" \
  -script "./scripts/init.sql"

java -cp "$H2_JAR" org.h2.tools.RunScript \
  -url "jdbc:h2:file:$DB_PATH;AUTO_SERVER=TRUE" \
  -user "sa" \
  -password "" \
  -script "./scripts/fill-accounts.sql"

java -cp "$H2_JAR" org.h2.tools.RunScript \
  -url "jdbc:h2:file:$DB_PATH;AUTO_SERVER=TRUE" \
  -user "sa" \
  -password "" \
  -script "./scripts/fill.sql"