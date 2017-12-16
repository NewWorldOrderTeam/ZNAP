package com.znap.lmr.lmr_znap;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;

public class RecordToZnapActivity extends AppCompatActivity implements OnItemSelectedListener {
    Spinner spinnerForZnap,
            spinnerForTypeOfService,
            spinnerForService;
    TextView textForZnap,
            textForTypeOfService,
            textForService;
    Button bTreg;
    int znap_id,type_id,service_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_to_znap);
        getSupportActionBar().setTitle(SystemMessages.REG_TO_QUEUE_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinnerForZnap = (Spinner) findViewById(R.id.spinnerForZnaps);
        spinnerForZnap.setOnItemSelectedListener(this);
        spinnerForTypeOfService = (Spinner) findViewById(R.id.spinnerForTypeOfServices);
        spinnerForTypeOfService.setOnItemSelectedListener(this);
        spinnerForService = (Spinner) findViewById(R.id.spinnerForService);
        spinnerForService.setOnItemSelectedListener(this);
        bTreg = (Button) findViewById(R.id.bTreg);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        znap_id = (int) spinnerForZnap.getItemIdAtPosition(arg2);
        System.out.println(znap_id);


        type_id = (int) spinnerForTypeOfService.getItemIdAtPosition(arg2);
        System.out.println(type_id);

        service_id = (int) spinnerForService.getItemIdAtPosition(arg2);
        System.out.println(service_id);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "Select something", Toast.LENGTH_SHORT).show();

    }

}