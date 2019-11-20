package com.quickreports.Models.OpenWeatherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Model {
    @Expose
    @SerializedName("coord")
    public Coord coord;

    @Expose
    @SerializedName("weather")
    public ArrayList<Weather> weather;

    @Expose
    @SerializedName("base")
    public String base;

    @Expose
    @SerializedName("main")
    public Main main;

    @Expose
    @SerializedName("visibility")
    public int visibility;

    @Expose
    @SerializedName("wind")
    public Wind wind;

    @Expose
    @SerializedName("clouds")
    public Clouds clouds;

    @Expose
    @SerializedName("rain")
    public Rain rain;

    @Expose
    @SerializedName("snow")
    public Snow snow;

    @Expose
    @SerializedName("dt")
    public String dt;

    @Expose
    @SerializedName("sys")
    public Sys sys;

    @Expose
    @SerializedName("timezone")
    public int timezone;

    @Expose
    @SerializedName("id")
    public int id;

    @Expose
    @SerializedName("name")
    public String name;

    @Expose
    @SerializedName("cod")
    public int cod;
}
