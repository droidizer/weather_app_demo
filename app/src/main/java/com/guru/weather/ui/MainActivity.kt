package com.guru.weather.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.android.databinding.library.baseAdapters.BR
import com.guru.weather.R
import com.guru.weather.ui.viewmodel.WeatherViewModel
import com.guru.weather.utils.AndroidBaseInjectableActivity
import javax.inject.Inject

class MainActivity : AndroidBaseInjectableActivity() {

    @Inject
    lateinit var mFactory: WeatherViewModel.Factory

    private lateinit var mViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, mFactory).get(WeatherViewModel::class.java)
        setContentView(R.layout.activity_main, BR.weatherViewModel, mViewModel)
    }
}
