package com.github.collabeng;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    public DataSourceFactory getDatabase() {
        return database;
    }



}
