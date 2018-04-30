package com.guru.weather.network.service;

import com.guru.weather.models.Forecast;
import com.guru.weather.models.WeatherModel;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherApiService {

    @GET("/weather?&q={city}&lang=en&APPID={api_key}")
    Observable<Forecast> weather(@Path("api_key") String key, @Path("city") String cityName);

    @GET("/forecast/daily?&q={city}&lang=en&cnt={count}&APPID={api_key")
    Observable<WeatherModel> weatherForecast(@Path("api_key") String key, @Path("city") String cityName, @Path("count") int count);
}