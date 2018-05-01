package com.guru.weather.ui.viewmodel;

import android.app.Application;

import com.guru.weather.network.manager.IWeatherApiManager;
import com.guru.weather.utils.AndroidBaseViewModel;

import javax.inject.Inject;

public class WeatherTodayViewModel extends AndroidBaseViewModel {

    private final IWeatherApiManager mWeatherApiManager;

    @Inject
    public WeatherTodayViewModel(Application application, IWeatherApiManager weatherApiManager) {
        super(application);
        mWeatherApiManager = weatherApiManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
