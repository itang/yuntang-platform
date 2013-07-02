#! /bin/sh

thejar=ansj_seg-20130424.jar

wget --no-check-certificate https://github.com/ansjsun/ansj_seg/raw/master/dist/${thejar}

lein localrepo install ${thejar} ansj_seg/ansj_seg 1.0

rm ${thejar}
