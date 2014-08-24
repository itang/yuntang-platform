#!/bin/sh

rm -rf ~/wapp_*.db

lein with-profile 1.6 migrate
