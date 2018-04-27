package com.guru.weather.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by greymatter on 30/04/16.
 */
public class Terms {

    @SerializedName("offset")
    public String offset;

     @SerializedName("value")
    public String value;

    public String getValue() {
        return value;
    }

    public Terms setValue(String value) {
        this.value = value;
        return this;
    }

    public String getOffset() {
        return offset;
    }

    public Terms setOffset(String offset) {
        this.offset = offset;
        return this;
    }
}
