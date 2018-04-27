package com.guru.weather.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Places {

        @SerializedName("predictions")
        public List<Prediction> predictions;

        @SerializedName("status")
        public String status;

    @Override
    public String toString() {
        return "Places{" +
                "predictions=" + predictions +
                ", status='" + status + '\'' +
                '}';
    }
}


