#!/usr/bin/env sh

DIR=`dirname $0`

cd $DIR/..
sbt test assembly
