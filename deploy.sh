#!/usr/bin/env bash

aws lambda update-function-code \
--function-name aws-lambda-java-template \
--zip-file fileb://target/hackthetone-backend-0.0.1.jar \
--publish
