package com.guru.weather;

import android.app.Activity;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.guru.weather.di.Component;
import com.guru.weather.di.DaggerComponent;
import com.guru.weather.di.SystemModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class WeatherApplication extends MultiDexApplication implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> mAndroidActivityInjector;

    private Component mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        mComponent = DaggerComponent.builder().application(this).systemModule(new SystemModule(this))
                .build();
        mComponent.inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mAndroidActivityInjector;
    }
}
