#!/usr/bin/env sh

DIR=`dirname $0`
cd $DIR/..

ansible-playbook ansible/copy.yml
