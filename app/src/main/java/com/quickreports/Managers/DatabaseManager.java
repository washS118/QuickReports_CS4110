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

    public static final String REPORTS_TABLE = "reportsTable";
    public static final String ID = "rId";
    public static final String TITLE = "rTitle";
    public static final String DESC = "rDesc";
    public static final String SUBMIT_TIME = "submitTime";
    public static final String SUBMIT_DATE = "submitDate";
    public static final String PHOTO_PATH = "photoPath";
    public static final String CONDITION = "wCondition";
    public static final String TEMP = "temperature";

    public DatabaseManager(Context context) {
        super(context, DBName, null, 2);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format(
                "CREATE TABLE %s"//Table Name
                + "(" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT," + //ID
                        "%s TEXT," + //Title
                        "%s TEXT," + //Description
                        "%s TEXT," + //Submit Time
                        "%s TEXT," + //Submit Date
                        "%s TEXT," + //Photo Path
                        "%s TEXT," + //Condition
                        "%s REAL" + //Temp
                  ")"
                ,REPORTS_TABLE, ID, TITLE, DESC, SUBMIT_TIME, SUBMIT_DATE, PHOTO_PATH, CONDITION, TEMP
        );
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format("DROP TABLE %s", REPORTS_TABLE);
        db.execSQL("DROP TABLE " + REPORTS_TABLE);
        onCreate(db);
    }

    public boolean InsertUpdateReport(ReportModel model){
        if (model.id == 0) {
            return addReport(model);
        }
        else {
            return updateReport(model);
        }
    }

    private boolean addReport(ReportModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE, model.title);
        contentValues.put(DESC, model.desc);
        contentValues.put(SUBMIT_TIME, model.submissionTime.toString());
        contentValues.put(SUBMIT_DATE, model.submissionDate.toString());
        contentValues.put(PHOTO_PATH, model.imgPath);
        contentValues.put(CONDITION, model.weather.condition);
        contentValues.put(TEMP, model.weather.temp);

        long result = db.insert(REPORTS_TABLE, null, contentValues);
        if(result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    private boolean updateReport(ReportModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE, model.title);
        contentValues.put(DESC, model.desc);
        contentValues.put(SUBMIT_TIME, model.submissionTime.toString());
        contentValues.put(SUBMIT_DATE, model.submissionDate.toString());
        contentValues.put(PHOTO_PATH, model.imgPath);
        contentValues.put(CONDITION, model.weather.condition);
        contentValues.put(TEMP, model.weather.temp);

        long result = db.update(REPORTS_TABLE, contentValues, String.format("%s = %d", ID, model.id), null);
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
            model.submissionTime = LocalTime.parse(cursor.getString(3));
            model.submissionDate = LocalDate.parse(cursor.getString(4));
            model.imgPath = cursor.getString(5);
            model.weather.condition = cursor.getString(6);
            model.weather.temp = cursor.getInt(7);
            reports.add(model);
        }
        return report;
    }

    public ReportModel getReportById(int ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s = %d", REPORTS_TABLE, DatabaseManager.ID, ID);
        Cursor res = db.rawQuery(query,null);

        ReportModel[] results = getModelArray(res);
        if (results.length < 1) return null;
        return results[0];
    }

    public ReportModel[] getAllReports() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %s", REPORTS_TABLE);
        Cursor res = db.rawQuery(query, null);
        return getModelArray(res);
    }
}
