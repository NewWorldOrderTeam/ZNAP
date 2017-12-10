package com.znap.lmr.lmr_znap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class OwnCabinetActivity extends AppCompatActivity {
    TextView firstNameText;
    TextView lastNameText;
    TextView middleNameText;
    private Retrofit retrofit;
    private static Request request;
    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_cabinet);
        firstNameText = (TextView) findViewById(R.id.etFirstName);
        lastNameText = (TextView) findViewById(R.id.etLastName);
        middleNameText = (TextView) findViewById(R.id.etMiddleName);
        Bundle bundle = getIntent().getExtras();

        findViewById(R.id.myreviews).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent openMyReviewsActivity = new Intent(OwnCabinetActivity.this, MyReviewsActivity.class);

                startActivity(openMyReviewsActivity);
            }
        });

        users = new ArrayList<>();
        if (bundle != null) {
            assert bundle != null;
            int userid = bundle.getInt("userid");
            System.out.println("Own cabinet  activity:" + String.valueOf(userid));


            retrofit = new Retrofit.Builder()
                    .baseUrl("http://znap.pythonanywhere.com/") //Базовая часть адреса
                    .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                    .build();
            request = retrofit.create(Request.class); //Создаем объект, при помощи которого будем выполнять запросы

            OwnCabinetActivity.getApi().getInfo(userid).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = (User)response.body();
                    String firstName =  user.getFirstName();
                    String lastName = user.getLastName();
                    String middleName= user.getMiddleName();
                    firstNameText.setText(String.valueOf(firstName));
                    lastNameText.setText(String.valueOf(lastName));
                    middleNameText.setText(String.valueOf(middleName));
                    System.out.println(firstName);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    System.out.println("asdhnashd");
                }
            });
            //System.out.println(users);
        } else {
            finish();
        }
    }

    public static Request getApi() {
        return request;
    }


}
