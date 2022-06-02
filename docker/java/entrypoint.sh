#!/bin/bash

set -o errexit
set -o pipefail
set -o nounset
mvn clean
mvn install

java -jar target/tibco-1.0-SNAPSHOT.jar
