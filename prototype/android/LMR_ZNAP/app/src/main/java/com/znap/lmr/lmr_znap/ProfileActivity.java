package com.znap.lmr.lmr_znap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ProfileActivity extends AppCompatActivity {
    TextView firstNameText;
    TextView lastNameText;
    TextView middleNameText;
    private Retrofit retrofit;
    private static Request request;
    private int user_id;
    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_cabinet);
        getSupportActionBar().setTitle(SystemMessages.PROFILE_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firstNameText = (TextView) findViewById(R.id.etFirstName);
        lastNameText = (TextView) findViewById(R.id.etLastName);
        middleNameText = (TextView) findViewById(R.id.etMiddleName);
        Bundle bundle = getIntent().getExtras();

        users = new ArrayList<>();
        if (bundle != null) {
            assert bundle != null;
            user_id = bundle.getInt(SystemMessages.USER_ID);
            findViewById(R.id.myreviews).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent openMyReviewsActivity = new Intent(ProfileActivity.this, MyReviewsActivity.class);
                    openMyReviewsActivity.putExtra(SystemMessages.USER_ID, user_id);
                    startActivity(openMyReviewsActivity);
                }
            });
            request = ZnapUtility.generateRetroRequest();
            ProfileActivity.getApi().getInfo(user_id).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = (User) response.body();
                    String firstName = user.getFirstName();
                    String lastName = user.getLastName();
                    String middleName = user.getMiddleName();
                    firstNameText.setText(String.valueOf(firstName));
                    lastNameText.setText(String.valueOf(lastName));
                    middleNameText.setText(String.valueOf(middleName));
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                }
            });
        } else {
            finish();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public static Request getApi() {
        return request;
    }
}
