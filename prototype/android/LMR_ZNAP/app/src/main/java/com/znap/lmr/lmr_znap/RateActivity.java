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
        OnItemSelectedListener {
    int quality;
    int znap_id;
    int pushed_user_id;
    Button btBad;
    Button btGood;
    Button btLeaveReview;
    String description;
    Spinner spinnerForZnaps;
    EditText etDescription;
    boolean badButtonClickedStatus = false;
    boolean goodButtonClickedStatus = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        getSupportActionBar().setTitle("Відгук");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinnerForZnaps = (Spinner) findViewById(R.id.znaps);
        spinnerForZnaps.setOnItemSelectedListener(this);
        etDescription = (EditText) findViewById(R.id.etDescription);
        btLeaveReview = (Button) findViewById(R.id.btLeaveReview);
        btGood = (Button) findViewById(R.id.btGood);
        btBad = (Button) findViewById(R.id.btBad);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            assert bundle != null;
            int userid = bundle.getInt(SystemMessages.userId);
            pushed_user_id = userid;
        }

        btLeaveReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description = etDescription.getText().toString();
                RateActivity.Request request = new RateActivity.Request();
                if (TextUtils.isEmpty(description)) {
                    etDescription.setError(NonSystemMessages.fieldMustBeNotEmpty);
                    return;
                }
                btGood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goodButtonClickedStatus = true;
                        badButtonClickedStatus = false;
                    }
                });
                btBad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goodButtonClickedStatus = false;
                        badButtonClickedStatus = true;
                    }
                });
                if (goodButtonClickedStatus) {
                    quality = Integer.parseInt(btGood.getText().toString());
                } else if (badButtonClickedStatus) {
                    quality = Integer.parseInt(btBad.getText().toString());
                }
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

    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        String sp1 = String.valueOf(spinnerForZnaps.getSelectedItem());
        znap_id = (int) spinnerForZnaps.getItemIdAtPosition(arg2);
        Toast.makeText(this, sp1, Toast.LENGTH_SHORT).show();
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
            Response response = services.Rate(pushed_user_id, znap_id, description, quality);
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
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

