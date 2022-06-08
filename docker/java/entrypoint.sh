#!/bin/bash

set -o errexit
set -o pipefail

mvn clean install
export CLASSPATH=/tibco/target/libs/jms-2.0.jar:${CLASSPATH}
export CLASSPATH=/tibco/target/libs/tibjms-8.2.2.jar:${CLASSPATH}
export CLASSPATH=/tibco/target/libs/log4j-api-2.17.2.jar:${CLASSPATH}
export CLASSPATH=/tibco/target/libs/log4j-core-2.17.2.jar:${CLASSPATH}
export CLASSPATH=/tibco/target/libs/dotenv-java-2.2.4.jar:${CLASSPATH}
cd /tibco/target/classes
java net.gisce.tibjmsMsgConsumer -user "${TIBCO_USER}" -password "${TIBCO_PASSWORD}" -queue  "AMM.LPOData" -server "tcp://localhost:7224"
"$@"
