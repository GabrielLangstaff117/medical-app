package com.example.medicalapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "hospital.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String COL_ID = "id";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";

    private static final String TABLE_PATIENTS = "patients";
    private static final String COL_PATIENT_ID = "id";
    private static final String COL_PATIENT_NAME = "name";
    private static final String COL_PATIENT_AGE = "age";
    private static final String COL_PATIENT_GENDER = "gender";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_USERNAME + " TEXT, "
                + COL_PASSWORD + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_PATIENTS_TABLE = "CREATE TABLE " + TABLE_PATIENTS + "("
                + COL_PATIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_PATIENT_NAME + " TEXT, "
                + COL_PATIENT_AGE + " INTEGER, "
                + COL_PATIENT_GENDER + " TEXT)";
        db.execSQL(CREATE_PATIENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENTS);
        onCreate(db);
    }

    public boolean addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean authenticateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE "
                + COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean isAuthenticated = cursor.getCount() > 0;
        cursor.close();
        return isAuthenticated;
    }

    public Cursor getPatients() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_PATIENTS;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {

            String[] columnNames = cursor.getColumnNames();
            for (String columnName : columnNames) {
                Log.d("DatabaseHelper", "Column: " + columnName); // Log each column name
            }

            int nameIndex = cursor.getColumnIndex(COL_PATIENT_NAME);
            int ageIndex = cursor.getColumnIndex(COL_PATIENT_AGE);
            int genderIndex = cursor.getColumnIndex(COL_PATIENT_GENDER);

            if (nameIndex == -1 || ageIndex == -1 || genderIndex == -1) {

                if (nameIndex == -1) {
                    Log.e("DatabaseHelper", "Column not found: " + COL_PATIENT_NAME);
                }
                if (ageIndex == -1) {
                    Log.e("DatabaseHelper", "Column not found: " + COL_PATIENT_AGE);
                }
                if (genderIndex == -1) {
                    Log.e("DatabaseHelper", "Column not found: " + COL_PATIENT_GENDER);
                }
            }
        }

        return cursor;
    }

    public long addPatient(ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_PATIENTS, null, values);
    }

    public int deletePatient(String patientName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COL_PATIENT_NAME + " = ?";
        String[] whereArgs = {patientName};
        return db.delete(TABLE_PATIENTS, whereClause, whereArgs);
    }
}
