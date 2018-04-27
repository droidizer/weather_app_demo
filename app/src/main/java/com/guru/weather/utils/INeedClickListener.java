package com.guru.weather.utils;

import com.guru.weather.misc.ClickItemWrapper;
import com.guru.weather.misc.SingleLiveEvent;

import android.arch.lifecycle.LifecycleObserver;

public interface INeedClickListener extends LifecycleObserver {

    SingleLiveEvent<ClickItemWrapper> getItemClickListenerNotifier();
}