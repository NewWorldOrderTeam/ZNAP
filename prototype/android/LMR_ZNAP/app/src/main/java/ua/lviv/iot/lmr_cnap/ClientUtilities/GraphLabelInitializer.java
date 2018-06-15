package ua.lviv.iot.lmr_cnap.ClientUtilities;

import android.graphics.Color;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import ua.lviv.iot.lmr_cnap.Pojo.QueueStateAPI;

import java.util.List;



public class GraphLabelInitializer {


    public void initializeGraphic(GraphView graph,List<QueueStateAPI> queueStateList) {

        for(int i=0; i<queueStateList.size(); i++){
            BarGraphSeries<DataPoint> label = new BarGraphSeries<>(new DataPoint[]{
                    new DataPoint(i+1, queueStateList.get(i).getCount()),

            });
            String[] rgb = queueStateList.get(i).getColor().split(",");
            label.setColor(Color.rgb(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2])));
            label.setSpacing(0);
            label.setAnimated(true);
            label.setDrawValuesOnTop(true);
            label.setValuesOnTopColor(Color.BLACK);
            graph.addSeries(label);
            label.setTitle(queueStateList.get(i).getName());

        }
    }

    public void setScalingForGraphic(GraphView graph){
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(12);
    }

    public void getNamesForLabels(GraphView graph){
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"", "", "", "","", "", ""});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
    }
}
