#!/bin/sh
cd ./EdgeMS/ems-eureka || exit
mvn -N io.takari:maven:wrapper
./mvnw clean

cd ../../EdgeMS/ems-zuul || exit
mvn -N io.takari:maven:wrapper
./mvnw clean

cd ../../EdgeMS/ems-admin || exit
mvn -N io.takari:maven:wrapper
./mvnw clean