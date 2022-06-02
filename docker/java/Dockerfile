FROM ubuntu:22.04

RUN apt-get update
RUN apt-get upgrade -y
RUN apt-get install openjdk-8-jdk -y
RUN apt-get install maven -y

# Clean
RUN apt-get purge -y --auto-remove -o APT::AutoRemove::RecommendsImportant=false \
      && rm -rf /var/lib/apt/lists/*

COPY ./docker/java/entrypoint.sh /entrypoint
RUN sed -i 's/\r$//g' /entrypoint
RUN chmod +x /entrypoint


WORKDIR ./tibco

ENTRYPOINT ["/entrypoint"]
