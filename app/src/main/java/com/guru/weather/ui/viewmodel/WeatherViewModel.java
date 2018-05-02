package com.guru.weather.ui.viewmodel;

import com.guru.weather.network.manager.IWeatherApiManager;
import com.guru.weather.utils.AndroidBaseViewModel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

public class WeatherViewModel extends AndroidBaseViewModel {

    private final IWeatherApiManager mWeatherApiManager;

    private Disposable mDisposable = Disposables.disposed();

    public WeatherViewModel(Application application, IWeatherApiManager weatherApiManager) {
        super(application);
        mWeatherApiManager = weatherApiManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final IWeatherApiManager mWeatherApiManager;

        @Inject
        public Factory(@NonNull Application application,
                IWeatherApiManager weatherApiManager) {
            mApplication = application;
            mWeatherApiManager = weatherApiManager;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new WeatherViewModel(mApplication, mWeatherApiManager);
        }
    }
}
