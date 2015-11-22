#!/usr/bin/env sh

DIR=`dirname $0`
cd $DIR/..

script/install.sh

tar czf target/package.tar.gz server/
ansible-playbook ansible/deploy.yml
