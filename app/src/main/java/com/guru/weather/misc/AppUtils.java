package com.codechallenge.app.utils;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.widget.TextView;
import com.codechallenge.app.R;
import com.common.android.utils.ContextHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static com.common.android.utils.ContextHelper.getContext;

public class AppUtils {

    public static String formatDate(@NonNull final SimpleDateFormat sdf, @NonNull final long position) {

        final Date date = new Date(position * 1000L); // *1000 is to convert seconds to milliseconds
        sdf.setTimeZone(TimeZone.getTimeZone("CET")); // Converting UTC to CET Time Zone
        return sdf.format(date);
    }

    public static int kelvinToCelcius(@NonNull final double temperature) {
        return (int)((temperature) - 273.15); // Temperature metric conversion
    }

    public static void saveCity(@NonNull final String cityName) {
        SharedPreferences sharedPref = getContext().getPreferences(getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(String.valueOf((R.string.city)), cityName);
        editor.commit();
    }

    public static String retrieveCity(){
        SharedPreferences sharedPref = getContext().getPreferences(ContextHelper.getContext().MODE_PRIVATE);
        return sharedPref.getString(String.valueOf((R.string.city)), "");
    }

    public static void setWeatherTypeFace(@NonNull final TextView weatherIcon, @NonNull final  String icon) {
        switch (icon)

        {
            case "01d":
                weatherIcon.setText(R.string.wi_day_sunny);
                break;
            case "02d":
                weatherIcon.setText(R.string.wi_cloudy_gusts);
                break;
            case "03d":
                weatherIcon.setText(R.string.wi_cloud_down);
                break;
            case "04d":
                weatherIcon.setText(R.string.wi_cloudy);
                break;
            case "04n":
                weatherIcon.setText(R.string.wi_night_cloudy);
                break;
            case "10d":
                weatherIcon.setText(R.string.wi_day_rain_mix);
                break;
            case "11d":
                weatherIcon.setText(R.string.wi_day_thunderstorm);
                break;
            case "13d":
                weatherIcon.setText(R.string.wi_day_snow);
                break;
            case "01n":
                weatherIcon.setText(R.string.wi_night_clear);
                break;
            case "02n":
                weatherIcon.setText(R.string.wi_night_cloudy);
                break;
            case "03n":
                weatherIcon.setText(R.string.wi_night_cloudy_gusts);
                break;
            case "10n":
                weatherIcon.setText(R.string.wi_night_cloudy_gusts);
                break;
            case "11n":
                weatherIcon.setText(R.string.wi_night_rain);
                break;
            case "13n":
                weatherIcon.setText(R.string.wi_night_snow);
                break;
        }
    }
}
