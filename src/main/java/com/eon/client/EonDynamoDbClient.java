package com.eon.client;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.eon.dto.HourType;
import com.eon.dto.Project;
import com.eon.dto.TimeEntry;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EonDynamoDbClient {
    private static final Logger LOG = Logger.getLogger(EonDynamoDbClient.class);
    private DynamoDB dynamoDb;

    public EonDynamoDbClient(String clientType, AWSCredentials credentials) {
        this.dynamoDb = clientType.equals("sync") ? new DynamoDB(new AmazonDynamoDBClient(credentials).withRegion(Regions.EU_CENTRAL_1)) : new DynamoDB(new AmazonDynamoDBAsyncClient(credentials).withRegion(Regions.EU_CENTRAL_1));
    }

    private PutItemOutcome upsertTimeEntry(TimeEntry timeEntry)
            throws ConditionalCheckFailedException {
        return this.dynamoDb.getTable("Time-entries")
                .putItem(
                        new PutItemSpec().withItem(new Item()
                                .withString("id", timeEntry.getId())
                                .withString("userId", timeEntry.getUserId())
                                .withString("startTime", timeEntry.getStartTime().toString())
                                .withString("endTime", timeEntry.getEndTime().toString())
                                .withString("projectId", timeEntry.getProjectId())
                                .withString("hoursType", timeEntry.getHoursTypeId())));
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

    private List<String> getAllTimeEntries() {
        List<String> jsonItems = new ArrayList<>();
        Table table = this.dynamoDb.getTable("Time-entries");
        ScanSpec scanSpec = new ScanSpec();
        try {
            ItemCollection<ScanOutcome> items = table.scan(scanSpec);
            Iterator<Item> iter = items.iterator();
            while(iter.hasNext()) {
                Item item = iter.next();
                jsonItems.add(item.toJSONPretty());
            }
        }
        catch (Exception e) {
            LOG.error(e.getMessage());
        }

        return jsonItems;
    }

    private List<String> getTimeEntriesForUser(String userId) {
        List<String> jsonItems = new ArrayList<>();
        Table table = this.dynamoDb.getTable("Time-entries");
        ScanSpec scanSpec = new ScanSpec().withFilterExpression("userId = :userID").withValueMap(new ValueMap().withString(":userID", userId));
        try {
            ItemCollection<ScanOutcome> items = table.scan(scanSpec);
            Iterator<Item> iter = items.iterator();
            while(iter.hasNext()) {
                Item item = iter.next();
                jsonItems.add(item.toJSONPretty());
            }
        }
        catch (Exception e) {
            LOG.error(e.getMessage());
        }

        return jsonItems;
    }



//    public static void main(String[] args) {
//            AWSCredentials awsCredentials = new AWSCredentials() {
//                @Override
//                public String getAWSAccessKeyId() {
//                    return "AKIAI3M5LN7N7YT7KUHQ";
//                }
//
//                @Override
//                public String getAWSSecretKey() {
//                    return "gw4JPsKc9y3voi66AyxLrPzxdV07n1ips9cALkmR";
//                }
//            };
//
//        EonDynamoDbClient eonDynamoDbClient = new EonDynamoDbClient("sync",awsCredentials);
//
//        List<String> entries = eonDynamoDbClient.getTimeEntriesForUser("1");
//        for (String e : entries) {
//            System.out.println(e);
//        }
//
//    }

}
