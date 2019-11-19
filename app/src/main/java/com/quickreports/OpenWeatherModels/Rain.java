package com.quickreports.OpenWeatherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rain {
    @Expose
    @SerializedName("1h")
    public double hour1;

    @Expose
    @SerializedName("3h")
    public double hour3;
}
