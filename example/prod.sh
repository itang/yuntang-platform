#! /bin/sh

export LEIN_NO_DEV=true

echo "lein migrate ..."
lein migrate

echo "lein trampoline http-kit ..."
lein trampoline http-kit

# LEIN_NO_DEV=YES java -XX:PermSize=256M -XX:MaxPermSize=512M -Xms2g -Xmx2g -XX:+UseParallelGC -jar example-0.1.0-SNAPSHOT-standalone.jar
