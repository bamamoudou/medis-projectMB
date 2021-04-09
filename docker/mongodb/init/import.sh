#! /bin/bash

mongoimport --host mongodb --username root --password password --authenticationDatabase admin --db test --collection census --type json --file /src/data/init.json --jsonArray