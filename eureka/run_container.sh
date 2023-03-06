#!/bin/sh
docker rm eureka -f

NETWORK_NAME=parceldelivery
if [ -z $(docker network ls --filter name=^${NETWORK_NAME}$ --format="{{ .Name }}") ] ; then
     docker network create ${NETWORK_NAME} ;
fi

docker container run -d --name eureka --network=parceldelivery -p 8761:8761 eureka:latest