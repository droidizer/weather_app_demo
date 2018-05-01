package com.guru.weather.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherForecastModel {

    @SerializedName("list")
    private List<Forecast> list;

    public List<Forecast> getForecast() {
        return list;
    }

    public WeatherForecastModel setForecast(List<Forecast> list) {
        this.list = list;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeatherForecastModel that = (WeatherForecastModel) o;

        return list != null ? list.equals(that.list) : that.list == null;

    }

    @Override
    public int hashCode() {
        return list != null ? list.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "WeatherForecastModel{" +
                "list=" + list +
                '}';
    }
}