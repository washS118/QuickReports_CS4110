package com.quickreports.Models;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReportModel {
    public int id;
    public String title;
    public String desc;
    public LocalDate submissionDate;
    public LocalTime submissionTime;
    public String imgPath;
    public WeatherModel weather;
}
