package com.example.medicalapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword, editTextConfirmPassword;
    private Button buttonAction;
    private TextView textViewToggle;
    private boolean isRegisterMode = false;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonAction = findViewById(R.id.buttonAction);
        textViewToggle = findViewById(R.id.textViewToggle);

        dbHelper = new DatabaseHelper(this);

        buttonAction.setOnClickListener(view -> {
            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (isRegisterMode) {
                String confirmPassword = editTextConfirmPassword.getText().toString().trim();
                if (validateRegistration(username, password, confirmPassword)) {
                    if (dbHelper.addUser(username, password)) {
                        Toast.makeText(MainActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                        toggleMode(); // Switch to login mode after successful registration
                    } else {
                        Toast.makeText(MainActivity.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                if (validateLogin(username, password)) {
                    if (dbHelper.authenticateUser(username, password)) {
                        Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        navigateToDashboard();
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        textViewToggle.setOnClickListener(view -> toggleMode());
    }


    private boolean validateLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateRegistration(String username, String password, String confirmPassword) {
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void toggleMode() {
        isRegisterMode = !isRegisterMode;
        editTextConfirmPassword.setVisibility(isRegisterMode ? View.VISIBLE : View.GONE);
        buttonAction.setText(isRegisterMode ? "Register" : "Login");
        textViewToggle.setText(isRegisterMode ? "Already have an account? Login" : "Don't have an account? Register");
    }

    private void navigateToDashboard() {
        Toast.makeText(this, "Navigating to Dashboard...", Toast.LENGTH_SHORT).show();
        // Add Intent to navigate to Dashboard Activity
    }

    public static class DatabaseHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "hospital.db";
        private static final int DATABASE_VERSION = 1;

        private static final String TABLE_USERS = "users";
        private static final String COL_ID = "id";
        private static final String COL_USERNAME = "username";
        private static final String COL_PASSWORD = "password";

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                    + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_USERNAME + " TEXT, "
                    + COL_PASSWORD + " TEXT)";
            db.execSQL(CREATE_USERS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
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
    }
}
