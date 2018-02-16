package com.znap.lmr.lmr_znap;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordToZnapActivity extends AppCompatActivity implements OnItemSelectedListener {
    Spinner spinnerForZnap,
            spinnerForTypeOfService,
            spinnerForService;
    Button bTreg;
    int znap_id, type_id, service_id, user_id;
    public static Request request;
    List<ZnapName> znapNames;
    List<String> znaps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_to_znap);
        getSupportActionBar().setTitle(SystemMessages.REG_TO_QUEUE_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        user_id = bundle.getInt(SystemMessages.USER_ID);
        System.out.println(user_id);
        spinnerForZnap = (Spinner) findViewById(R.id.spinnerForZnaps);
        spinnerForZnap.setOnItemSelectedListener(this);
        spinnerForTypeOfService = (Spinner) findViewById(R.id.spinnerForTypeOfServices);
        spinnerForTypeOfService.setOnItemSelectedListener(this);
        spinnerForService = (Spinner) findViewById(R.id.spinnerForService);
        spinnerForService.setOnItemSelectedListener(this);
        bTreg = (Button) findViewById(R.id.bTreg);
        bTreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(RecordToZnapActivity.this,
                        RegisteredToZnap.class);
                myIntent.putExtra(SystemMessages.USER_ID, user_id);
                myIntent.putExtra("znap_id", znap_id);
                myIntent.putExtra("service_id", service_id);
                startActivity(myIntent);
            }
        });
        znapNames = new ArrayList<>();
        znaps = new ArrayList<>();
        request = ZnapUtility.generateRetroRequest();
        RecordToZnapActivity.getApi().getZnapNames().enqueue(new Callback<List<ZnapName>>() {
            @Override
            public void onResponse(Call<List<ZnapName>> call, Response<List<ZnapName>> response) {
                znapNames.addAll(response.body());

                for (int i = 0; i < znapNames.size(); i++) {
                    System.out.println(znapNames.get(i).getName());
                    znaps.add(znapNames.get(i).getName());

                }
                final ArrayAdapter<String> a = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, znaps);
                a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerForZnap.setAdapter(a);

            }

            @Override
            public void onFailure(Call<List<ZnapName>> call, Throwable t) {
            }
        });
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
    public static Request getApi() {
        return request;
    }

}