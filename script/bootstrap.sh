#!/usr/bin/env sh

DIR=`dirname $0`

mkdir -p $DIR/../server
curl http://tcpr.ca/files/spigot/spigot-1.8.8-R0.1-SNAPSHOT-latest.jar > $DIR/../server/spigot.jar
