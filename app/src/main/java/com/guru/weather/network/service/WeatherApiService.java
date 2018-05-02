package com.guru.weather.network.service;

import com.guru.weather.models.Forecast;
import com.guru.weather.models.WeatherForecastModel;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {

    //Todo - Improvise for different cities
    @GET("weather?&q=Berlin")
    Observable<Forecast> getWeather(@Query("APPID") String key);

    @GET("forecast/daily?&q=Berlin")
    Observable<WeatherForecastModel> getWeatherForecast(@Query("cnt") int count, @Query("APPID") String key);
}