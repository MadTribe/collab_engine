package com.github.collabeng.dao.util;

/**
 * Created by paul.smout on 26/01/2015.
 */
public class QueryParam<X> {
    private String name;
    private X value;

    public QueryParam(String name, X value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public X getValue() {
        return value;
    }
}
