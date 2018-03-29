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

import com.google.gson.Gson;
import com.znap.lmr.lmr_znap.Pojo.DateChooserAPI;
import com.znap.lmr.lmr_znap.ServerUtilities.GsonPConverterFactory;
import com.znap.lmr.lmr_znap.R;
import com.znap.lmr.lmr_znap.ServerUtilities.Request;
import com.znap.lmr.lmr_znap.ClientUtilities.SystemMessages;
import com.znap.lmr.lmr_znap.ServerUtilities.ZnapUtility;

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

public class DateChooserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerForDate,getSpinnerForTime;
    Button bTreg;
    int user_id;
    int znap_id;
    int service_id;
    int group_id;
    String date,time;
    int date_id;
    String organisationID = SystemMessages.ORGANISATION_ID;
    private static Retrofit retrofit;
    private static Request request;
    List<DateChooserAPI> dates,times;
    List<String> datesList;
    List<String> timesList;
    String day,hour;
    HashMap<Integer,Integer> datesMap,timesMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_chooser);
        getSupportActionBar().setTitle(SystemMessages.REG_TO_QUEUE_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getBunldes();
        spinnerForDate = (Spinner) findViewById(R.id.spinnerForDate);
        getSpinnerForTime = (Spinner) findViewById(R.id.spinnerForTime);
        getSpinnerForTime.setOnItemSelectedListener(this);
        spinnerForDate.setOnItemSelectedListener(this);
        bTreg = (Button) findViewById(R.id.buttonTOReg);
        bTreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(DateChooserActivity.this,
                        FinishActivity.class);
                myIntent.putExtra(SystemMessages.USER_ID, user_id);
                myIntent.putExtra("znap_id", znap_id);
                myIntent.putExtra("service_id",service_id);
                myIntent.putExtra("day",day);
                myIntent.putExtra("hour",hour);
                startActivity(myIntent);
            }
        });
        dates = new ArrayList<>();
        times = new ArrayList<>();
        datesList = new ArrayList<>();
        timesList = new ArrayList<>();
        request = ZnapUtility.QLogicRequest();
        DateChooserActivity.getApi().getDates(znap_id,service_id).enqueue(new Callback<List<DateChooserAPI>>() {
            @Override
            public void onResponse(Call<List<DateChooserAPI>> call, Response<List<DateChooserAPI>> response) {
                dates.addAll(response.body());
                for (int i=0; i<dates.size(); i++){
                    datesList.add(dates.get(i).getDay());
                }
                final ArrayAdapter<String> a = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, datesList);
                a.setDropDownViewResource(R.layout.spinner_item);
                spinnerForDate.setAdapter(a);
            }
            @Override
            public void onFailure(Call<List<DateChooserAPI>> call, Throwable t) {
                System.out.println(t.getCause());
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
        date_id = (int) spinnerForDate.getSelectedItemId();
        for (int i=0; i<dates.get(date_id).getTimes().size(); i++){
            timesList.add(dates.get(date_id).getTimes().get(i).getStart());
        }
        final ArrayAdapter<String> timeAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, timesList);
        getSpinnerForTime.setAdapter(timeAdapter);
        day = spinnerForDate.getSelectedItem().toString();
        hour = getSpinnerForTime.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "Select something", Toast.LENGTH_SHORT).show();

    }
    public static Request getApi() {
        return request;
    }

    public void getBunldes(){
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        user_id = bundle.getInt(SystemMessages.USER_ID);
        znap_id = bundle.getInt(SystemMessages.ZNAP_ID);
        group_id = bundle.getInt(SystemMessages.GROUP_ID);
        service_id = bundle.getInt(SystemMessages.SERVICE_ID);
    }

}



