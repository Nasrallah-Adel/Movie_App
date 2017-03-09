package com.example.nasrallah.movies.models;

/**
 * Created by root on 11/25/16.
 */

public class trailer_model {
    String key;
    String id;
    String name;

    public String getKey() {
        return key;
    }

    public String getId() {
        return id;
    }

    public String getName(String name) {
        return this.name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
