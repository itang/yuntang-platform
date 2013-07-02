#!/bin/sh

if [ "$1" = "" ]; then
  go run install.go
else 
  go run install.go $1 $2
fi

#ph=`pwd`
#echo $ph

#cd $ph/modules/captcha/core
#lein install

#cd $ph/modules/common/core
#lein install

#cd $ph/admin/core
#lein install

#cd $ph/user/core
#lein install

#cd $ph/modules/captcha/ui
#lein sub install

#cd $ph/modules/common/ui
#lein sub install

#cd $ph/admin/ui
#lein sub install

#cd $ph/user/ui
#lein sub install

#cd $ph/layout/ui
#lein sub install

#cd $ph/app
#lein install

