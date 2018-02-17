package com.znap.lmr.lmr_znap;

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

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Zava on 01.12.2017.
 */

public class ServiceChooserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerForService;
    Button bTreg;
    int user_id;
    int znap_id;
    int service_id;
    int group_id;
    String organisationID = "{28c94bad-f024-4289-a986-f9d79c9d8102}";
    private static Retrofit retrofit;
    private static Request request;
    List<ServiceChooserAPI> services;
    List<String> servicesList;
    HashMap<Integer,Integer> servicesMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_service);
        getSupportActionBar().setTitle(SystemMessages.REG_TO_QUEUE_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        user_id = bundle.getInt(SystemMessages.USER_ID);
        System.out.println(user_id);
        znap_id = bundle.getInt(SystemMessages.ZNAP_ID);
        System.out.println("qwe"+ znap_id);
        group_id = bundle.getInt(SystemMessages.GROUP_ID);
        System.out.println("group_id" + group_id);
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
                myIntent.putExtra("service_id",service_id);
                startActivity(myIntent);
            }
        });
        services = new ArrayList<>();
        servicesList = new ArrayList<>();
        servicesMap = new HashMap<Integer, Integer>();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://qlogic.net.ua:8084/")
                .addConverterFactory(new GsonPConverterFactory(new Gson()))
                .build();
        request = retrofit.create(Request.class);
        ServiceChooserActivity.getApi().getServices(organisationID, znap_id,group_id).enqueue(new Callback<List<ServiceChooserAPI>>() {
            @Override
            public void onResponse(Call<List<ServiceChooserAPI>> call, Response<List<ServiceChooserAPI>> response) {
                System.out.println(znap_id);
                System.out.println(request.getServices(organisationID,znap_id,group_id).request().url().toString());
                System.out.println(response.body());
                services.addAll(response.body());

                for (int i = 0; i < services.size(); i++) {
                    System.out.println(services.get(i).getDescription());
                    servicesList.add(services.get(i).getDescription());
                    servicesMap.put(i, services.get(i).getServiceId());
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




