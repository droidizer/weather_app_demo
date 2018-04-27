package com.guru.weather.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Prediction {

    @SerializedName("description")
    public String description;

    @SerializedName("terms")
    public List<Terms> terms;

}

