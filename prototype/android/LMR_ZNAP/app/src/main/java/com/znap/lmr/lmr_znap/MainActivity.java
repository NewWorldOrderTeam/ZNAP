package com.znap.lmr.lmr_znap;

/**
 * Created by Andy Blyzniuk on 01.11.2017.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bLeaveReview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent openRateActivity = new Intent(MainActivity.this, RateActivity.class);

                startActivity(openRateActivity);
            }
        });

        findViewById(R.id.bRecordToZnap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent openRecordToZnapActivity = new Intent(MainActivity.this, RecordToZnapActivity.class);

                startActivity(openRecordToZnapActivity);
            }
        });

        findViewById(R.id.bQueueState).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent openQueueStateActivity = new Intent(MainActivity.this, QueueStateActivity.class);

                startActivity(openQueueStateActivity);
            }
        });

    }

}

