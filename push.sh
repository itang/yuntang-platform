#! /bin/sh

git add .
if [ "$1" = "" ]; then
  git commit -am "update"
else
  git commit -am "$1"
fi
https_proxy=localhost:8087 git push origin master
