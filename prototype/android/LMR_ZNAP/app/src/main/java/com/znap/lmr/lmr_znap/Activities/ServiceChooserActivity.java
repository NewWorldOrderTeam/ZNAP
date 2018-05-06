package com.znap.lmr.lmr_znap.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


import com.znap.lmr.lmr_znap.R;
import com.znap.lmr.lmr_znap.ServerUtilities.Request;
import com.znap.lmr.lmr_znap.Pojo.ServiceChooserAPI;
import com.znap.lmr.lmr_znap.ClientUtilities.SystemMessages;
import com.znap.lmr.lmr_znap.ServerUtilities.ZnapUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceChooserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerForService;
    Button bTreg;
    int user_id;
    int znap_id;
    int service_id;
    int group_id;

    private static Request request;
    List<ServiceChooserAPI> services;
    List<String> servicesList;
    HashMap<Integer, Integer> servicesMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_service);
        getSupportActionBar().setTitle(SystemMessages.REG_TO_QUEUE_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getBundles();
        spinnerForService = (Spinner) findViewById(R.id.spinnerForService);
        spinnerForService.setOnItemSelectedListener(this);
        bTreg = (Button) findViewById(R.id.buttonTOReg);
        bTreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ServiceChooserActivity.this,
                        DateChooserActivity.class);
                myIntent.putExtra(SystemMessages.USER_ID, user_id);
                myIntent.putExtra("znap_id", znap_id);
                myIntent.putExtra("service_id", service_id);
                myIntent.putExtra("group_id", group_id);
                startActivity(myIntent);
            }
        });
        services = new ArrayList<>();
        servicesList = new ArrayList<>();
        servicesMap = new HashMap<Integer, Integer>();
        request = ZnapUtility.QLogicRequest();
        ServiceChooserActivity.getApi().getServices(znap_id, group_id).enqueue(new Callback<List<ServiceChooserAPI>>() {
            @Override
            public void onResponse(Call<List<ServiceChooserAPI>> call, Response<List<ServiceChooserAPI>> response) {
                services.addAll(response.body());
                for (int i = 0; i < services.size(); i++) {
                    servicesList.add(services.get(i).getDescription());
                    servicesMap.put(i, services.get(i).getId());
                }
                final ArrayAdapter<String> a = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, servicesList);
                a.setDropDownViewResource(R.layout.spinner_item);
                spinnerForService.setAdapter(a);
            }

            @Override
            public void onFailure(Call<List<ServiceChooserAPI>> call, Throwable t) {
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
        service_id = servicesMap.get(spinnerForService.getSelectedItemPosition());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "Select something", Toast.LENGTH_SHORT).show();

    }

    public static Request getApi() {
        return request;
    }

    public void getBundles() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        user_id = bundle.getInt(SystemMessages.USER_ID);
        znap_id = bundle.getInt(SystemMessages.ZNAP_ID);
        group_id = bundle.getInt(SystemMessages.GROUP_ID);
    }

}




