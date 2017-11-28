package com.znap.lmr.lmr_znap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.sql.SQLOutput;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Response;

/**
 * Created by Andy Blyzniuk on 13.11.2017.
 */

public class RateActivity extends AppCompatActivity {
    Integer[] quality = {1,2,3,4,5};
    EditText etDescription;
    EditText etUser_id;
    EditText etQuality;
    Button btLeaveReview;
    String description;
    int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        getSupportActionBar().setTitle("Відгук");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayAdapter<Integer> arrayAdapterForZnap = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_dropdown_item_1line, quality);

        MaterialBetterSpinner dropdownForZnap = (MaterialBetterSpinner)
                findViewById(R.id.quality);
        dropdownForZnap.setAdapter(arrayAdapterForZnap);

        etDescription = (EditText) findViewById(R.id.etDescription);
        etUser_id = (EditText) findViewById(R.id.etUser_id);
        btLeaveReview = (Button) findViewById(R.id.btLeaveReview);


        btLeaveReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description = etDescription.getText().toString();
                user_id = Integer.parseInt(etUser_id.getText().toString());
                RateActivity.Request request = new RateActivity.Request();
                request.execute();
                Pattern pattern = Pattern.compile("message=.*,");
                try {
                    Matcher matcher = pattern.matcher(request.get());
                    while (matcher.find()){
                        int start = matcher.start()+8;
                        int end = matcher.end()-1;
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
    class Request extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            Services services = new Services();
            Response response = services.Rate(description,user_id,quality);
            System.out.println(response.toString());
            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            return ;
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

