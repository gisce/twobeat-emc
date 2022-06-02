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


ENV TIBEMS_ROOT=/tibco
ENV TIBEMS_JAVA=${TIBEMS_ROOT}/src/main/lib
ENV TIBEMS_SRC=${TIBEMS_ROOT}/src/main/java/net.gisce/
ENV TIBEMS_TARGET=${TIBEMS_ROOT}/src/target
ENV CLASSPATH=${TIBEMS_JAVA}/jms-2.0.jar:${CLASSPATH}
ENV CLASSPATH=.:${TIBEMS_JAVA}/tibjms.jar:${TIBEMS_JAVA}/tibjmsufo.jar:${TIBEMS_JAVA}/tibcrypt.jar:${TIBEMS_JAVA}/tibjmsadmin.jar:${CLASSPATH}

WORKDIR ./tibco/src/target/net/gisce

ENTRYPOINT ["/entrypoint"]
