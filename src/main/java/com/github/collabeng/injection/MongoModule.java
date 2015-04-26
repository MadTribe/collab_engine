package com.github.collabeng.injection;

import com.github.collabeng.configuration.MongoConfiguration;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import java.util.Arrays;

/**
 * Created by paul.smout on 25/04/2015.
 */
public class MongoModule extends AbstractModule {

    private MongoConfiguration mongoConfiguration;

    private MongoDatabase mongoDatabase;

    public MongoModule(MongoConfiguration mongoConfiguration) {
        this.mongoConfiguration = mongoConfiguration;
    }

    @Override
    protected void configure() {

        // TODO configure list of servers from YAML
        MongoClient mongoClient = new MongoClient(
                Arrays.asList(new ServerAddress(mongoConfiguration.getServerAddress(),mongoConfiguration.getPort())));

        mongoDatabase = mongoClient.getDatabase(mongoConfiguration.getDatabaseName());

    }

    @Provides
    public MongoDatabase getMongoDatabase(){
        return mongoDatabase;
    }
}
