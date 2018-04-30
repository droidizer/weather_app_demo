package com.guru.weather.di;

import android.content.Context;
import android.content.res.Resources;

import com.guru.weather.WeatherApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class SystemModule {

    @Singleton
    @Provides
    public Context providesContext(WeatherApplication application) {
        return application;
    }

    @Singleton
    @Provides
    public Resources providesResources(Context context) {
        return context.getResources();
    }

}
