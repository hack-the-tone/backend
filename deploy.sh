#!/usr/bin/env bash

mvn clean package

aws lambda update-function-code \
--function-name aws-lambda-java-template \
--zip-file fileb://target/hackthetone-backend-0.0.1-jar-with-dependencies.jar \
--publish
