package com.guru.weather.di;

import com.guru.weather.network.manager.IPlacesApiManager;
import com.guru.weather.network.manager.IWeatherApiManager;
import com.guru.weather.network.manager.PlacesApiManager;
import com.guru.weather.network.manager.WeatherApiManager;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class ManagersModule {

    @Binds
    public abstract IWeatherApiManager provideWeatherApiManager(WeatherApiManager weatherApiManager);

    @Binds
    public abstract IPlacesApiManager providePlacesApiManager(PlacesApiManager placesApiManager);
}
