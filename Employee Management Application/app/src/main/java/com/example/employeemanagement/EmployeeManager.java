package com.example.employeemanagement;

/*
Final Project
Android Application development
Ronen Agarunov - ragarunov@myseneca.ca
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.employeemanagement.domain.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

class EmployeeManager {

    // Call Database helper
    private DatabaseHelper dbHelper;

    // Constructor
    EmployeeManager(Context context) { dbHelper = DatabaseHelper.getInstance(context); }

    // Returns a List of all employees from database
    List<Employee> getEmployees() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_NAME,
                null
        );

        List<Employee> employees = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Employee employee = new Employee(
                        cursor.getLong(cursor.getColumnIndex(DatabaseHelper.EMPLOYEE_ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.FULL_NAME)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.ADDRESS)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.PHONE)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.POSITION)),
                        cursor.getLong(cursor.getColumnIndex(DatabaseHelper.ID))
                );

                employees.add(employee);
                cursor.moveToNext();
            }
        }

        cursor.close();
        return employees;
    }

    // Returns a String value of a single employee
    String getEmployee(int id) {

        String empString = ""; // Initialise

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE "
                + DatabaseHelper.EMPLOYEE_ID + " = " + id;

        Cursor c = db.rawQuery(query,null);

        // If search returns any results- add to string
        if (c.getCount() > 0) {
            c.moveToFirst();
            empString = "Name: ";
            empString += c.getString(c.getColumnIndex(DatabaseHelper.FULL_NAME));
            empString += "\n";

            empString += "Position: ";
            empString += c.getString(c.getColumnIndex(DatabaseHelper.POSITION));
            empString += "\n";

            empString += "Address: ";
            empString += c.getString(c.getColumnIndex(DatabaseHelper.ADDRESS));
            empString += "\n";

            empString += "Phone: ";
            empString += c.getString(c.getColumnIndex(DatabaseHelper.PHONE));
            empString += "\n";

        }

        else if (c.getCount() == 0) // If not results returns, return this message
            empString += "No employee under such ID";

        c.close(); // cursor closure
        return empString;
    }

    // Add employee object to database
    void addEmployee(Employee emp) {
        ContentValues newEmployee = new ContentValues();
        newEmployee.put(DatabaseHelper.EMPLOYEE_ID, emp.getEmpId());
        newEmployee.put(DatabaseHelper.FULL_NAME, emp.getName());
        newEmployee.put(DatabaseHelper.ADDRESS, emp.getAddress());
        newEmployee.put(DatabaseHelper.PHONE, emp.getPhone());
        newEmployee.put(DatabaseHelper.POSITION, emp.getPosition());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(DatabaseHelper.TABLE_NAME, null, newEmployee);
    }

    // Remove employee from database
    // Identify by database id
    // database id != employeeId
    void removeEmployee(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] args = new String[] { String.valueOf(id) };

        db.delete(
                DatabaseHelper.TABLE_NAME,
                DatabaseHelper.ID + "=?",
                args
        );
    }

    // Check if value is empty, return bool
    Boolean isEmpty(String value) { return value.equals(""); }

    Boolean doesExist(int id) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE "
                + DatabaseHelper.EMPLOYEE_ID + " = " + id;

        Cursor c = db.rawQuery(query,null);

        // If search returns any results, item exists
        if (c.getCount() == 0) {
            c.close();
            return false;
        }
        else {
            c.close();
            return true;
        }

    }
}
