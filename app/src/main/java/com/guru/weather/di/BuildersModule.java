package com.guru.weather.di;

import com.guru.weather.ui.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract MainActivity mainActivity();
}
