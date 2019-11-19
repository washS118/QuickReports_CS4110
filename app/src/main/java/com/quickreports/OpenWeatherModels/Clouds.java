package com.quickreports.OpenWeatherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clouds {
    @Expose
    @SerializedName("all")
    public double all;
}
