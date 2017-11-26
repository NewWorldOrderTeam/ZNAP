package com.znap.lmr.lmr_znap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy Blyzniuk on 14.11.2017.
 */

public class RecordToZnapActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_to_znap);
        getSupportActionBar().setTitle("Запис у ЦНАП");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner spinnerZnap = (Spinner) findViewById(R.id.spinnerZnap);
        Spinner spinnerServices = (Spinner) findViewById(R.id.spinnerServices);

        List<String> categories = new ArrayList<String>();
        categories.add("ЦНАП пл.Ринок 1");
        categories.add("ЦНАП на вул. К. Левицького, 67");
        categories.add("ЦНАП на вул. М. Хвильового, 14а");
        categories.add("ЦНАП на пр. Ч. Калини, 72а");
        categories.add("ЦНАП на вул. Т. Шевченка, 374 (Рясне)");
        categories.add("ЦНАП на вул. Ген. Чупринки, 85");
        categories.add("ЦНАП на вул. І. Вітовського, 32");
        spinnerZnap.setOnItemSelectedListener(this);

        List<String> categoriesServices = new ArrayList<String>();
        categoriesServices.add("Подати документи");
        categoriesServices.add("Отримати результат");
        categoriesServices.add("Записатися на прийом");
        spinnerServices.setOnItemSelectedListener(this);


        ArrayAdapter<String> dataAdapter = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item, categories);

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item, categoriesServices);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerZnap.setAdapter(dataAdapter);

        spinnerServices.setAdapter(dataAdapter1);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item = parent.getItemAtPosition(position).toString();

        

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}
