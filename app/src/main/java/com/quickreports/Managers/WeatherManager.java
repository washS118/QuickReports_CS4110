package com.quickreports.Managers;

import android.location.Location;

import com.quickreports.Models.OpenWeatherModels.Weather;
import com.quickreports.Models.WeatherModel;
import com.quickreports.Models.OpenWeatherModels.Model;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherManager {
    private Retrofit retrofit;
    private ApiService service;

    private ApiSuccess successFunc;
    private ApiError errorFunc;

    public WeatherManager(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/weather/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        service = retrofit.create(ApiService.class);
    }

    public void GetWeatherData(Location location){
        service.GetWeatherData(location.getLatitude(), location.getLongitude())
                .subscribe(new SingleObserver<Model>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Model result) {
                        if (successFunc == null) return;

                        successFunc.success(CreateModel(result));
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (errorFunc == null) return;

                        errorFunc.error(e.getMessage());
                    }
                });
    }

    public void SetSuccessFunction(ApiSuccess success){
        successFunc = success;
    }

    public void SetErrorFunction(ApiError error){
        errorFunc = error;
    }

    private WeatherModel CreateModel(Model apiResult){
        WeatherModel model = new WeatherModel();

        model.condition = apiResult.weather.get(0).main;
        model.description = apiResult.weather.get(0).description;
        model.temp = apiResult.main.temp;

        return model;
    }
}
