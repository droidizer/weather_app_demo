package com.guru.weather.network.manager;

import com.guru.weather.network.service.WeatherApiService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WeatherApiManager {

    private final WeatherApiService mWeatherApiService;

    @Inject
    WeatherApiManager(WeatherApiService weatherApiService){
        mWeatherApiService = weatherApiService;
    }
}
