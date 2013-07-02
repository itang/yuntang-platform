#! /bin/sh

echo "drip kill ..."
drip kill

export LEIN_NO_DEV=true 

echo "lein migrate ..."
lein migrate

echo "lein trampoline http-kit ..."
lein trampoline http-kit

