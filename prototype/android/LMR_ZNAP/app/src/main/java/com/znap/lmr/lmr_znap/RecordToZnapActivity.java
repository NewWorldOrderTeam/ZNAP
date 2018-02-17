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

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecordToZnapActivity extends AppCompatActivity implements OnItemSelectedListener {
    Spinner spinnerForZnap;
    Button bTreg;
    int user_id;
    int znap_id;
    private static Retrofit retrofit;
    private static Request request;
    List<RecordToZnapAPI> znapNames;
    List<String> znaps;
    HashMap<Integer,Integer> znapsMap;


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
        bTreg = (Button) findViewById(R.id.bTreg);
        bTreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(RecordToZnapActivity.this,
                        RegisteredToZnap.class);
                myIntent.putExtra(SystemMessages.USER_ID, user_id);
                myIntent.putExtra("znap_id", znap_id);
                startActivity(myIntent);
            }
        });
        znapNames = new ArrayList<>();
        znaps = new ArrayList<>();
        znapsMap = new HashMap<Integer, Integer>();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://qlogic.net.ua:8084/")
                .addConverterFactory(new GsonPConverterFactory(new Gson()))
                .build();
        request = retrofit.create(Request.class);
        RecordToZnapActivity.getApi().getRecordsToZnap().enqueue(new Callback<List<RecordToZnapAPI>>() {
            @Override
            public void onResponse(Call<List<RecordToZnapAPI>> call, Response<List<RecordToZnapAPI>> response) {
                System.out.println(response.body());
                znapNames.addAll(response.body());

                for (int i = 0; i < znapNames.size(); i++) {
                    System.out.println(znapNames.get(i).getServiceCenterName());
                    znaps.add(znapNames.get(i).getLocationName());
                    znapsMap.put(i,znapNames.get(i).getServiceCenterId());
                }
                final ArrayAdapter<String> a = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, znaps);
                a.setDropDownViewResource(R.layout.spinner_item);
                spinnerForZnap.setAdapter(a);
            }
            @Override
            public void onFailure(Call<List<RecordToZnapAPI>> call, Throwable t) {
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
        znap_id = znapsMap.get(spinnerForZnap.getSelectedItemPosition());
        System.out.println(znap_id);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "Select something", Toast.LENGTH_SHORT).show();

    }
    public static Request getApi() {
        return request;
    }

}