package com.znap.lmr.lmr_znap;

/**
 * Created by Andy Blyzniuk on 01.11.2017.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;


public class MainActivity extends AppCompatActivity {
    public int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        user_id = bundle.getInt(SystemMessages.USER_ID);

        findViewById(R.id.bLeaveReview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openRateActivity = new Intent(MainActivity.this, RateActivity.class);
                openRateActivity.putExtra(SystemMessages.USER_ID, user_id);
                startActivity(openRateActivity);
            }
        });

        findViewById(R.id.bRecordToZnap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent openRecordToZnapActivity = new Intent(MainActivity.this, RecordToZnapActivity.class);
                openRecordToZnapActivity.putExtra(SystemMessages.USER_ID, user_id);
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

        findViewById(R.id.bOwnCabinet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(MainActivity.this, ProfileActivity.class);
                mainIntent.putExtra(SystemMessages.USER_ID, user_id);
                startActivity(mainIntent);
            }
        });

        findViewById(R.id.bOnline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callOnline = new Intent(Intent.ACTION_DIAL);
                callOnline.setData(Uri.parse("tel:0971735178"));
                startActivity(callOnline);
            }
        });

        findViewById(R.id.ibtFacebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.facebook.com/cnaplviv")));

            }
        });

        findViewById(R.id.bAboutZNAP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent aboutUsActivity = new Intent(MainActivity.this, AboutUsActivity.class);
                aboutUsActivity.putExtra(SystemMessages.USER_ID, user_id);
                startActivity(aboutUsActivity);
            }
        });


    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

}
