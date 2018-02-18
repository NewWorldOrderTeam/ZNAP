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

import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Zava on 01.12.2017.
 */

public class HoursChooserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerForHour;
    Button bTreg;
    int user_id;

    int znap_id;
    int service_id;
    int group_id;
    String date;
    String startTime,endTime,time;
    String organisationID = "{28c94bad-f024-4289-a986-f9d79c9d8102}";
    private static Retrofit retrofit;
    private static Request request;
    List<HoursChooserAPI> hours;
    List<String> hoursList;
    List<String> datesListFormated;
    HashMap<Integer,Integer> hoursMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_hour);
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
        service_id = bundle.getInt(SystemMessages.SERVICE_ID);
        System.out.println("service_id" + service_id);
        date = bundle.getString("date");
        spinnerForHour = (Spinner) findViewById(R.id.spinnerForHour);
        spinnerForHour.setOnItemSelectedListener(this);
        bTreg = (Button) findViewById(R.id.buttonTOReg);

        bTreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(HoursChooserActivity.this,
                        FinishActivity.class);
                myIntent.putExtra(SystemMessages.USER_ID, user_id);
                myIntent.putExtra("znap_id", znap_id);
                myIntent.putExtra("service_id",service_id);
                myIntent.putExtra("hours", startTime);
                myIntent.putExtra("date", date);

                
                startActivity(myIntent);
            }
        });
        hours = new ArrayList<>();
        hoursList = new ArrayList<>();
        datesListFormated=new ArrayList<>();
        hoursMap = new HashMap<Integer, Integer>();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://qlogic.net.ua:8084/")
                .addConverterFactory(new GsonPConverterFactory(new Gson()))
                .build();
        request = retrofit.create(Request.class);
        HoursChooserActivity.getApi().getHours(organisationID, znap_id,service_id,date).enqueue(new Callback<List<HoursChooserAPI>>() {
            @Override
            public void onResponse(Call<List<HoursChooserAPI>> call, Response<List<HoursChooserAPI>> response) {
                System.out.println(znap_id);
                System.out.println(request.getHours(organisationID,znap_id,service_id,date).request().url().toString());
                System.out.println(response.body());
                hours.addAll(response.body());
                for (int i = 0; i < hours.size(); i++) {
                    startTime = hours.get(i).getStartTime();
                    startTime = startTime.substring(2,startTime.length()-1);
                    if(startTime.contains("H")){
                        startTime = startTime.replace("H",":");
                    } else {
                        if (startTime.length()==1){
                            startTime = "0" + startTime + ":00";
                        }
                        else {
                            startTime = startTime + ":00";
                        }

                    }
                    hoursList.add(startTime);
                }

                final ArrayAdapter<String> a = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, hoursList);
                a.setDropDownViewResource(R.layout.spinner_item);
                spinnerForHour.setAdapter(a);
            }
            @Override
            public void onFailure(Call<List<HoursChooserAPI>> call, Throwable t) {
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
        System.out.println("Choto tam"+service_id);



    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "Select something", Toast.LENGTH_SHORT).show();

    }
    public static Request getApi() {
        return request;
    }

}




