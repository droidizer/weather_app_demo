package com.guru.weather.network.manager;


import com.guru.weather.models.Places;

import io.reactivex.Observable;

public interface IPlacesApiManager {

    Observable<Places> getCity(String key, String input);
}
