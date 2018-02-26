package com.znap.lmr.lmr_znap.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.znap.lmr.lmr_znap.ServerUtilities.GsonPConverterFactory;
import com.znap.lmr.lmr_znap.Pojo.QueueStateAPI;
import com.znap.lmr.lmr_znap.R;
import com.znap.lmr.lmr_znap.ServerUtilities.Request;
import com.znap.lmr.lmr_znap.ClientUtilities.SystemMessages;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Andy Blyzniuk on 14.11.2017.
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

                    BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                            new DataPoint(0, queueStateList.get(0).getCustomerCount()),
                            new DataPoint(1, 1),
                            new DataPoint(2, 2),
                            new DataPoint(3, 3),
                            new DataPoint(4, 4),
                            new DataPoint(5, 5),
                            new DataPoint(6, 6),

                    });
                graph.getViewport().setYAxisBoundsManual(true);
                graph.getViewport().setMinY(0);
                graph.getViewport().setMaxY(50);

                graph.getViewport().setXAxisBoundsManual(true);
                graph.getViewport().setMinX(0);
                graph.getViewport().setMaxX(6);
                graph.getViewport().setScrollable(true); // enables horizontal scrolling
                graph.getViewport().setScrollableY(true);

                StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
                staticLabelsFormatter.setHorizontalLabels(new String[] {"ЦНАП1", "ЦНАП2", "ЦНАП3","ЦНАП4", "ЦНАП5", "ЦНАП6","CNAP5"});
                graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
                graph.addSeries(series);

// styling
                    series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                        @Override
                        public int get(DataPoint data) {
                            return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
                        }
                    });

                    series.setSpacing(50);

// draw values on top
                    series.setDrawValuesOnTop(true);
                    series.setValuesOnTopColor(Color.RED);

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
