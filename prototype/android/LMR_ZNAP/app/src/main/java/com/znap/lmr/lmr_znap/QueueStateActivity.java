package com.znap.lmr.lmr_znap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Andy Blyzniuk on 14.11.2017.
 */

public class QueueStateActivity extends AppCompatActivity {
    private static Retrofit retrofit;
    private static Request request;
    List<QueueStateAPI> queueStateList;
    List<Integer> queues;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_state);
        getSupportActionBar().setTitle(SystemMessages.QUEUE_STATE_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list = (ListView) findViewById(R.id.ReviewsList);
        retrofit = new Retrofit.Builder()
                    .baseUrl("http://qlogic.net.ua:8081/")
                .addConverterFactory(new GsonPConverterFactory(new Gson()))
                .build();
        request = retrofit.create(Request.class);
        queueStateList = new ArrayList<>();
        queues = new ArrayList<>();
        QueueStateActivity.getApi().getQueue().enqueue(new Callback<List<QueueStateAPI>>() {

            @Override
            public void onResponse(Call<List<QueueStateAPI>> call, Response<List<QueueStateAPI>> response) {
                System.out.println(response.body());
                queueStateList.addAll(response.body());
                for (int i = 0; i < queueStateList.size(); i++) {
                    System.out.println(queueStateList.get(i).getDescription());
                    queues.add(queueStateList.get(i).getCustomerCount());

                }
                list.setAdapter(new ArrayAdapter<>(QueueStateActivity.this, android.R.layout.simple_list_item_1, queues));
            }

            @Override
            public void onFailure(Call<List<QueueStateAPI>> call, Throwable t) {
                System.out.println("qwe");
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
    public static Request getApi() {
        return request;
    }




}

