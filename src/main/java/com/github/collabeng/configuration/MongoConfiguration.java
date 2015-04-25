package com.github.collabeng.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by paul.smout on 25/04/2015.
 */
public class MongoConfiguration {
    @Valid
    @NotNull
    @JsonProperty
    private String serverAddress;

    @Valid
    @NotNull
    @JsonProperty
    private Integer port;

    public String getServerAddress() {
        return serverAddress;
    }

    public Integer getPort() {
        return port;
    }
}