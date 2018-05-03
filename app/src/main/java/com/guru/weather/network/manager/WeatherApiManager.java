package com.guru.weather.network.manager;

import com.guru.weather.R;
import com.guru.weather.models.Forecast;
import com.guru.weather.models.WeatherForecastModel;
import com.guru.weather.network.service.WeatherApiService;

import android.content.res.Resources;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class WeatherApiManager implements IWeatherApiManager {

    private final WeatherApiService mWeatherApiService;

    @Inject
    Resources mResources;

    @Inject
    public WeatherApiManager(WeatherApiService weatherApiService) {
        mWeatherApiService = weatherApiService;
    }

    @Override
    public Observable<Forecast> getWeather() {
        return mWeatherApiService.getWeather(mResources.getString(R.string.weather_key));
    }

    @Override
    public Observable<WeatherForecastModel> getWeatherForecast(int count) {
        return mWeatherApiService.getWeatherForecast(count, mResources.getString(R.string.weather_key));
    }
}
