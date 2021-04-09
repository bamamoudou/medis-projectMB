#!/bin/sh
cd ./EdgeMS/ems-eureka
./build.sh

cd ../../EdgeMS/ems-zuul
./build.sh

cd ../../EdgeMS/ems-admin
./build.sh