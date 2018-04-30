package com.guru.weather.network.service;

import com.guru.weather.models.Places;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PlacesApiService {

    @GET("/json?&types=(cities)&input={input}&key={key}")
    Observable<Places> cityPredictions(@Path("key") String key, @Path("input") String input);
}