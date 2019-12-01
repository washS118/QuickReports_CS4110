package com.quickreports.Managers;

import com.quickreports.Models.OpenWeatherModels.Model;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface ApiService {
    /**
     * Retrieve data from OpenWeather API
     * @param lat Latitude of the device
     * @param lon Longitude of the device
     * @param appid API Key
     * @return a model as defined by OpenWeather API
     */
    @GET("weather?units=imperial")
    Single<Model> GetWeatherData(@Query("lat") double lat,
                                 @Query("lon") double lon,
                                 @Query("appid") String appid);
}