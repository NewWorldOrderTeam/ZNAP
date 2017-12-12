package com.znap.lmr.lmr_znap;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class RecordToZnapActivity extends Activity implements
        OnItemSelectedListener {
    Spinner spinnerForZnaps,
            spinnerForTypeOfService,
            spinnerForServices;
    Button bTreg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_to_znap);
        spinnerForZnaps = (Spinner) findViewById(R.id.spinnerForZnaps);
        spinnerForTypeOfService = (Spinner) findViewById(R.id.spinnerForServices);
        spinnerForZnaps.setOnItemSelectedListener(this);
        spinnerForServices = (Spinner) findViewById(R.id.spinnerForTypes);
        spinnerForServices.setOnItemSelectedListener(this);
        bTreg = (Button) findViewById(R.id.bTreg);
        bTreg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(RecordToZnapActivity.this,
                        RegisteredToZnap.class);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {
        String sp1 = String.valueOf(spinnerForZnaps.getSelectedItem());
        String get = String.valueOf(spinnerForServices.getSelectedItem());
        Toast.makeText(this, sp1, Toast.LENGTH_SHORT).show();
        if (sp1.contentEquals("Цнап1")) {
            if (get.contentEquals("Отримати довідку")) {
                List<String> list = new ArrayList<String>();
                list.add("Отримати при цнап1");
                list.add("Отримати2 при цнап1");
                list.add("Отримати3 при цнап1");
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dataAdapter.notifyDataSetChanged();
                spinnerForTypeOfService.setAdapter(dataAdapter);
            }
            if (get.contentEquals("Подати довідку")) {
                List<String> list = new ArrayList<String>();
                list.add("Подати при цнап1");
                list.add("Подати2 при цнап1");
                list.add("Подати3 при цнап1");
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dataAdapter.notifyDataSetChanged();
                spinnerForTypeOfService.setAdapter(dataAdapter);
            }
        }
        if (sp1.contentEquals("Цнап2")) {
            if (get.contentEquals("Отримати довідку")) {
                List<String> list = new ArrayList<String>();
                list.add("Отримати при цнап2");
                list.add("Отримати2 при цнап2");
                list.add("Отримати3 при цнап2");
                ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, list);
                dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dataAdapter2.notifyDataSetChanged();
                spinnerForTypeOfService.setAdapter(dataAdapter2);
            }
            if (get.contentEquals("Подати довідку")) {
                List<String> list = new ArrayList<String>();
                list.add("Подати при цнап2");
                list.add("Подати2 при цнап2");
                list.add("Подати3 при цнап2");
                ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, list);
                dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dataAdapter2.notifyDataSetChanged();
                spinnerForTypeOfService.setAdapter(dataAdapter2);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }
}