#!/bin/sh

mvn -N io.takari:maven:wrapper
./mvnw clean package
