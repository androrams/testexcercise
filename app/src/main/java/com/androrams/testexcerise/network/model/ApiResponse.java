package com.androrams.testexcerise.network.model;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {

    @SerializedName("type")
    private String type;
    @SerializedName("value")
    private Joke value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Joke getValue() {
        return value;
    }

    public void setValue(Joke value) {
        this.value = value;
    }
}
