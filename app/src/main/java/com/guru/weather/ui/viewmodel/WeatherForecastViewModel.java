package com.guru.weather.ui.viewmodel;

import com.guru.weather.misc.AppUtils;
import com.guru.weather.models.Forecast;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class WeatherForecastViewModel {

    private final static String format = "MMM dd";

    private final Forecast mForecast;

    WeatherForecastViewModel(Forecast forecast) {
        mForecast = forecast;
    }

    public String getDayTemp() {
        if (mForecast.temperature != null) {
            return String.valueOf(AppUtils.kelvinToCelcius(mForecast.temperature.temp));
        }
        return "";
    }

    public String getDate() {
        return String.valueOf(AppUtils.formatDate(new SimpleDateFormat(format, Locale.GERMANY), mForecast.dt));
    }

    public String getReport() {
        if (mForecast.weather != null && !mForecast.weather.isEmpty()) {
            return mForecast.weather.get(0).title;
        }
        return "";
    }
}
