package com.example.employeemanagement.domain;

/*
Final Project
Android Application development
Ronen Agarunov - ragarunov@myseneca.ca
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Declarations
    private static final String DATABASE_NAME = "employee.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "employees";

    public static final String ID = "_id";
    public static final String EMPLOYEE_ID = "employeeid";
    public static final String FULL_NAME = "fullname";
    public static final String POSITION = "position";
    public static final String ADDRESS = "address";
    public static final String PHONE = "phone";

    private static DatabaseHelper instance = null;

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null)
            instance = new DatabaseHelper(context);

        return instance;
    }

    private DatabaseHelper(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createQuery = "CREATE TABLE " + TABLE_NAME +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "employeeid INTEGER NOT NULL," +
                "fullname TEXT NOT NULL," +
                "position TEXT NOT NULL," +
                "address TEXT NOT NULL," +
                "phone TEXT NOT NULL)";
        db.execSQL(createQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not covered
    }
}
