package com.guru.weather.models;

import com.google.gson.annotations.SerializedName;

public class Temperature {

    @SerializedName("day")
    public double temp;

    @SerializedName("min")
    public double tempMin;

    @SerializedName("max")
    public double tempMax;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Temperature that = (Temperature) o;

        if (Double.compare(that.temp, temp) != 0) return false;
        if (Double.compare(that.tempMin, tempMin) != 0) return false;
        return Double.compare(that.tempMax, tempMax) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp1;
        temp1 = Double.doubleToLongBits(temp);
        result = (int) (temp1 ^ (temp1 >>> 32));
        temp1 = Double.doubleToLongBits(tempMin);
        result = 31 * result + (int) (temp1 ^ (temp1 >>> 32));
        temp1 = Double.doubleToLongBits(tempMax);
        result = 31 * result + (int) (temp1 ^ (temp1 >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "temp=" + temp +
                ", tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                '}';
    }
}
