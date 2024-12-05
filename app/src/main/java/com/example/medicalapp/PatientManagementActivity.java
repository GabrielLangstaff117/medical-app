package com.example.medicalapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PatientManagementActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView lvPatients;
    private EditText etPatientName, etPatientAge, etPatientGender;
    private Button btnAddPatient, btnUpdatePatient, btnClear;
    private ArrayList<String> patientList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_management);

        lvPatients = findViewById(R.id.lvPatients);
        etPatientName = findViewById(R.id.etName);
        etPatientAge = findViewById(R.id.etAge);
        etPatientGender = findViewById(R.id.etGender);
        btnAddPatient = findViewById(R.id.btnAdd);
        btnUpdatePatient = findViewById(R.id.btnUpdate);
        btnClear = findViewById(R.id.btnClear);

        databaseHelper = new DatabaseHelper(this);
        patientList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, patientList);
        lvPatients.setAdapter(adapter);

        loadPatients();

        btnAddPatient.setOnClickListener(view -> {
            String name = etPatientName.getText().toString().trim();
            String age = etPatientAge.getText().toString().trim();
            String gender = etPatientGender.getText().toString().trim();

            if (!name.isEmpty() && !age.isEmpty() && !gender.isEmpty()) {
                addPatient(name, age, gender);
                loadPatients();
            } else {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdatePatient.setOnClickListener(view -> {
            String name = etPatientName.getText().toString().trim();
            String age = etPatientAge.getText().toString().trim();
            String gender = etPatientGender.getText().toString().trim();

            Toast.makeText(this, "Update functionality is not implemented yet", Toast.LENGTH_SHORT).show();
        });

        btnClear.setOnClickListener(view -> {

            etPatientName.setText("");
            etPatientAge.setText("");
            etPatientGender.setText("");
        });

        lvPatients.setOnItemClickListener((parent, view, position, id) -> {
            String selectedPatient = patientList.get(position);
            showPatientDetails(selectedPatient);
        });

        lvPatients.setOnItemLongClickListener((parent, view, position, id) -> {
            String selectedPatient = patientList.get(position);
            deletePatient(selectedPatient);
            loadPatients();
            return true;
        });
    }

    private void loadPatients() {
        patientList.clear();
        Cursor cursor = databaseHelper.getPatients();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String patientName = cursor.getString(cursor.getColumnIndex("name"));
                patientList.add(patientName);
            } while (cursor.moveToNext());
            cursor.close();
        }
        adapter.notifyDataSetChanged();
    }

    private void addPatient(String name, String age, String gender) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("age", age);
        values.put("gender", gender);
        long result = databaseHelper.addPatient(values);
        if (result != -1) {
            Toast.makeText(this, "Patient added!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to add patient!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showPatientDetails(String patientName) {
        Toast.makeText(this, "Selected: " + patientName, Toast.LENGTH_SHORT).show();
    }

    private void deletePatient(String patientName) {
        int result = databaseHelper.deletePatient(patientName);
        if (result > 0) {
            Toast.makeText(this, "Patient deleted!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to delete patient!", Toast.LENGTH_SHORT).show();
        }
    }
}
