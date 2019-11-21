package com.quickreports.Managers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseManager extends SQLiteOpenHelper {

    //SQLiteDatabase mydatabase = SQLiteDatabase.openOrCreateDatabase("QuickReportsDB", );
    public static final String DBName = "QuickReports.db";
    public static final String table1Name = "reportsTable";
    public static final String column1 = "rId";
    public static final String column2 = "rTitle";
    public static final String column3 = "rDesc";
    public static final String column4 = "submitTime";
    public static final String column5 = "photo";
    public static final String column6 = "wId";

    public static final String table2Name = "weatherTable";
    public static final String table2column1 = "wId";
    public static final String table2column2 = "wCondition";
    public static final String table2column3 = "temperature";


    public DatabaseManager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DBName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + table1Name + "(rId INTEGER PRIMARY KEY AUTOINCREMENT,rTitle TEXT,rDesc TEXT,submitTime TIME,photo,wId)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
