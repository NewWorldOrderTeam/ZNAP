package com.znap.lmr.lmr_znap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Created by Zava on 01.12.2017.
 */

public class RegisteredToZnap extends AppCompatActivity {
    Spinner spinnerForTime, spinnerForHour;
    Button btToReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_to_znap);
        spinnerForTime = (Spinner) findViewById(R.id.spinnerForTime);
        spinnerForHour = (Spinner) findViewById(R.id.spinnerForHour);
        btToReg = (Button) findViewById(R.id.buttonTOReg);
        btToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(RegisteredToZnap.this,
                        RecordToZnapActivity.class);
                startActivity(myIntent);
            }
        });
    }
}

