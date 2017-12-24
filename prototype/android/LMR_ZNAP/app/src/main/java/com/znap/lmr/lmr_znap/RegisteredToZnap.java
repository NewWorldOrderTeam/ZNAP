package com.znap.lmr.lmr_znap;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Response;

/**
 * Created by Zava on 01.12.2017.
 */

public class RegisteredToZnap extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Context context = this;
    Spinner spinnerForTime, spinnerForHour;
    Button btToReg;
    int user_id,znap_id,service;
    String date,time;
    int pushed_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_to_znap);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        user_id = bundle.getInt(SystemMessages.USER_ID);
        znap_id = bundle.getInt("znap_id");
        service = bundle.getInt("service_id");
        if (bundle != null) {
            assert bundle != null;
            int userid = bundle.getInt(SystemMessages.USER_ID);
            pushed_user_id = userid;
        }
        spinnerForTime = (Spinner) findViewById(R.id.spinnerForTime);
        spinnerForHour = (Spinner) findViewById(R.id.spinnerForHour);
        spinnerForHour.setOnItemSelectedListener(this);
        spinnerForTime.setOnItemSelectedListener(this);
        btToReg = (Button) findViewById(R.id.buttonTOReg);
        btToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setMessage(NonSystemMessages.recordToZnapSuccessFull + " " + date + " на " + time)
                        .setCancelable(false)
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                requestPatternValidation();
                                Intent mainAct = new Intent(RegisteredToZnap.this, MainActivity.class);
                                mainAct.putExtra(SystemMessages.USER_ID, user_id);
                                startActivity(mainAct);
                            }
                        })
                        .show();

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
        date = (String) spinnerForTime.getSelectedItem().toString();
        System.out.println(date);


        time = (String) spinnerForHour.getSelectedItem().toString();
        System.out.println(time);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "Select something", Toast.LENGTH_SHORT).show();
    }

    public void requestPatternValidation(){
        RegisteredToZnap.Request request = new Request();
        request.execute();
        Pattern pattern = Pattern.compile("message=.*,");
        try {
            Matcher matcher = pattern.matcher(request.get());
            while (matcher.find()) {
                int start = matcher.start() + 8;
                int end = matcher.end() - 1;
                String match = request.get().substring(start, end);
                if (match.equals(SystemMessages.BAD_REQUEST)) {
                    match = NonSystemMessages.FIELD_IS_NOT_ENTERED_CORRECTLY;
                    Toast.makeText(getApplicationContext(), match, Toast.LENGTH_LONG).show();
                }
                if (match.equals(SystemMessages.OK)) {
                    Intent mainIntent = new Intent(RegisteredToZnap.this, MainActivity.class);
                    RegisteredToZnap.this.startActivity(mainIntent);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    class Request extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            Services services = new Services();
            Response response = services.Queue(pushed_user_id,znap_id,date,time,service);
            System.out.println(response.toString());
            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            return;
        }
    }


}



