package com.guru.weather.network.manager;

import com.guru.weather.di.HelperModule;
import com.guru.weather.models.Forecast;
import com.guru.weather.models.WeatherForecastModel;
import com.guru.weather.network.service.WeatherApiService;

import android.content.res.Resources;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class WeatherApiManager implements IWeatherApiManager {

    private final WeatherApiService mWeatherApiService;

    private final String mWeatherKey;

    @Inject
    Resources mResources;

    @Inject
    public WeatherApiManager(WeatherApiService weatherApiService, @Named(HelperModule.WEATHER_API_KEY) String WEATHER_KEY) {
        mWeatherApiService = weatherApiService;
        mWeatherKey = WEATHER_KEY;
    }

    @Override
    public Observable<Forecast> getWeather() {
        return mWeatherApiService.getWeather(mWeatherKey);
    }

    @Override
    public Observable<WeatherForecastModel> getWeatherForecast(final int count) {
        return mWeatherApiService.getWeatherForecast(count, mWeatherKey);
    }
}
