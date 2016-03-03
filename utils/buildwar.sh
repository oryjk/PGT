#!/bin/bash

#echo "start add war"
#cd /home/liqiang/Projects/PGT/admin/
#gradle build

for var in $*
do
    echo "$var start build war"
    if [ `expr "$var" : '.*/'` = 0 ] ;then
	cd /$1/$var/
	gradle build
    fi
done

#${var##*/} use / to del left and save right
#${var%%/*} use / to del right and save left

#for var in `seq $#`
#do
#    if [ $var = 2 ] ;then
#	echo ${@##* }
#    fi
#done