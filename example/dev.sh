#!/bin/sh
unset LEIN_NO_DEV

echo "drip kill ..."
drip kill

echo "lein dev ..."
lein with-profile 1.6 dev
