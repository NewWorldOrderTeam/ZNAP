package com.znap.lmr.lmr_znap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;


public class OwnCabinetActivity extends AppCompatActivity {
    TextView idText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_cabinet);
        idText =  (TextView) findViewById(R.id.etTxt);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            assert bundle != null;
             int userid = bundle.getInt("userid");
            System.out.println("Own cabonet  activity:" + String.valueOf(userid));
            idText.setText(String.valueOf(userid));
        }
        else{
            finish();
        }

    }
}