package com.quickreports.Managers;

import com.quickreports.Models.WeatherModel;

public interface ApiSuccess {
    /**
     * Handles weather request success
     * @param model all weather data needed for QuickReports
     */
    void success(WeatherModel model);
}
