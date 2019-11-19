package com.quickreports.OpenWeatherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind {
    @Expose
    @SerializedName("speed")
    public double speed;

    @Expose
    @SerializedName("deg")
    public int deg;
}
