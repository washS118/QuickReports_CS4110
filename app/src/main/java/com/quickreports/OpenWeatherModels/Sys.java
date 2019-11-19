package com.quickreports.OpenWeatherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sys {
    @Expose
    @SerializedName("type")
    public double type;

    @Expose
    @SerializedName("id")
    public int id;

    @Expose
    @SerializedName("message")
    public double message;

    @Expose
    @SerializedName("country")
    public String country;

    @Expose
    @SerializedName("sunrise")
    public int sunrise;

    @Expose
    @SerializedName("sunset")
    public int sunset;
}
