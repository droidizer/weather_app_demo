package com.guru.weather.di;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.guru.weather.BuildConfig;
import com.guru.weather.network.service.PlacesApiService;
import com.guru.weather.network.service.WeatherApiService;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private static final int CONNECTION_TIMEOUT_SECS = 20;

    private static final long HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 5 * 1024 * 1024;

    private static final String BASE_WEATHER_URL = "http://api.openweathermap.org/data/2.5";

    private static final String BASE_PLACES_URL = "https://maps.googleapis.com/maps/api/place/autocomplete";

    @Provides
    public Cache provideCacheFile(Context context) {
        final File baseDir = context.getCacheDir();
        if (baseDir != null) {
            final File cacheDir = new File(baseDir, "HttpResponseCache");
            return new Cache(cacheDir, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
        } else {
            return null;
        }
    }

    @Singleton
    @Provides
    public Gson providesGson() { return new GsonBuilder().create(); }

    @Provides
    @Singleton
    public GsonConverterFactory provideGsonConverterLibrary(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    public RxJava2CallAdapterFactory providesRxJava2CallAdapter() {
        return RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient(Cache cache) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        if (cache != null) {
            okHttpClientBuilder.cache(cache);
        }

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(logging);
        }

        okHttpClientBuilder
                .connectTimeout(CONNECTION_TIMEOUT_SECS, TimeUnit.SECONDS)
                .readTimeout(CONNECTION_TIMEOUT_SECS, TimeUnit.SECONDS)
                .writeTimeout(CONNECTION_TIMEOUT_SECS, TimeUnit.SECONDS);

        return okHttpClientBuilder.build();
    }

    @Provides
    @Singleton
    public WeatherApiService provideWeatherApiService(GsonConverterFactory gsonConverterFactory, OkHttpClient okHttpClient, RxJava2CallAdapterFactory rxJava2CallAdapterFactory) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_WEATHER_URL)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .addConverterFactory(gsonConverterFactory);
        Retrofit retrofit = builder.client(okHttpClient).build();
        return retrofit.create(WeatherApiService.class);
    }

    @Provides
    @Singleton
    public PlacesApiService providePlacesApiService(GsonConverterFactory gsonConverterFactory, OkHttpClient okHttpClient, RxJava2CallAdapterFactory rxJava2CallAdapterFactory) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_PLACES_URL)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .addConverterFactory(gsonConverterFactory);
        Retrofit retrofit = builder.client(okHttpClient).build();
        return retrofit.create(PlacesApiService.class);
    }
}