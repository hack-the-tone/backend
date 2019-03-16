#!/usr/bin/env bash

mvn clean package

aws lambda create-function \
--region eu-central-1 \
--function-name cognito-token-generator \
--zip-file fileb://target/hackthetone-backend-0.0.1-jar-with-dependencies.jar \
--role arn:aws:iam::767133151044:role/lambda_basic_execution \
--handler com.eon.handlers.CognitoTokenGenerator \
--runtime java8 \
--timeout 30 \
--memory-size 256
