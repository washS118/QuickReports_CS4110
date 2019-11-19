package com.quickreports.OpenWeatherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {
    @Expose
    @SerializedName("id")
    public int id;

    @Expose
    @SerializedName("main")
    public String main;

    @Expose
    @SerializedName("description")
    public String description;

    @Expose
    @SerializedName("icon")
    public String icon;
}
