package com.quickreports.Managers;

import com.quickreports.Models.OpenWeatherModels.Model;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface ApiService {

    @GET("api.openweathermap.org/data/2.5/weather?lat=<<lat>>&lon=<<lon>>&APPID=<<key>>")
    Single<Model> GetWeatherData(@Query("lat") double lat,
                                 @Query("lon") double lon,
                                 @Query("key") String apiKey);
}