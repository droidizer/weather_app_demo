package com.guru.weather.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Forecast {

    @SerializedName("dt")
    public long dt;

    @SerializedName("weather")
    public List<Weather> weather;

    @SerializedName("main")
    public Main main;

    @SerializedName("temp")
    public Temperature temperature;

    @SerializedName("pressure")
    public double pressure;

    @SerializedName("humidity")
    public int humidity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Forecast forecast = (Forecast) o;

        if (dt != forecast.dt) return false;
        if (weather != null ? !weather.equals(forecast.weather) : forecast.weather != null) return false;
        return main != null ? main.equals(forecast.main) : forecast.main == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (dt ^ (dt >>> 32));
        result = 31 * result + (weather != null ? weather.hashCode() : 0);
        result = 31 * result + (main != null ? main.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "dt=" + dt +
                ", weather=" + weather +
                ", main=" + main +
                '}';
    }
}
