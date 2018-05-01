package com.guru.weather.network.manager;

import android.content.res.Resources;

import com.guru.weather.R;
import com.guru.weather.models.Forecast;
import com.guru.weather.models.Weather;
import com.guru.weather.models.WeatherForecastModel;
import com.guru.weather.network.service.WeatherApiService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

@Singleton
public class WeatherApiManager {

    private final WeatherApiService mWeatherApiService;

    private Disposable mWeatherDisposable = Disposables.disposed();

    @Inject
    Resources mResources;

    @Inject
    public WeatherApiManager(WeatherApiService weatherApiService) {
        mWeatherApiService = weatherApiService;
    }

    public Observable<Forecast> getWeather(String city) {
        return mWeatherApiService.getWeather(mResources.getString(R.string.weather_key), city);
    }

    public Observable<WeatherForecastModel> getWeather(String city, int count) {
        return mWeatherApiService.getWeatherForecast(mResources.getString(R.string.weather_key), city, count);
    }
}
