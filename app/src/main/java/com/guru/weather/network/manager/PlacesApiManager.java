package com.guru.weather.network.manager;

import android.content.res.Resources;

import com.guru.weather.R;
import com.guru.weather.models.Places;
import com.guru.weather.network.service.PlacesApiService;


import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class PlacesApiManager {

    private final PlacesApiService mPlacesApiService;
    private final Resources mResources;

    @Inject
    public PlacesApiManager(PlacesApiService placesApiService, Resources resources) {
        mPlacesApiService = placesApiService;
        mResources = resources;
    }

    public Observable<Places> getCity(String input) {
        return mPlacesApiService.getCity(mResources.getString(R.string.places_key), input);
    }
}
