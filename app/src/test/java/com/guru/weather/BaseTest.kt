package com.guru.weather

import android.app.Application
import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.guru.weather.helpers.CustomSchedulersTestRule
import com.guru.weather.models.Forecast
import com.guru.weather.models.Temperature
import org.junit.Before
import org.junit.ClassRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
open class BaseTest {

    @Mock
    lateinit var mApplication: Application

    companion object {
        @ClassRule
        @JvmField
        val schedulers = CustomSchedulersTestRule()

        @ClassRule
        @JvmField
        val instantRule = InstantTaskExecutorRule()
    }

    @Before
    open fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    fun getForecast(): Forecast {
        val forecast = Forecast()
        forecast.temperature = Temperature()
        forecast.temperature.temp = 20.00
        forecast.humidity = 50
        forecast.pressure = 102.00
        forecast.dt = 1525699640
        return forecast
    }

}