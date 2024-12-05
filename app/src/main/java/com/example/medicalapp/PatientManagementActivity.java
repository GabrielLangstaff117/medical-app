package com.example.medicalapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    private Button btnAddPatient;
    private ArrayList<String> patientList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_management);
