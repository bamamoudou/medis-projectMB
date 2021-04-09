#!/bin/sh
cd ./ms-authentication
./build.sh

cd ../ms-patientAdmin
./build.sh

cd ../ms-medicalrecord
./build.sh

cd ../ms-medicalreport
./build.sh

cd ../ms-clientui
./build.sh