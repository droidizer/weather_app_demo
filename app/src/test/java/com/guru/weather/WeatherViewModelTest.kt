package com.guru.weather

import android.content.res.Resources
import com.google.gson.Gson
import com.guru.weather.helpers.JsonFileReader
import com.guru.weather.models.Forecast
import com.guru.weather.models.WeatherForecastModel
import com.guru.weather.network.manager.WeatherApiManager
import com.guru.weather.ui.viewmodel.CurrentWeatherViewModel
import com.guru.weather.ui.viewmodel.WeatherForecastViewModel
import com.guru.weather.ui.viewmodel.WeatherViewModel
import io.reactivex.Observable
import org.fest.assertions.api.Assertions
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times

class WeatherViewModelTest : BaseTest() {

    @Mock
    lateinit var mWeatherManager: WeatherApiManager

    @Mock
    lateinit var mResources: Resources

    private val mWeatherModel: WeatherViewModel by lazy {
        WeatherViewModel(mApplication, mResources, mWeatherManager)
    }

    @Test
    fun test_item_click() {
        val weatherVm = Mockito.spy(WeatherViewModel(mApplication, mResources, mWeatherManager))
        weatherVm.handleClick()
        Mockito.verify(weatherVm, times(1)).notifyMessage(R.string.item_clicked)
    }

    @Test
    fun test_weather_data_is_valid() {
        `when`(mWeatherManager.weather)
                .thenReturn(getWeather())
        `when`(mWeatherManager.getWeatherForecast(Mockito.anyInt()))
                .thenReturn(getWeatherForecast())

        mWeatherModel.subscribeForCurrentWeatherData()
        Assertions.assertThat(mWeatherModel.forecastItems).isNotNull
        Assertions.assertThat(mWeatherModel.forecastItems.size).isNotZero
        Assertions.assertThat(mWeatherModel.forecastItems.size).isEqualTo(6)
        Assertions.assertThat(mWeatherModel.forecastItems.get(0)).isInstanceOf(CurrentWeatherViewModel::class.java)
        Assertions.assertThat(mWeatherModel.forecastItems.get(1)).isInstanceOf(WeatherForecastViewModel::class.java)
        Assertions.assertThat(mWeatherModel.isErrorVisible).isFalse
    }

    @Test
    fun test_weather_data_error() {
        `when`(mWeatherManager.weather)
                .thenReturn(Observable.error(Throwable()))
        `when`(mWeatherManager.getWeatherForecast(10))
                .thenReturn(getWeatherForecast())

        mWeatherModel.subscribeForCurrentWeatherData()
        Assertions.assertThat(mWeatherModel.isErrorVisible)
        Assertions.assertThat(mWeatherModel.errorMessage).isEqualToIgnoringCase(mResources.getString(R.string.connection_error))
        Assertions.assertThat(mWeatherModel.isLoading).isFalse
        Assertions.assertThat(mWeatherModel.forecastItems.size).isZero
    }

    private fun getWeather(): Observable<Forecast> =
            JsonFileReader.read(javaClass.classLoader.getResourceAsStream("current_weather_forecast.json"), Gson(), Forecast::class.java)

    private fun getWeatherForecast(): Observable<WeatherForecastModel> =
            JsonFileReader.read(javaClass.classLoader.getResourceAsStream("weather_forecast.json"), Gson(), WeatherForecastModel::class.java)

}