package com.guru.weather.network.manager;

import com.guru.weather.network.service.PlacesApiService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PlacesApiManager {

    private final PlacesApiService mPlacesApiService;

    @Inject
    PlacesApiManager(PlacesApiService placesApiService) {
        mPlacesApiService = placesApiService;
    }
}
