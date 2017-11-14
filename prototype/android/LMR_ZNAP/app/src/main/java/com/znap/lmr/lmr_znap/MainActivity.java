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
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bLeaveReview).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent openRateActivity = new Intent(MainActivity.this, RateActivity.class);

                startActivity(openRateActivity);
            }
        });

        initToolbar();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ЦНАП");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });

        toolbar.inflateMenu(R.menu.menu);
    }
}
