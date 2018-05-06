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

import com.znap.lmr.lmr_znap.Pojo.DateChooserAPI;
import com.znap.lmr.lmr_znap.R;
import com.znap.lmr.lmr_znap.ServerUtilities.Request;
import com.znap.lmr.lmr_znap.ClientUtilities.SystemMessages;
import com.znap.lmr.lmr_znap.ServerUtilities.ZnapUtility;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Zava on 01.12.2017.
 */

public class DateChooserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerForDate, getSpinnerForTime;
    Button bTreg;
    int user_id;
    int znap_id;
    int service_id;
    int group_id;
    int date_id;
    private static Request request;
    List<DateChooserAPI> dates, times;
    List<String> datesList;
    String day, hour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_chooser);
        getSupportActionBar().setTitle(SystemMessages.REG_TO_QUEUE_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getBunldes();
        spinnerForDate = (Spinner) findViewById(R.id.spinnerForDate);
        getSpinnerForTime = (Spinner) findViewById(R.id.spinnerForTime);
        spinnerForDate.setOnItemSelectedListener(this);
        bTreg = (Button) findViewById(R.id.buttonTOReg);
        bTreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(DateChooserActivity.this,
                        FinishActivity.class);
                myIntent.putExtra(SystemMessages.USER_ID, user_id);
                myIntent.putExtra("znap_id", znap_id);
                myIntent.putExtra("service_id", service_id);
                myIntent.putExtra("day", day);
                myIntent.putExtra("hour", hour);
                startActivity(myIntent);
            }
        });
        dates = new ArrayList<>();
        times = new ArrayList<>();
        datesList = new ArrayList<>();
        request = ZnapUtility.QLogicRequest();
        DateChooserActivity.getApi().getDates(znap_id, service_id).enqueue(new Callback<List<DateChooserAPI>>() {
            @Override
            public void onResponse(Call<List<DateChooserAPI>> call, Response<List<DateChooserAPI>> response) {
                dates.addAll(response.body());
                for (int i = 0; i < dates.size(); i++) {
                    datesList.add(dates.get(i).getDay());
                }
                final ArrayAdapter<String> arr = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, datesList);
                arr.setDropDownViewResource(R.layout.spinner_item);
                spinnerForDate.setAdapter(arr);
            }

            @Override
            public void onFailure(Call<List<DateChooserAPI>> call, Throwable t) {

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
        List<String> timesList = new ArrayList<>();
        for (int i = 0; i < dates.get(date_id).getTimes().size(); i++) {
            timesList.add(dates.get(date_id).getTimes().get(i).getStart());
        }
        final ArrayAdapter<String> timeAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, timesList);

        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeAdapter.notifyDataSetChanged();
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

    public void getBunldes() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        user_id = bundle.getInt(SystemMessages.USER_ID);
        znap_id = bundle.getInt(SystemMessages.ZNAP_ID);
        group_id = bundle.getInt(SystemMessages.GROUP_ID);
        service_id = bundle.getInt(SystemMessages.SERVICE_ID);
    }

}




