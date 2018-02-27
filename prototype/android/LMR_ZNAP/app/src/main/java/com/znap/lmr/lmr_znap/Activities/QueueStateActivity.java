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
                    BarGraphSeries<DataPoint> firstLabel = new BarGraphSeries<>(new DataPoint[] {
                            new DataPoint(1, queueStateList.get(0).getCountOfWaitingJobs()),

                    });
                firstLabel.setColor(Color.BLUE);
                firstLabel.setSpacing(0);
                firstLabel.setAnimated(true);
                firstLabel.setDrawValuesOnTop(true);
                firstLabel.setValuesOnTopColor(Color.BLACK);
                graph.addSeries(firstLabel);
                BarGraphSeries<DataPoint> secondLabel = new BarGraphSeries<>(new DataPoint[] {
                        new DataPoint(2, queueStateList.get(1).getCountOfWaitingJobs()),

                });
                secondLabel.setColor(Color.RED);
                secondLabel.setSpacing(0);
                secondLabel.setAnimated(true);
                secondLabel.setDrawValuesOnTop(true);
                secondLabel.setValuesOnTopColor(Color.BLACK);
                graph.addSeries(secondLabel);
                BarGraphSeries<DataPoint> thirdLabel = new BarGraphSeries<>(new DataPoint[] {
                        new DataPoint(3, queueStateList.get(2).getCountOfWaitingJobs()),

                });
                thirdLabel.setColor(Color.GREEN);
                thirdLabel.setSpacing(0);
                thirdLabel.setAnimated(true);
                thirdLabel.setDrawValuesOnTop(true);
                thirdLabel.setValuesOnTopColor(Color.BLACK);
                graph.addSeries(thirdLabel);
                BarGraphSeries<DataPoint> fourthLabel = new BarGraphSeries<>(new DataPoint[] {
                        new DataPoint(4, queueStateList.get(3).getCountOfWaitingJobs()),

                });
                fourthLabel.setColor(Color.YELLOW);
                fourthLabel.setSpacing(0);
                fourthLabel.setAnimated(true);
                fourthLabel.setDrawValuesOnTop(true);
                fourthLabel.setValuesOnTopColor(Color.BLACK);
                graph.addSeries(fourthLabel);
                BarGraphSeries<DataPoint> fifthLabel = new BarGraphSeries<>(new DataPoint[] {
                        new DataPoint(5, queueStateList.get(4).getCountOfWaitingJobs()),

                });
                fifthLabel.setColor(Color.BLACK);
                fifthLabel.setSpacing(0);
                fifthLabel.setAnimated(true);
                fifthLabel.setDrawValuesOnTop(true);
                fifthLabel.setValuesOnTopColor(Color.BLACK);
                graph.addSeries(fifthLabel);
                BarGraphSeries<DataPoint> sixthLabel = new BarGraphSeries<>(new DataPoint[] {
                        new DataPoint(6, queueStateList.get(5).getCountOfWaitingJobs()),

                });
                sixthLabel.setColor(Color.GRAY);
                sixthLabel.setSpacing(0);
                sixthLabel.setAnimated(true);
                sixthLabel.setDrawValuesOnTop(true);
                sixthLabel.setValuesOnTopColor(Color.BLACK);
                graph.addSeries(sixthLabel);
                BarGraphSeries<DataPoint> seventhLabel = new BarGraphSeries<>(new DataPoint[] {
                        new DataPoint(7, queueStateList.get(6).getCountOfWaitingJobs()),

                });
                seventhLabel.setColor(Color.MAGENTA);
                seventhLabel.setSpacing(0);
                seventhLabel.setAnimated(true);
                seventhLabel.setDrawValuesOnTop(true);
                seventhLabel.setValuesOnTopColor(Color.BLACK);
                graph.addSeries(seventhLabel);
                graph.getViewport().setXAxisBoundsManual(true);
                graph.getViewport().setMinX(0);
                graph.getViewport().setMaxX(12);
                StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
                firstLabel.setTitle("Пл.Ринок");
                secondLabel.setTitle("РЯСНЕ");
                thirdLabel.setTitle("Ч.Калини");
                fourthLabel.setTitle("Хвильового");
                fifthLabel.setTitle("Левицького");
                sixthLabel.setTitle("Виговського");
                seventhLabel.setTitle("Чупринки");
                graph.getLegendRenderer().setVisible(true);
                graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                staticLabelsFormatter.setHorizontalLabels(new String[] {"", "", "", "","", "", ""});
                graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

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
