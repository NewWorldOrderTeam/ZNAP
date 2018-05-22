package ua.lviv.iot.lmr_cnap.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import ua.lviv.iot.lmr_cnap.ClientUtilities.SystemMessages;
import ua.lviv.iot.lmr_cnap.R;

/**
 * Created by Andy Blyzniuk on 22.05.2018.
 */

public class SettingsActivity extends AppCompatActivity {

    private int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setTitle(SystemMessages.SETTINGS);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_settings);
        Bundle bundle = getIntent().getExtras();

        findViewById(R.id.bSurnameChange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openSurnameChangeActivity = new Intent(SettingsActivity.this, SurnameChangeActivity.class);
                openSurnameChangeActivity.putExtra(SystemMessages.USER_ID, user_id);
                startActivity(openSurnameChangeActivity);
            }
        });

        findViewById(R.id.bPhoneChange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openPhoneChangeActivity = new Intent(SettingsActivity.this, PhoneNumberChangingActivity.class);
                openPhoneChangeActivity.putExtra(SystemMessages.USER_ID, user_id);
                startActivity(openPhoneChangeActivity);
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
}
