#!/usr/bin/env sh

DIR=`dirname $0`

mkdir -p $DIR/../server/plugins/
cp ../target/scala-2.11/SysAdmincraft.jar $DIR/../server/plugins/
