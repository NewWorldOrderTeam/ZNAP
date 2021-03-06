package ua.lviv.iot.lmr_cnap.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import ua.lviv.iot.lmr_cnap.R;
import ua.lviv.iot.lmr_cnap.Pojo.RecordToZnapAPI;
import ua.lviv.iot.lmr_cnap.ServerUtilities.Request;
import ua.lviv.iot.lmr_cnap.ClientUtilities.SystemMessages;
import ua.lviv.iot.lmr_cnap.ServerUtilities.ZnapUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordToZnapActivity extends AppCompatActivity implements OnItemSelectedListener {
    Spinner spinnerForZnap;
    Button bTreg;
    int user_id;
    int znap_id;
    public static Request request;
    List<RecordToZnapAPI> znapNames;
    List<String> znaps;
    HashMap<Integer, Integer> znapsMap;
    String znap_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_to_znap);
        getSupportActionBar().setTitle(SystemMessages.REG_TO_QUEUE_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getBundles();
        spinnerForZnap = (Spinner) findViewById(R.id.spinnerForZnaps);
        spinnerForZnap.setOnItemSelectedListener(this);
        bTreg = (Button) findViewById(R.id.bTreg);
        bTreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(RecordToZnapActivity.this,
                        RegisteredToZnap.class);
                myIntent.putExtra(SystemMessages.USER_ID, user_id);
                myIntent.putExtra("znap_id", znap_id);
                myIntent.putExtra("znap_name", znap_name);
                startActivity(myIntent);
            }
        });
        znapNames = new ArrayList<>();
        znaps = new ArrayList<>();
        znapsMap = new HashMap<Integer, Integer>();
        request = ZnapUtility.QLogicRequest();
        RecordToZnapActivity.getApi().getRecordsToZnap().enqueue(new Callback<List<RecordToZnapAPI>>() {
            @Override
            public void onResponse(Call<List<RecordToZnapAPI>> call, Response<List<RecordToZnapAPI>> response) {
                znapNames.addAll(response.body());
                for (int i = 0; i < znapNames.size(); i++) {
                    znaps.add(znapNames.get(i).getName());
                    znapsMap.put(i, znapNames.get(i).getId());
                }
                final ArrayAdapter<String> a = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, znaps);
                a.setDropDownViewResource(R.layout.spinner_item);
                spinnerForZnap.setAdapter(a);
            }

            @Override
            public void onFailure(Call<List<RecordToZnapAPI>> call, Throwable t) {
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

    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        znap_id = znapsMap.get(spinnerForZnap.getSelectedItemPosition());
        znap_name = spinnerForZnap.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "Select something", Toast.LENGTH_SHORT).show();

    }

    public static Request getApi() {
        return request;
    }

    public void getBundles() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        user_id = bundle.getInt(SystemMessages.USER_ID);
    }

}