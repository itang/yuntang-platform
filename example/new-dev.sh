#! /bin/sh

./clean-db.sh

lein migrate

lein ring server
