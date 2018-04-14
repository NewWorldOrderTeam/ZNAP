package com.znap.lmr.lmr_znap.ClientUtilities;

import android.graphics.Color;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.znap.lmr.lmr_znap.Pojo.QueueStateAPI;

import java.util.List;

/**
 * Created by Zava on 28.02.2018.
 */

public class GraphLabelInitializer {


    public void initializeGraphic(GraphView graph,List<QueueStateAPI> queueStateList) {
        BarGraphSeries<DataPoint> firstLabel = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(1, queueStateList.get(0).getCount()),

        });
        firstLabel.setColor(Color.rgb(37,201,3));
        firstLabel.setSpacing(0);
        firstLabel.setAnimated(true);
        firstLabel.setDrawValuesOnTop(true);
        firstLabel.setValuesOnTopColor(Color.BLACK);
        graph.addSeries(firstLabel);
        BarGraphSeries<DataPoint> secondLabel = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(2, queueStateList.get(1).getCount()),

        });
        secondLabel.setColor(Color.rgb(17,123,225));
        secondLabel.setSpacing(0);
        secondLabel.setAnimated(true);
        secondLabel.setDrawValuesOnTop(true);
        secondLabel.setValuesOnTopColor(Color.BLACK);
        graph.addSeries(secondLabel);
        BarGraphSeries<DataPoint> thirdLabel = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(3, queueStateList.get(2).getCount()),

        });
        thirdLabel.setColor(Color.rgb(106,110,112));
        thirdLabel.setSpacing(0);
        thirdLabel.setAnimated(true);
        thirdLabel.setDrawValuesOnTop(true);
        thirdLabel.setValuesOnTopColor(Color.BLACK);
        graph.addSeries(thirdLabel);
        BarGraphSeries<DataPoint> fourthLabel = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(4, queueStateList.get(3).getCount()),

        });
        fourthLabel.setColor(Color.rgb(219,87,0));
        fourthLabel.setSpacing(0);
        fourthLabel.setAnimated(true);
        fourthLabel.setDrawValuesOnTop(true);
        fourthLabel.setValuesOnTopColor(Color.BLACK);
        graph.addSeries(fourthLabel);
        BarGraphSeries<DataPoint> fifthLabel = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(5, queueStateList.get(4).getCount()),

        });
        fifthLabel.setColor(Color.rgb(204,51,51));
        fifthLabel.setSpacing(0);
        fifthLabel.setAnimated(true);
        fifthLabel.setDrawValuesOnTop(true);
        fifthLabel.setValuesOnTopColor(Color.BLACK);
        graph.addSeries(fifthLabel);
        BarGraphSeries<DataPoint> sixthLabel = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(6, queueStateList.get(5).getCount()),

        });
        sixthLabel.setColor(Color.rgb(98,4,187));
        sixthLabel.setSpacing(0);
        sixthLabel.setAnimated(true);
        sixthLabel.setDrawValuesOnTop(true);
        sixthLabel.setValuesOnTopColor(Color.BLACK);
        graph.addSeries(sixthLabel);
        BarGraphSeries<DataPoint> seventhLabel = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(7, queueStateList.get(6).getCount()),

        });
        seventhLabel.setColor(Color.rgb(231,117,22));
        seventhLabel.setSpacing(0);
        seventhLabel.setAnimated(true);
        seventhLabel.setDrawValuesOnTop(true);
        seventhLabel.setValuesOnTopColor(Color.BLACK);
        graph.addSeries(seventhLabel);
        firstLabel.setTitle("Ч.Калини");
        secondLabel.setTitle("Левицького");
        thirdLabel.setTitle("пл.Ринок");
        fourthLabel.setTitle("Хвильового");
        fifthLabel.setTitle("Виговського");
        sixthLabel.setTitle("Чупринки");
        seventhLabel.setTitle("РЯСНЕ");
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
