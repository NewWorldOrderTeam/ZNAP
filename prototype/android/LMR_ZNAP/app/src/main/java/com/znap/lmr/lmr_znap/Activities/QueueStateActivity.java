package com.znap.lmr.lmr_znap.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.znap.lmr.lmr_znap.ClientUtilities.GraphLabelInitializer;
import com.znap.lmr.lmr_znap.ServerUtilities.GsonPConverterFactory;
import com.znap.lmr.lmr_znap.Pojo.QueueStateAPI;
import com.znap.lmr.lmr_znap.R;
import com.znap.lmr.lmr_znap.ServerUtilities.Request;
import com.znap.lmr.lmr_znap.ClientUtilities.SystemMessages;
import com.znap.lmr.lmr_znap.ServerUtilities.ZnapUtility;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by ne andy on 14.11.2017.
 */

public class QueueStateActivity extends AppCompatActivity {
    private static Retrofit retrofit;
    private static Request request;
    List<QueueStateAPI> queueStateList;
    List<String> queues;
    GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_state);
        graph = (GraphView) findViewById(R.id.graph);
        getSupportActionBar().setTitle(SystemMessages.QUEUE_STATE_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        queueStateList = new ArrayList<>();
        queues = new ArrayList<>();
        request = ZnapUtility.generateRetroRequest();
        QueueStateActivity.getApi().getQueue().enqueue(new Callback<List<QueueStateAPI>>() {
            @Override
            public void onResponse(Call<List<QueueStateAPI>> call, Response<List<QueueStateAPI>> response) {
                System.out.println(response.body());
                queueStateList.addAll(response.body());
                GraphLabelInitializer graphLabelInitializer = new GraphLabelInitializer();
                graphLabelInitializer.initializeGraphic(graph,queueStateList);
                graphLabelInitializer.setScalingForGraphic(graph);
                graphLabelInitializer.getNamesForLabels(graph);
            }

            @Override
            public void onFailure(Call<List<QueueStateAPI>> call, Throwable t) {
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
