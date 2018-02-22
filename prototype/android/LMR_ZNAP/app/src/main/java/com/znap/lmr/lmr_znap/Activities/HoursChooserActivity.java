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
import com.znap.lmr.lmr_znap.ServerUtilities.GsonPConverterFactory;
import com.znap.lmr.lmr_znap.Pojo.HoursChooserAPI;
import com.znap.lmr.lmr_znap.R;
import com.znap.lmr.lmr_znap.ServerUtilities.Request;
import com.znap.lmr.lmr_znap.ClientUtilities.SystemMessages;
import com.znap.lmr.lmr_znap.ServerUtilities.ZnapUtility;

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

public class HoursChooserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerForHour;
    Button bTreg;
    int user_id;

    int znap_id;
    int service_id;
    int group_id;
    String date;
    String startTime;
    String organisationID = SystemMessages.ORGANISATION_ID;
    private static Retrofit retrofit;
    private static Request request;
    List<HoursChooserAPI> hours;
    List<String> hoursList;
    List<String> datesListFormated;
    HashMap<Integer, Integer> hoursMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_hour);
        getSupportActionBar().setTitle(SystemMessages.REG_TO_QUEUE_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getBundles();
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
                myIntent.putExtra("service_id", service_id);
                myIntent.putExtra("hours", startTime);
                myIntent.putExtra("date", date);
                startActivity(myIntent);
            }
        });
        hours = new ArrayList<>();
        hoursList = new ArrayList<>();
        datesListFormated = new ArrayList<>();
        hoursMap = new HashMap<Integer, Integer>();
        request = ZnapUtility.QLogicRequest();
        HoursChooserActivity.getApi().getHours(organisationID, znap_id, service_id, date).enqueue(new Callback<List<HoursChooserAPI>>() {
            @Override
            public void onResponse(Call<List<HoursChooserAPI>> call, Response<List<HoursChooserAPI>> response) {
                hours.addAll(response.body());
                for (int i = 0; i < hours.size(); i++) {
                    if (hours.get(i).getIsAllow() == 1) {
                        startTime = hours.get(i).getStartTime();
                        startTime = startTime.substring(2, startTime.length() - 1);
                        if (startTime.contains("H")) {
                            startTime = startTime.replace("H", ":");
                        } else {
                            if (startTime.length() == 1) {
                                startTime = "0" + startTime + ":00";
                            } else {
                                startTime = startTime + ":00";
                            }

                        }
                        hoursList.add(startTime);
                    }
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
        startTime = spinnerForHour.getSelectedItem().toString();


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
        service_id = bundle.getInt(SystemMessages.SERVICE_ID);
        date = bundle.getString("date");
    }

}




