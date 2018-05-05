package com.guru.weather.ui.viewmodel;

import com.guru.weather.BR;
import com.guru.weather.R;
import com.guru.weather.models.Forecast;
import com.guru.weather.network.manager.IWeatherApiManager;
import com.guru.weather.utils.AndroidBaseViewModel;
import com.guru.weather.utils.rv.AndroidItemBinder;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.res.Resources;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import retrofit2.HttpException;

import static com.guru.weather.misc.AppUtils.kelvinToCelcius;

public class WeatherViewModel extends AndroidBaseViewModel {

    private final IWeatherApiManager mWeatherApiManager;

    private Disposable mWeatherDisposable = Disposables.disposed();

    private String mHumidity;

    private String mMinTemp;

    private String mMaxTemp;

    private String mPressure;

    private String mWeatherReport;

    private String mDayTemp;

    private boolean mLoading;

    private String mErrorMessage;

    private boolean mErrorVisible;

    private Resources mResources;

    private Map<Class<?>, AndroidItemBinder> mTemplates;

    private List<WeatherForecastViewModel> mForecastList = new ArrayList<>();

    public WeatherViewModel(Application application, Resources resources, IWeatherApiManager weatherApiManager) {
        super(application);
        mWeatherApiManager = weatherApiManager;
        mResources = resources;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setLoading(true);
        if (mWeatherDisposable.isDisposed()) {
            mWeatherDisposable = Observable.zip(mWeatherApiManager.getWeather(), mWeatherApiManager.getWeatherForecast(10),
                    Pair::new)
                    .subscribe(forecast -> {
                        setLoading(false);
                        if (!forecast.second.getForecast().isEmpty()) {
                            for (Forecast f : forecast.second.getForecast())
                                mForecastList.add(new WeatherForecastViewModel(f));
                        }
                        notifyBindings();
                    }, throwable -> {
                        setLoading(false);
                        notifyError(throwable);
                    });
        }
    }

    @Bindable
    public Map<Class<?>, AndroidItemBinder> getTemplatesForObjects() {
        if (mTemplates == null) {
            mTemplates = new HashMap<>();
            mTemplates.put(WeatherForecastViewModel.class, new AndroidItemBinder(R.layout.weather_forecast_item, BR.weatherViewModel));
        }
        return mTemplates;
    }

    @Bindable
    public List<?> getForecastItems() {
        return mForecastList;
    }

    private void notifyError(Throwable throwable) {
        setLoading(false);
        String errorMessage = (throwable instanceof HttpException || throwable instanceof IOException)
                ? mResources.getString(R.string.connection_error)
                : mResources.getString(R.string.error);
        setErrorMessage(errorMessage);
        setErrorVisible(true);
    }

    public void setErrorMessage(String message) {
        mErrorMessage = message;
    }

    private void setErrorVisible(boolean errorVisible) {
        mErrorVisible = !isLoading() && errorVisible;
    }

    public void notifyBindings() {
        notifyPropertyChanged(BR.forecastItems);
    }
    private void setLoading(boolean loading) {
        mLoading = loading;
        notifyPropertyChanged(BR.loading);
    }

    @Override
    public void onDestroy() {
        mWeatherDisposable.dispose();
        super.onDestroy();
    }

    @Bindable
    public boolean isLoading() {
        return mLoading;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final IWeatherApiManager mWeatherApiManager;

        private Resources mResources;

        @Inject
        public Factory(@NonNull Application application, Resources resources,
                       IWeatherApiManager weatherApiManager) {
            mApplication = application;
            mWeatherApiManager = weatherApiManager;
            mResources = resources;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new WeatherViewModel(mApplication, mResources, mWeatherApiManager);
        }
    }
}
