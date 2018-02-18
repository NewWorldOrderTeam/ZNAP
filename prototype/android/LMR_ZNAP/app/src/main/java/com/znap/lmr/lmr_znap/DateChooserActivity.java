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
    Spinner spinnerForDate;
    Button bTreg;
    int user_id;
    int znap_id;
    int service_id;
    int group_id;
    String date;
    String organisationID = "{28c94bad-f024-4289-a986-f9d79c9d8102}";
    private static Retrofit retrofit;
    private static Request request;
    List<DateChooserAPI> dates;
    List<StringBuilder> datesList;
    List<String> datesListFormated;
    HashMap<Integer,Integer> datesMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_chooser);
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
        
        spinnerForDate = (Spinner) findViewById(R.id.spinnerForDate);
        spinnerForDate.setOnItemSelectedListener(this);
        bTreg = (Button) findViewById(R.id.buttonTOReg);
        bTreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(DateChooserActivity.this,
                        HoursChooserActivity.class);
                myIntent.putExtra(SystemMessages.USER_ID, user_id);
                myIntent.putExtra("znap_id", znap_id);
                myIntent.putExtra("service_id",service_id);
                myIntent.putExtra("date",date);
                startActivity(myIntent);
            }
        });
        dates = new ArrayList<>();
        datesList = new ArrayList<>();
        datesListFormated=new ArrayList<>();
        datesMap = new HashMap<Integer, Integer>();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://qlogic.net.ua:8084/")
                .addConverterFactory(new GsonPConverterFactory(new Gson()))
                .build();
        request = retrofit.create(Request.class);
        DateChooserActivity.getApi().getDates(organisationID, znap_id,service_id).enqueue(new Callback<List<DateChooserAPI>>() {
            @Override
            public void onResponse(Call<List<DateChooserAPI>> call, Response<List<DateChooserAPI>> response) {
                System.out.println(znap_id);
                System.out.println(request.getDates(organisationID,znap_id,service_id).request().url().toString());
                System.out.println(response.body());
                dates.addAll(response.body());

                for (int i = 0; i < dates.size(); i++) {

                    datesMap.put(i, dates.get(i).getCountJobs());
                    StringBuilder stringBuilder = new StringBuilder(dates.get(i).getDatePart().toString());
                    stringBuilder.delete(0,6);
                    stringBuilder.delete(10,stringBuilder.length());
                    datesList.add(stringBuilder);
                    long unix = Integer.valueOf(datesList.get(i).toString());
                    Date date = new Date(unix*1000);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sdf.setTimeZone(TimeZone.getTimeZone("GMT+2"));
                    String formattedDate = sdf.format(date);
                    datesListFormated.add(formattedDate);

                }

                final ArrayAdapter<String> a = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, datesListFormated);
                a.setDropDownViewResource(R.layout.spinner_item);
                spinnerForDate.setAdapter(a);
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
        date = String.valueOf(spinnerForDate.getSelectedItem().toString());
        System.out.println("DATE: " +date);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "Select something", Toast.LENGTH_SHORT).show();

    }
    public static Request getApi() {
        return request;
    }

}




