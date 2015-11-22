#!/usr/bin/env sh

DIR=`dirname $0`
cd $DIR/../

mkdir -p server/plugins/
cp target/scala-2.11/SysAdmincraft.jar server/plugins/
