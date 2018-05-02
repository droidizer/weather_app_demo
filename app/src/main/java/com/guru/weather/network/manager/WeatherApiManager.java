package com.guru.weather.network.manager;

import com.guru.weather.R;
import com.guru.weather.models.Forecast;
import com.guru.weather.models.WeatherForecastModel;
import com.guru.weather.network.service.WeatherApiService;

import android.content.res.Resources;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

@Singleton
public class WeatherApiManager implements IWeatherApiManager {

    private final WeatherApiService mWeatherApiService;

    private Disposable mWeatherDisposable = Disposables.disposed();

    @Inject
    Resources mResources;

    @Inject
    public WeatherApiManager(WeatherApiService weatherApiService) {
        mWeatherApiService = weatherApiService;
    }

    @Override
    public Observable<Forecast> getWeather(String city) {
        return mWeatherApiService.getWeather(mResources.getString(R.string.weather_key), city);
    }

    @Override
    public Observable<WeatherForecastModel> getWeatherForecast(String cityName, int count) {
        return mWeatherApiService.getWeatherForecast(mResources.getString(R.string.weather_key), cityName, count);
    }
}
