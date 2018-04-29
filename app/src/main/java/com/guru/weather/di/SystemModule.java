package com.guru.weather.di;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SystemModule {

    private final Application application;

    public SystemModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return application;
    }

    @Singleton
    @Provides
    public Application providesApplication() {
        return application;
    }

    @Singleton
    @Provides
    public Resources provideResources(Context context) {
        return context.getResources();
    }
}
