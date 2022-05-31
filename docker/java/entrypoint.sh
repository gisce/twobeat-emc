#!/bin/bash

set -o errexit
set -o pipefail
set -o nounset
javac -d ${TIBEMS_BIN} ${TIBEMS_SRC}/*.java

