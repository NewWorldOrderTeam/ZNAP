package com.znap.lmr.lmr_znap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Andy Blyzniuk on 14.11.2017.
 */

public class QueueStateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_state);
        getSupportActionBar().setTitle(SystemMessages.QUEUE_STATE_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
