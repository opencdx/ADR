#!/bin/bash

./gradlew clean bootJar

docker build -t adr:latest .
cd docker
docker-compose --project-name opencdx up -d

