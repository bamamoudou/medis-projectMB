#!/bin/sh
cd ./ms-authentication || exit
mvn -N io.takari:maven:wrapper
./mvnw clean

cd ../ms-patientAdmin || exit
mvn -N io.takari:maven:wrapper
./mvnw clean

cd ../ms-medicalrecord || exit
mvn -N io.takari:maven:wrapper
./mvnw clean

cd ../ms-medicalreport || exit
mvn -N io.takari:maven:wrapper
./mvnw clean