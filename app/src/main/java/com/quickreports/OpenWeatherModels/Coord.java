package com.quickreports.OpenWeatherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coord {
    @Expose
    @SerializedName("lon")
    public double lon;

    @Expose
    @SerializedName("lat")
    public double lat;
}
