package com.znap.lmr.lmr_znap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Zava on 17.02.2018.
 */

public class AboutUsActivity extends AppCompatActivity {
    TextView title,desc;
    ImageView image;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_about_us);
            title = (TextView) findViewById(R.id.textView6);
            desc = (TextView) findViewById(R.id.textView7);
            image = (ImageView) findViewById(R.id.image);
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
