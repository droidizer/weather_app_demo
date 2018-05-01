package com.guru.weather.network.manager;


import com.guru.weather.models.Forecast;
import com.guru.weather.models.WeatherForecastModel;

import io.reactivex.Observable;

public interface IWeatherApiManager {

    Observable<Forecast> getWeather(String city);

    Observable<WeatherForecastModel> getWeatherForecast(String cityName, int count);
}
