#!/bin/sh

rm -rf ~/wapp_*.db

lein migrate
