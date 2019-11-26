package com.quickreports.Managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.quickreports.Models.ReportModel;
import com.quickreports.Models.WeatherModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {


    public static final String DBName = "QuickReports.db";

    public static final String table1Name = "reportsTable";
    public static final String t1Col1 = "rId";
    public static final String t1Col2 = "rTitle";
    public static final String t1Col3 = "rDesc";
    public static final String t1Col4 = "submitTime";
    public static final String t1Col5 = "submitDate";
    public static final String t1Col6 = "photoPath";
    public static final String t1Col7 = "wCondition";
    public static final String t1Col8 = "temperature";

    public DatabaseManager(Context context) {
        super(context, DBName, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + table1Name + "(rId INTEGER PRIMARY KEY AUTOINCREMENT,rTitle TEXT,rDesc TEXT,submitTime TEXT,submitDate TEXT,photoPath TEXT, wCondition TEXT, temperature INTEGER)");
        //db.execSQL("INSERT INTO " + table1Name + "(rTitle, rDesc, submitTime,submitDate, photoPath, wCondition, temperature) VALUES('Report 1', 'Accident not my fault.', '8:00am','11/22/19', 'photopath','Snowy', 88)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + table1Name);
        onCreate(db);
    }

    public boolean addReport(ReportModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(t1Col2, model.title);
        contentValues.put(t1Col3, model.desc);
        contentValues.put(t1Col4, model.submisionTime.toString());
        contentValues.put(t1Col5, model.submisionDate.toString());
        contentValues.put(t1Col6, model.imgPath);
        contentValues.put(t1Col7, model.weather.condition);
        contentValues.put(t1Col8, model.weather.temp);
        long result = db.insert(table1Name, null, contentValues);
        if(result == -1) {
            return false;
        }
        else {
            return true;
        }
    }
    private ReportModel[] getModelArray(Cursor cursor) {
        ArrayList<ReportModel> reports = new ArrayList<>();
        ReportModel[] report = new ReportModel[reports.size()];
        report = reports.toArray(report);
        while(cursor.moveToNext()) {
            ReportModel model = new ReportModel();
            model.weather = new WeatherModel();
            model.id = cursor.getInt(0);
            model.title = cursor.getString(1);
            model.desc = cursor.getString(2);
            model.submisionTime = LocalTime.parse(cursor.getString(3));
            model.submisionDate = LocalDate.parse(cursor.getString(4));
            model.imgPath = cursor.getString(5);
            model.weather.condition = cursor.getString(6);
            model.weather.temp = cursor.getInt(7);
            reports.add(model);

        }
        return report;
    }

    public Cursor getReportById(int ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + table1Name + "WHERE rId = "+ID+";",null);
        return res;
    }

    public ReportModel getAllReports() {
        SQLiteDatabase db = this.getWritableDatabase();
        ReportModel[] model;
        Cursor res = db.rawQuery("SELECT * FROM " + table1Name + ";", null);
        return ;
    }
}
