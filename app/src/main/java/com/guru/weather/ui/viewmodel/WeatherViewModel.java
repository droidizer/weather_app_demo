package com.guru.weather.ui.viewmodel;

import com.guru.weather.BR;
import com.guru.weather.models.Forecast;
import com.guru.weather.network.manager.IWeatherApiManager;
import com.guru.weather.utils.AndroidBaseViewModel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.Bindable;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

import static com.guru.weather.misc.AppUtils.kelvinToCelcius;

public class WeatherViewModel extends AndroidBaseViewModel {

    private final IWeatherApiManager mWeatherApiManager;

    private Disposable mDisposable = Disposables.disposed();

    private String mHumidity;

    private String mMinTemp;

    private String mMaxTemp;

    private String mPressure;

    private String mWeatherReport;

    private String mDayTemp;

    private boolean mLoading;

    public WeatherViewModel(Application application, IWeatherApiManager weatherApiManager) {
        super(application);
        mWeatherApiManager = weatherApiManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setLoading(true);
        mDisposable = mWeatherApiManager
                .getWeather()
                .subscribe(forecast -> {
                    setLoading(false);
                    setResults(forecast);
                    notifyBindings();
                }, throwable -> {
                    setLoading(false);
                    notifyError();
                });
    }

    private void setResults(Forecast forecast) {
        if (forecast != null) {
            if (forecast.weather != null) {
                mWeatherReport = (!forecast.weather.isEmpty() && forecast.weather.size() == 1) ? forecast.weather.get(0).title : "";
                final double pressure = (forecast.main != null) ?
                        forecast.main.pressure : forecast.pressure;
                final double humidity = (forecast.main != null) ?
                        forecast.main.humidity : forecast.humidity;

                mPressure = "Pressure: " + String.valueOf(pressure) + " hPa";
                mHumidity = "Humidity: " + String.valueOf(humidity) + " %";

                final double temp = (forecast.temperature != null) ?
                        forecast.temperature.temp : forecast.main.temp;
                final double max = (forecast.temperature != null) ?
                        forecast.temperature.tempMax : forecast.main.tempMax;
                final double min = (forecast.temperature != null) ?
                        forecast.temperature.tempMin : forecast.main.tempMin;

                mDayTemp = String.valueOf(kelvinToCelcius(temp));
                mMinTemp = "Min temperature: " + (kelvinToCelcius(min)) + " deg C";
                mMaxTemp = "Max temperature: " + (kelvinToCelcius(max)) + " deg C";
            }
        }
    }

    private void notifyError() {

    }

    public void notifyBindings() {
        notifyPropertyChanged(BR.weatherReport);
        notifyPropertyChanged(BR.dayTemp);
        notifyPropertyChanged(BR.humidity);
        notifyPropertyChanged(BR.maxTemp);
        notifyPropertyChanged(BR.minTemp);
        notifyPropertyChanged(BR.pressure);
    }

    @Bindable
    public String getWeatherReport() {
        return mWeatherReport;
    }

    @Bindable
    public String getDayTemp() {
        return mDayTemp;
    }

    @Bindable
    public String getHumidity() {
        return mHumidity;
    }

    @Bindable
    public String getMaxTemp() {
        return mMaxTemp;
    }

    @Bindable
    public String getMinTemp() {
        return mMinTemp;
    }

    @Bindable
    public String getPressure() {
        return mPressure;
    }

    private void setLoading(boolean loading) {
        mLoading = loading;
        notifyPropertyChanged(BR.loading);
    }

    @Bindable
    public boolean isLoading() {
        return mLoading;
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
