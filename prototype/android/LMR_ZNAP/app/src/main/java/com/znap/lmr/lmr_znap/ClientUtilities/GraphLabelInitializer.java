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
                new DataPoint(1, queueStateList.get(0).getCountOfWaitingJobs()),

        });
        firstLabel.setColor(Color.BLUE);
        firstLabel.setSpacing(0);
        firstLabel.setAnimated(true);
        firstLabel.setDrawValuesOnTop(true);
        firstLabel.setValuesOnTopColor(Color.BLACK);
        graph.addSeries(firstLabel);
        BarGraphSeries<DataPoint> secondLabel = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(2, queueStateList.get(1).getCountOfWaitingJobs()),

        });
        secondLabel.setColor(Color.RED);
        secondLabel.setSpacing(0);
        secondLabel.setAnimated(true);
        secondLabel.setDrawValuesOnTop(true);
        secondLabel.setValuesOnTopColor(Color.BLACK);
        graph.addSeries(secondLabel);
        BarGraphSeries<DataPoint> thirdLabel = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(3, queueStateList.get(2).getCountOfWaitingJobs()),

        });
        thirdLabel.setColor(Color.GREEN);
        thirdLabel.setSpacing(0);
        thirdLabel.setAnimated(true);
        thirdLabel.setDrawValuesOnTop(true);
        thirdLabel.setValuesOnTopColor(Color.BLACK);
        graph.addSeries(thirdLabel);
        BarGraphSeries<DataPoint> fourthLabel = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(4, queueStateList.get(3).getCountOfWaitingJobs()),

        });
        fourthLabel.setColor(Color.YELLOW);
        fourthLabel.setSpacing(0);
        fourthLabel.setAnimated(true);
        fourthLabel.setDrawValuesOnTop(true);
        fourthLabel.setValuesOnTopColor(Color.BLACK);
        graph.addSeries(fourthLabel);
        BarGraphSeries<DataPoint> fifthLabel = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(5, queueStateList.get(4).getCountOfWaitingJobs()),

        });
        fifthLabel.setColor(Color.BLACK);
        fifthLabel.setSpacing(0);
        fifthLabel.setAnimated(true);
        fifthLabel.setDrawValuesOnTop(true);
        fifthLabel.setValuesOnTopColor(Color.BLACK);
        graph.addSeries(fifthLabel);
        BarGraphSeries<DataPoint> sixthLabel = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(6, queueStateList.get(5).getCountOfWaitingJobs()),

        });
        sixthLabel.setColor(Color.GRAY);
        sixthLabel.setSpacing(0);
        sixthLabel.setAnimated(true);
        sixthLabel.setDrawValuesOnTop(true);
        sixthLabel.setValuesOnTopColor(Color.BLACK);
        graph.addSeries(sixthLabel);
        BarGraphSeries<DataPoint> seventhLabel = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(7, queueStateList.get(6).getCountOfWaitingJobs()),

        });
        seventhLabel.setColor(Color.MAGENTA);
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
