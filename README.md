# twobeat-emc
Tibco ems connection and download xml file 
## system requirements
    - docker-compose > 1.29.2
    - docker  > 20.10.17
## Setup local development environment (with docker-composer)

1. cp .env.sample .env
   1. Edit and config env file
   2. doc ftp https://github.com/panubo/docker-vsftpd
3. Build image  
    4. `docker-compose  build`
5. run containers
   6. `docker-compose  up -d`
