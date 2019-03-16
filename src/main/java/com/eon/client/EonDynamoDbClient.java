package com.eon.client;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public class EonDynamoDbClient {
    private AmazonDynamoDBClient dynamoDb;

    public EonDynamoDbClient(String clientType, AWSCredentials credentials){

        this.dynamoDb = clientType.equals("sync") ?  new AmazonDynamoDBClient(credentials) :  new AmazonDynamoDBAsyncClient(credentials);

    }
}
