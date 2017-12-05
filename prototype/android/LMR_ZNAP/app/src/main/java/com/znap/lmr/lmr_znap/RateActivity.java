package com.znap.lmr.lmr_znap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import com.znap.lmr.lmr_znap.Request;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;
import retrofit2.Response;

/**
 * Created by Andy Blyzniuk on 13.11.2017.
 */

public class RateActivity extends AppCompatActivity implements
        OnItemSelectedListener  {
    int quality;
    int znap_id;
    EditText etDescription;
    EditText etUser_id;
    Button btLeaveReview;
    String description;
    Button btGood;
    Button btBad;
    int user_id;
    Spinner spinnerForZnaps;
    Spinner spinnerForServices;
    String idOfUser;
    boolean clicked=false;
    boolean clicked1 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        getSupportActionBar().setTitle("Відгук");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinnerForZnaps = (Spinner)findViewById(R.id.znaps);
        spinnerForServices = (Spinner)findViewById(R.id.services);
        spinnerForZnaps.setOnItemSelectedListener(this);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etUser_id = (EditText) findViewById(R.id.etUser_id);
        btLeaveReview = (Button) findViewById(R.id.btLeaveReview);
        btGood = (Button) findViewById(R.id.btGood);
        btBad = (Button) findViewById(R.id.btBad);

        btLeaveReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description = etDescription.getText().toString();
                idOfUser = etUser_id.getText().toString();
                RateActivity.Request request = new RateActivity.Request();
                if(TextUtils.isEmpty(description)) {
                    etDescription.setError("Поле має бути заповнене");
                    return;
                }
                if(TextUtils.isEmpty(idOfUser)) {
                    etUser_id.setError("Поле має бути заповнене");
                    return;
                } else {
                    user_id = Integer.parseInt(etUser_id.getText().toString());
                }

                btGood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        clicked=true;
                        clicked1=false;
                    }
                });
                btBad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clicked=false;
                        clicked1=true;
                    }
                });
                if(clicked)
                {
                    quality = Integer.parseInt(btGood.getText().toString());}
                else if (clicked1)
                {
                    quality = Integer.parseInt(btBad.getText().toString());}
                request.execute();
                Pattern pattern = Pattern.compile("message=.*,");
                try {
                    Matcher matcher = pattern.matcher(request.get());
                    while (matcher.find()) {
                        int start = matcher.start() + 8;
                        int end = matcher.end() - 1;
                        String match = request.get().substring(start, end);
                        Toast.makeText(getApplicationContext(), match, Toast.LENGTH_LONG).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {

        String sp1= String.valueOf(spinnerForZnaps.getSelectedItem());
        znap_id = (int) spinnerForZnaps.getItemIdAtPosition(arg2);
        Toast.makeText(this, sp1, Toast.LENGTH_SHORT).show();
        if(sp1.contentEquals("1")) {
            List<String> list = new ArrayList<String>();
            list.add("Одна послуга");
            list.add("Друга");
            list.add("Третя");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);

            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            spinnerForServices.setAdapter(dataAdapter);
        }
        if(sp1.contentEquals("Цнап2")) {
            List<String> list = new ArrayList<String>();
            list.add("А тут вже інша");
            list.add("А тут вже інша");
            list.add("А тут вже інша");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            spinnerForServices.setAdapter(dataAdapter2);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    class Request extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            Services services = new Services();
            Response response = services.Rate(user_id,znap_id,description,quality);
            System.out.println(response);
            System.out.println(znap_id);
            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            return;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

