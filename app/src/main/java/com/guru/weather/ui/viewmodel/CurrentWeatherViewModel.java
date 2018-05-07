package com.guru.weather.ui.viewmodel;

import android.app.Application;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.guru.weather.models.Forecast;
import com.guru.weather.utils.AndroidBaseViewModel;

import static com.guru.weather.misc.AppUtils.kelvinToCelcius;

public class CurrentWeatherViewModel extends AndroidBaseViewModel {

    private String mHumidity;

    private String mMinTemp;

    private String mMaxTemp;

    private String mPressure;

    private String mWeatherReport;

    private String mTempNow;

    public CurrentWeatherViewModel(Application application, Forecast forecast) {
        super(application);
        setResults(forecast);
    }

    private void setResults(Forecast forecast) {
        if (forecast.main != null) {
            setTempNow(forecast.main.temp);
            setHumidity(forecast.main.humidity);
            setMaxTemp(forecast.main.tempMax);
            setMinTemp(forecast.main.tempMin);
            setPressure(forecast.main.pressure);
        }
        if (forecast.weather != null && !forecast.weather.isEmpty()) {
            setWeatherReport(forecast.weather.get(0).title);
        }
    }

    public void setHumidity(int humidity) {
        mHumidity = "Humidity: ".concat(String.valueOf(humidity)).concat(" %");
    }

    public void setMinTemp(double tempMin) {
        mMinTemp = "Min temp: ".concat(String.valueOf(kelvinToCelcius(tempMin))).concat(" deg C");
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

    public void setTempNow(double temp) {
        mTempNow = String.valueOf(kelvinToCelcius(temp));
    }

    public String getWeatherReport() {
        return mWeatherReport;
    }

    public String getTempNow() {
        return mTempNow;
    }

    public String getHumidity() {
        return mHumidity;
    }

    public String getMaxTemp() {
        return mMaxTemp;
    }

    public String getMinTemp() {
        return mMinTemp;
    }

    public String getPressure() {
        return mPressure;
    }
}
