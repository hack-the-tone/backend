#!/usr/bin/env bash

aws lambda create-function \
--region eu-central-1 \
--function-name aws-lambda-java-template \
--zip-file fileb://target/hackthetone-backend-0.0.1.jar \
--role ROLE_ARN \
--handler com.eon.handlers.Application \
--runtime java8 \
--timeout 30 \
--memory-size 256
