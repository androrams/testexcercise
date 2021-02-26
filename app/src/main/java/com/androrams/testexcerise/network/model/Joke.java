package com.androrams.testexcerise.network.model;

import com.google.gson.annotations.SerializedName;

public class Joke {

    @SerializedName("id")
    private int id;
    @SerializedName("joke")
    private String joke;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }
}
