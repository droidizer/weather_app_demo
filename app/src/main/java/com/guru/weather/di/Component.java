package com.guru.weather.di;

import com.guru.weather.WeatherApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@dagger.Component(modules = {
        AndroidSupportInjectionModule.class,
        BuildersModule.class,
        SystemModule.class,
        HelperModule.class,
        ManagersModule.class,
        NetworkModule.class})
public interface Component {

    void inject(WeatherApplication weatherApplication);

    @dagger.Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(WeatherApplication application);

        Component build();
    }
}
