package com.guru.weather.models;

import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("icon")
    public String icon;

    @SerializedName("description")
    public String description;

    @SerializedName("main")
    public String title;

    @SerializedName("id")
    public int id;

    public String getIcon() {
        return icon;
    }

    public Weather setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Weather setDescription(String description) {
        this.description = description;
        return this;
    }

    public String gettitle() {
        return title;
    }

    public Weather settitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Weather weather = (Weather) o;

        if (id != weather.id) return false;
        if (icon != null ? !icon.equals(weather.icon) : weather.icon != null) return false;
        if (description != null ? !description.equals(weather.description) : weather.description != null) return false;
        return title != null ? title.equals(weather.title) : weather.title == null;

    }

    @Override
    public int hashCode() {
        int result = icon != null ? icon.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "icon='" + icon + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", id=" + id +
                '}';
    }
}
