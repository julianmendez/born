#!/bin/bash

cd `dirname $0`
date +%FT%T
echo `basename $0`


timestamp=`date +%s`


ontology="src/test/resources/born-example.owl"
network="src/test/resources/network.pl"
queries="src/test/resources/born-example.pl"
output="output.pl"


cd ../../../
mvn exec:java -Dexec.args="get --log ${ontology} ${network} ${queries} ${output}"


date +%FT%T


