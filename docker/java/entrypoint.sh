#!/bin/bash
set -o errexit
set -o pipefail
if [ -z ${AMM_QUEUE} ];then
  echo "AMM_QUEUE is unset";
  exit;
  else
    echo "AMM_QUEUE is set to '${AMM_QUEUE}'";
fi

if [ "${APP_ENVIORMENT}" = "prod" ]; then
  mvn compiler
else
  mvn compiler
fi

cd /tibco/target/classes
CLASSPATH=/tibco/target/libs/jms-2.0.jar:${CLASSPATH} \
CLASSPATH=/tibco/target/libs/tibjms-8.2.2.jar:${CLASSPATH} \
CLASSPATH=/tibco/target/libs/log4j-api-2.17.2.jar:${CLASSPATH} \
CLASSPATH=/tibco/target/libs/log4j-core-2.17.2.jar:${CLASSPATH} \
CLASSPATH=/tibco/target/libs/dotenv-java-2.2.4.jar:${CLASSPATH} \
java net.gisce.tibjmsMsgConsumer -user "${TIBCO_USER}" -password "${TIBCO_PASSWORD}" -queue  "${AMM_QUEUE}" -server "${TIBCO_SERVER_URL}"
"$@"