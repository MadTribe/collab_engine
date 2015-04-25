package com.github.collabeng.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.Mongo;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by paul.smout on 25/01/2015.
 */
public class CollaborationEngineConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty
    private String systemLocale;

    @Valid
    @NotNull
    @JsonProperty
    private DataSourceFactory database;

    @Valid
    @NotNull
    @JsonProperty
    private MongoConfiguration mongoConfiguration;

    public CollaborationEngineConfiguration() {
    }

    public DataSourceFactory getDatabase() {
        return database;
    }

    public MongoConfiguration getMongoConfiguration() {
        return mongoConfiguration;
    }
}
