#!/usr/bin/env bash
set -e

CP="app.jar:libs/kotlin-stdlib.jar:libs/kotlin-reflect-1.7.10.jar:libs/kotlinx-cli-jvm-0.3.6.jar"

HASH=$(java -cp "$CP" AppKt --calc-hash)

cat > scripts/fill-accounts.sql <<EOF
INSERT INTO accounts (login, salt, password_hash)
VALUES
 ('player', 'gameSalt', '$HASH');
EOF

echo "Сгенерирован scripts/fill-accounts.sql с hash=$HASH"