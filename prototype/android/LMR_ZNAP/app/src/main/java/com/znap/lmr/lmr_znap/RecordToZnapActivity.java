package com.znap.lmr.lmr_znap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;


public class RecordToZnapActivity extends AppCompatActivity {
    String[] znapS = {"ЦНАП пл.Ринок 1", "ЦНАП на вул. К. Левицького, 67", "ЦНАП на вул. М. Хвильового, 14а", "ЦНАП на пр. Ч. Калини, 72а",
    "ЦНАП на вул. Т. Шевченка, 374 (Рясне)","ЦНАП на вул. Ген. Чупринки, 85","ЦНАП на вул. І. Вітовського, 32"};
    String[] services = {"Отримати документ","Подати документ"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_to_znap);
        getSupportActionBar().setTitle("Запис у ЦНАП");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<String> arrayAdapterForZnap = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, znapS);
        MaterialBetterSpinner dropdownForZnap = (MaterialBetterSpinner)
                findViewById(R.id.znapName);
        dropdownForZnap.setAdapter(arrayAdapterForZnap);

        ArrayAdapter<String> arrayAdapterForService = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, services);
        MaterialBetterSpinner dropdownForService = (MaterialBetterSpinner)
                findViewById(R.id.typeOfService);
        dropdownForService.setAdapter(arrayAdapterForService);
    }

}
