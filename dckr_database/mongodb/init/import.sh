#! /bin/bash

mongoimport --host mongo_db --username root --password password --authenticationDatabase admin --db mediscreen_oc_mc --collection patientsMedicalRecords --type json --file /src/data/init_db_prod.json --jsonArray