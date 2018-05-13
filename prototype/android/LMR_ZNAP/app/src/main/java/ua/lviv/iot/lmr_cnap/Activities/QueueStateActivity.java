package ua.lviv.iot.lmr_cnap.Activities;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


import com.jjoe64.graphview.GraphView;
import ua.lviv.iot.lmr_cnap.ClientUtilities.GraphLabelInitializer;
import ua.lviv.iot.lmr_cnap.Pojo.QueueStateAPI;
import ua.lviv.iot.lmr_cnap.R;
import ua.lviv.iot.lmr_cnap.ServerUtilities.Request;
import ua.lviv.iot.lmr_cnap.ClientUtilities.SystemMessages;
import ua.lviv.iot.lmr_cnap.ServerUtilities.ZnapUtility;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QueueStateActivity extends AppCompatActivity {
    private static Request request;
    List<QueueStateAPI> queueStateList;
    List<String> queues;
    GraphView graph;
    Context context = this;

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
                if (response.body().isEmpty()) {
                    new AlertDialog.Builder(context)
                            .setMessage("На жаль, стан черги недоступний")
                            .setCancelable(false)
                            .setNegativeButton("Ок", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                }
                            })
                            .show();
                } else {
                    GraphLabelInitializer graphLabelInitializer = new GraphLabelInitializer();
                    graphLabelInitializer.initializeGraphic(graph, queueStateList);
                    graphLabelInitializer.setScalingForGraphic(graph);
                    graphLabelInitializer.getNamesForLabels(graph);
                }
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
