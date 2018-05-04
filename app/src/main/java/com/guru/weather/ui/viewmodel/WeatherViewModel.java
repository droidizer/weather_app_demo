package com.guru.weather.ui.viewmodel;

import com.guru.weather.BR;
import com.guru.weather.R;
import com.guru.weather.models.Forecast;
import com.guru.weather.network.manager.IWeatherApiManager;
import com.guru.weather.utils.AndroidBaseViewModel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.res.Resources;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.util.Pair;

import java.io.IOException;

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
            mWeatherDisposable = Observable.zip(mWeatherApiManager.getWeather(), mWeatherApiManager.getWeatherForecast(7),
                    Pair::new)
                    .subscribe(forecast -> {
                        setLoading(false);
                        setResults(forecast.first);
                        notifyBindings();
                    }, throwable -> {
                        setLoading(false);
                        notifyError(throwable);
                    });
        }
    }

    private void setResults(Forecast forecast) {
        if (forecast.main != null) {
            setDayTemp(forecast.main.temp);
            setHumidity(forecast.main.humidity);
            setMaxTemp(forecast.main.tempMax);
            setMinTemp(forecast.main.tempMin);
            setPressure(forecast.main.pressure);
        }
        if (forecast.weather != null && !forecast.weather.isEmpty()) {
            setWeatherReport(forecast.weather.get(0).title);
        }
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
        notifyPropertyChanged(BR.errorMessage);
    }

    private void setErrorVisible(boolean errorVisible) {
        mErrorVisible = !isLoading() && errorVisible;
        notifyPropertyChanged(BR.errorVisible);
    }

    public void notifyBindings() {
        notifyPropertyChanged(BR.weatherReport);
        notifyPropertyChanged(BR.dayTemp);
        notifyPropertyChanged(BR.humidity);
        notifyPropertyChanged(BR.maxTemp);
        notifyPropertyChanged(BR.minTemp);
        notifyPropertyChanged(BR.pressure);
    }

    public void setHumidity(int humidity) {
        mHumidity = "Humidity: ".concat(String.valueOf(humidity)).concat(" %");
    }

    public void setMinTemp(double tempMin) {
        mMinTemp = "Min tem: ".concat(String.valueOf(kelvinToCelcius(tempMin))).concat(" deg C");
    }

    public void setMaxTemp(double tempMax) {
        mMaxTemp = "Max temp: ".concat(String.valueOf(kelvinToCelcius(tempMax))).concat(" deg C");
    }

    public void setPressure(double pressure) {
        mPressure = "Pressure: ".concat(String.valueOf(pressure)).concat(" hPa");
    }

    public void setWeatherReport(String title) {
        mWeatherReport = title;
    }

    public void setDayTemp(double temp) {
        mDayTemp = String.valueOf(kelvinToCelcius(temp));
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

    @Bindable
    public boolean getErrorVisible() {
        return mErrorVisible;
    }

    @Bindable
    public String getErrorMessage() {
        return mErrorMessage;
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
