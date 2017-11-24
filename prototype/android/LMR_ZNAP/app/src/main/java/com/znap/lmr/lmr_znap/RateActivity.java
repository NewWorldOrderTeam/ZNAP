package com.znap.lmr.lmr_znap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Response;

/**
 * Created by Andy Blyzniuk on 13.11.2017.
 */

public class RateActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton znapSqRunok;
    RadioButton znapStrLevutskogo;
    RadioButton znapStrShevchenka;
    RadioButton znapStrHvulovogo;
    RadioButton znapPrKalunu;
    RadioButton znapStrChuprunku;
    RadioButton znapStrVitovskogo;
    ImageButton btGoodReview;
    ImageButton btBadReview;
    EditText etLeaveReview;
    Button btLeaveReview;
    String leaveReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        getSupportActionBar().setTitle("Відгук");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        znapStrLevutskogo = (RadioButton) findViewById(R.id.znapStrLevutskogo);
        znapSqRunok = (RadioButton) findViewById(R.id.znapSqRunok);
        znapPrKalunu = (RadioButton) findViewById(R.id.znapPrKalunu);
        znapStrChuprunku = (RadioButton) findViewById(R.id.znapStrChuprunku);
        znapStrHvulovogo = (RadioButton) findViewById(R.id.znapStrHvulovogo);
        znapStrVitovskogo = (RadioButton) findViewById(R.id.znapStrVitovskogo);
        znapStrShevchenka = (RadioButton) findViewById(R.id.znapStrShevchenka);
        btGoodReview = (ImageButton) findViewById(R.id.btGoodReview);
        btBadReview = (ImageButton) findViewById(R.id.btBadReview);
        etLeaveReview = (EditText) findViewById(R.id.etLeaveReview);
        btLeaveReview = (Button) findViewById(R.id.btLeaveReview);

        btLeaveReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaveReview = etLeaveReview.getText().toString();
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
            Response response = services.Rate(leaveReview);
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

