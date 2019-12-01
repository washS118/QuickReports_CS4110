package com.quickreports.Managers;

public interface ApiError {
    /**
     * Handles errors generated in WeatherManager
     * @param message Error message
     */
    void error(String message);
}
