package com.quickreports.Models;

import java.sql.Time;
import java.util.Date;

public class ReportModel {
    public int id;
    public String title;
    public String desc;
    public Date submisionDate;
    public Time submisionTime;
    public String imgPath;
    public WeatherModel weather;
}
