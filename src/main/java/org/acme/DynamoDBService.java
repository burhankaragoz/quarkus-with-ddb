/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/

package org.acme;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

/*
 Before running this code example, create an Amazon DynamoDB table named Work with a primary key named id.
 */
@ApplicationScoped
public class DynamoDBService {

    private DynamoDbClient getClient() {
        Region region = Region.US_EAST_1;
        final DynamoDbClient build =
            DynamoDbClient.builder().endpointOverride(URI.create("http://localhost:8000")).region(region).build();
        return build;
    }

    // Get All items from the DynamoDB table.
    public List<Pet> getAllItems() {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(getClient()).build();

        try {
            DynamoDbTable<Pet> table = enhancedClient.table("Pets", TableSchema.fromBean(Pet.class));
            Iterator<Pet> results = table.scan().items().iterator();
            ArrayList<Pet> itemList = new ArrayList<>();
            while (results.hasNext()) {
                Pet pet = results.next();
                itemList.add(pet);
            }
            return itemList;

        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }

    public void setItem(Pet pet) {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(getClient()).build();
        putRecord(enhancedClient, pet);
    }

    // Put an item into a DynamoDB table.
    public void putRecord(DynamoDbEnhancedClient enhancedClient, Pet pet) {
        try {
            DynamoDbTable<Pet> workTable = enhancedClient.table("Pets", TableSchema.fromBean(Pet.class));
            workTable.putItem(pet);
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}