#!/bin/sh

rm -rf ~/{{name}}_*.db

lein migrate

lein cljsbuild once
