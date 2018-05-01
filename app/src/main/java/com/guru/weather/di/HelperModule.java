package com.guru.weather.di;

import android.content.res.Resources;

import com.guru.weather.R;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
class HelperModule {

    private static final String WEATHER_API_KEY = "WeatherApiKey";

    private static final String PLACES_API_KEY = "PlacesApiKey";

    @Provides
    @Named(WEATHER_API_KEY)
    public String provideWeatherApiKey(Resources resources) {
        return resources.getString(R.string.weather_key);
    }

    @Provides
    @Named(PLACES_API_KEY)
    public String providePlacesApiKey(Resources resources) {
        return resources.getString(R.string.places_key);
    }
}
