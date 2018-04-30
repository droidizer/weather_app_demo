package com.guru.weather.ui

import android.os.Bundle
import com.guru.weather.utils.AndroidBaseInjectableActivity
import com.guru.weather.R

class MainActivity : AndroidBaseInjectableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
