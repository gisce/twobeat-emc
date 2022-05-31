FROM ubuntu:22.04

RUN apt-get update
RUN apt-get upgrade -y
RUN apt-get install openjdk-8-jdk -y

# Clean
RUN apt-get purge -y --auto-remove -o APT::AutoRemove::RecommendsImportant=false \
      && rm -rf /var/lib/apt/lists/*

COPY ./docker/java/entrypoint.sh /entrypoint
RUN sed -i 's/\r$//g' /entrypoint
RUN chmod +x /entrypoint


ENV TIBEMS_ROOT=/tibco/java
ENV TIBEMS_JAVA=${TIBEMS_ROOT}/lib
ENV TIBEMS_SRC=${TIBEMS_ROOT}/src
ENV TIBEMS_BIN=${TIBEMS_ROOT}/bin
ENV CLASSPATH=${TIBEMS_JAVA}/jms-2.0.jar:${CLASSPATH}
ENV CLASSPATH=.:${TIBEMS_JAVA}/tibjms.jar:${TIBEMS_JAVA}/tibjmsufo.jar:${TIBEMS_JAVA}/tibcrypt.jar:${TIBEMS_JAVA}/tibjmsadmin.jar:${TIBEMS_JAVA}/slf4j-api-1.4.2.jar:${TIBEMS_JAVA}/slf4j-simple-1.4.2.jar:${CLASSPATH}

WORKDIR ./tibco/java/bin

ENTRYPOINT ["/entrypoint"]
