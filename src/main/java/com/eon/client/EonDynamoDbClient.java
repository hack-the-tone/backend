package com.eon.client;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.eon.dto.HourType;
import com.eon.dto.Project;
import com.eon.dto.TimeEntry;

public class EonDynamoDbClient {
    private DynamoDB dynamoDb;

    public EonDynamoDbClient(String clientType, AWSCredentials credentials) {
        this.dynamoDb = clientType.equals("sync") ? new DynamoDB(new AmazonDynamoDBClient(credentials)) : new DynamoDB(new AmazonDynamoDBAsyncClient(credentials));
    }

    private PutItemOutcome upsertTimeEntry(TimeEntry timeEntry)
            throws ConditionalCheckFailedException {
        return this.dynamoDb.getTable("Time-entries")
                .putItem(
                        new PutItemSpec().withItem(new Item()
                                .withString("id", timeEntry.getId())
                                .withPrimaryKey("userId", timeEntry.getUserId())
                                .withString("startTime", timeEntry.getStartTime().toString())
                                .withString("endTime", timeEntry.getEndTime().toString())
                                .withString("projectId", timeEntry.getProjectId())
                                .withString("hoursType", timeEntry.getHoursType())));
    }

    private PutItemOutcome upsertProject(Project project)
            throws ConditionalCheckFailedException {
        return this.dynamoDb.getTable("Projects")
                .putItem(
                        new PutItemSpec().withItem(new Item()
                                .withPrimaryKey("id", project.getId())
                                .withString("name", project.getName())
                                .withString("code", project.getCode())
                                .withString("description", project.getDescription())));
    }

    private PutItemOutcome upsertHourType(HourType hourType)
            throws ConditionalCheckFailedException {
        return this.dynamoDb.getTable("Hour-types")
                .putItem(
                        new PutItemSpec().withItem(new Item()
                                .withPrimaryKey("id", hourType.getId())
                                .withString("type", hourType.getType())));
    }


}
