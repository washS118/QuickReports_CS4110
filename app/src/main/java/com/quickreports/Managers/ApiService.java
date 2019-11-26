package com.quickreports.Managers;

import com.quickreports.Models.OpenWeatherModels.Model;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface ApiService {
    @GET("weather")
    Single<Model> GetWeatherData(@Query("lat") double lat,
                                 @Query("lon") double lon,
                                 @Query("appid") String appid);
}