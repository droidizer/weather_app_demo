package com.guru.weather.ui.viewmodel;

import com.guru.weather.BR;
import com.guru.weather.R;
import com.guru.weather.models.Forecast;
import com.guru.weather.network.manager.IWeatherApiManager;
import com.guru.weather.utils.AndroidBaseViewModel;
import com.guru.weather.utils.rv.AndroidItemBinder;
import com.guru.weather.utils.rv.ItemClickListener;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.res.Resources;
import android.databinding.Bindable;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import retrofit2.HttpException;

public class WeatherViewModel extends AndroidBaseViewModel {

    private final IWeatherApiManager mWeatherApiManager;

    private Disposable mWeatherDisposable = Disposables.disposed();

    private boolean mLoading;

    private String mErrorMessage;

    private boolean mErrorVisible;

    private Resources mResources;

    private Map<Class<?>, AndroidItemBinder> mTemplates;

    private List<AndroidBaseViewModel> mForecastList = new ArrayList<>();

    public WeatherViewModel(Application application, Resources resources, IWeatherApiManager weatherApiManager) {
        super(application);
        mWeatherApiManager = weatherApiManager;
        mResources = resources;
    }

    public RecyclerView.ItemDecoration getItemDecoration() {
        int bottomMargin = (int) mResources.getDimension(R.dimen.margin_4);
        int horizontalMargin = (int) mResources.getDimension(R.dimen.activity_horizontal_margin);
        return new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                if (position != 0) {
                    try {
                        AndroidBaseViewModel viewModel = mForecastList.get(position);
                        if (viewModel instanceof WeatherForecastViewModel) {
                            outRect.top = bottomMargin;
                            outRect.right = horizontalMargin;
                            outRect.left = horizontalMargin;
                        }
                    } catch (Exception e) {
                    }
                }
            }
        };
    }

    @Bindable
    public String getErrorMessage() {
        return mErrorMessage;
    }

    @Bindable
    public boolean isErrorVisible() {
        return mErrorVisible;
    }

    public ItemClickListener getItemClickListener() {
        return ((view, item) -> {
            if (item instanceof WeatherForecastViewModel) {

            }
        });
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
                        mForecastList.add(new CurrentWeatherViewModel(getApplication(), forecast.first));
                        if (!forecast.second.getForecast().isEmpty()) {
                            for (Forecast f : forecast.second.getForecast())
                                mForecastList.add(new WeatherForecastViewModel(getApplication(), f));
                        }
                        notifyBindings();
                    }, throwable -> {
                        setLoading(false);
                        notifyError(throwable);
                    });
        }
    }

    public Map<Class<?>, AndroidItemBinder> getTemplatesForObjects() {
        if (mTemplates == null) {
            mTemplates = new HashMap<>();
            mTemplates.put(CurrentWeatherViewModel.class, new AndroidItemBinder(R.layout.layout_current_weather, BR.currentWeatherViewModel));
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
        notifyPropertyChanged(BR.errorMessage);
    }

    private void setErrorVisible(boolean errorVisible) {
        mErrorVisible = !isLoading() && errorVisible;
        notifyPropertyChanged(BR.errorVisible);
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
        mForecastList = null;
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
