#!/bin/sh

mvn clean install -DskipTests

docker build . -t gateway-api:latest

docker-compose -f application-startup.yml up -d
