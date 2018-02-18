package com.znap.lmr.lmr_znap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Zava on 18.02.2018.
 */

public class FinishActivity extends AppCompatActivity {
    Button bFinish;
    int user_id,znap_id,service_id;
    String time,date;
    String organisationID = "{28c94bad-f024-4289-a986-f9d79c9d8102}";
    String firstName, lastName, phone, email;

    private static Retrofit retrofit;
    private static Request request;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_registration);
        getSupportActionBar().setTitle(SystemMessages.REG_TO_QUEUE_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        user_id = bundle.getInt(SystemMessages.USER_ID);

        znap_id = bundle.getInt(SystemMessages.ZNAP_ID);

        service_id = bundle.getInt(SystemMessages.SERVICE_ID);

        date = bundle.getString("date");

        time = bundle.getString("hours");


        final String dateAndTime = date + " " + time + ":00";
        System.out.println(dateAndTime);

        request = ZnapUtility.generateRetroRequest();
        FinishActivity.getApi().getInfo(user_id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = (User) response.body();
                firstName = user.getFirstName();
                lastName = user.getLastName();
                phone = user.getPhone();
                email = user.getEmail();

                try {
                    firstName = AESEncryption.decrypt_string(firstName);
                    lastName = AESEncryption.decrypt_string(lastName);
                    phone = AESEncryption.decrypt_string(phone);
                    email = AESEncryption.decrypt_string(email);



                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }

        });




        bFinish = (Button) findViewById(R.id.finish);
        bFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(FinishActivity.this,
                        MainActivity.class);
                myIntent.putExtra(SystemMessages.USER_ID, user_id);
                myIntent.putExtra("znap_id", znap_id);
                myIntent.putExtra("service_id", service_id);

                retrofit = new Retrofit.Builder()
                        .baseUrl("http://qlogic.net.ua:8084/")
                        .addConverterFactory(new GsonPConverterFactory(new Gson()))
                        .build();
                request = retrofit.create(Request.class);



                String name = lastName + firstName;
                FinishActivity.getApi().getResult(organisationID, znap_id,service_id, dateAndTime, phone, email, name, 1).enqueue(new Callback<SuccessRegistrationAPI>() {
                    @Override
                    public void onResponse(Call<SuccessRegistrationAPI> call, Response<SuccessRegistrationAPI> response) {
                        System.out.println(request.getResult(organisationID, znap_id,service_id, dateAndTime, phone, email, "Підор", 1).request().url().toString());
                        SuccessRegistrationAPI successRegistrationAPI = (SuccessRegistrationAPI) response.body();
                        String orderId = successRegistrationAPI.getCustOrderGuid();
                        System.out.println(orderId);



                    }
                    @Override
                    public void onFailure(Call<SuccessRegistrationAPI> call, Throwable t) {
                    }
                });
                startActivity(myIntent);
            }
        });



    }

    public static Request getApi() {
        return request;
    }
}
