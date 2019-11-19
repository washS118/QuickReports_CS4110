package com.quickreports.OpenWeatherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {
    @Expose
    @SerializedName("temp")
    public double temp;

    @Expose
    @SerializedName("pressure")
    public int pressure;

    @Expose
    @SerializedName("humidity")
    public int humidity;

    @Expose
    @SerializedName("temp_min")
    public double tempMin;

    @Expose
    @SerializedName("temp_max")
    public double tempMax;
}
