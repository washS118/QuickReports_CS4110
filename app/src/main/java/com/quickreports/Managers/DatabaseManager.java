package com.quickreports.Managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {


    public static final String DBName = "QuickReports.db";

    public static final String table1Name = "reportsTable";
    public static final String t1Col1 = "rId";
    public static final String t1Col2 = "rTitle";
    public static final String t1Col3 = "rDesc";
    public static final String t1Col4 = "submitTime";
    public static final String t1Col5 = "photoPath";
    public static final String t1Col6 = "wId";
    public static final String table2Name = "weatherTable";

    public DatabaseManager(Context context) {
        super(context, DBName, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + table1Name + "(rId INTEGER PRIMARY KEY AUTOINCREMENT,rTitle TEXT,rDesc TEXT,submitTime TEXT,photoPath TEXT,wId INTEGER, FOREIGN KEY(wId) REFERENCES " +table2Name+" (wId))");
        db.execSQL("CREATE TABLE " + table2Name + "(wId INTEGER PRIMARY KEY AUTOINCREMENT, wCondition TEXT, temperature TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + table2Name);
        db.execSQL("DROP TABLE " + table1Name);
        onCreate(db);
    }

    public boolean addReport(String rTitle, String rDesc, String submitTime, String photoPath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(t1Col2, rTitle);
        contentValues.put(t1Col3, rDesc);
        contentValues.put(t1Col4, submitTime);
        contentValues.put(t1Col5, photoPath);
        long result = db.insert(table1Name, null, contentValues);
        if(result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public void addWeatherReport(String rTitle, String rDesc, String submitTime, String photoPath) {

    }
}
