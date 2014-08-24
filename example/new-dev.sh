#! /bin/sh

./clean-db.sh

lein with-profile 1.6 migrate

lein with-profile 1.6 ring server
