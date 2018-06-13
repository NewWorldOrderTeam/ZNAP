package ua.lviv.iot.lmr_cnap.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import ua.lviv.iot.lmr_cnap.Pojo.TypeOfServiceAPI;
import ua.lviv.iot.lmr_cnap.R;
import ua.lviv.iot.lmr_cnap.ServerUtilities.Request;
import ua.lviv.iot.lmr_cnap.ClientUtilities.SystemMessages;
import ua.lviv.iot.lmr_cnap.ServerUtilities.ZnapUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisteredToZnap extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerForTypeOfService;
    Button bTreg;
    int user_id;
    int znap_id;
    int group_id;
    String znap_name, group_name;
    String organisationID = SystemMessages.ORGANISATION_ID;
    private static Request request;
    List<TypeOfServiceAPI> typeOfServices;
    List<String> services;
    HashMap<Integer, Integer> servicesMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_of_service);
        getSupportActionBar().setTitle(SystemMessages.REG_TO_QUEUE_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getBundles();
        spinnerForTypeOfService = (Spinner) findViewById(R.id.spinnerForTypeOfService);
        spinnerForTypeOfService.setOnItemSelectedListener(this);
        bTreg = (Button) findViewById(R.id.buttonTOReg);
        bTreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(RegisteredToZnap.this,
                        ServiceChooserActivity.class);
                myIntent.putExtra(SystemMessages.USER_ID, user_id);
                myIntent.putExtra("znap_id", znap_id);
                myIntent.putExtra("group_id", group_id);
                myIntent.putExtra("znap_name", znap_name);
                myIntent.putExtra("group_name", group_name);
                startActivity(myIntent);
            }
        });
        typeOfServices = new ArrayList<>();
        services = new ArrayList<>();
        servicesMap = new HashMap<Integer, Integer>();
        request = ZnapUtility.QLogicRequest();
        RegisteredToZnap.getApi().getTypeOfService(znap_id).enqueue(new Callback<List<TypeOfServiceAPI>>() {
            @Override
            public void onResponse(Call<List<TypeOfServiceAPI>> call, Response<List<TypeOfServiceAPI>> response) {
                typeOfServices.addAll(response.body());

                for (int i = 0; i < typeOfServices.size(); i++) {
                    services.add(typeOfServices.get(i).getDescription());
                    servicesMap.put(i, typeOfServices.get(i).getId());
                }
                final ArrayAdapter<String> a = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, services);
                a.setDropDownViewResource(R.layout.spinner_item);
                spinnerForTypeOfService.setAdapter(a);
            }

            @Override
            public void onFailure(Call<List<TypeOfServiceAPI>> call, Throwable t) {
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
        group_id = servicesMap.get(spinnerForTypeOfService.getSelectedItemPosition());
        group_name = spinnerForTypeOfService.getSelectedItem().toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "Select something", Toast.LENGTH_SHORT).show();

    }

    public static Request getApi() {
        return request;
    }

    public void getBundles(){
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        user_id = bundle.getInt(SystemMessages.USER_ID);
        znap_id = bundle.getInt(SystemMessages.ZNAP_ID);
        znap_name = bundle.getString("znap_name");
    }

}




