package com.example.smarthome.ui.energycomponent;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smarthome.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class EnergyComponentFragment extends Fragment {

    private EnergyComponentViewModel sendViewModel;
    private LineChartView lineChartView;
    private View root;
    private DatabaseReference energy_consumption;
    private ArrayList<String> axisData;
    private ArrayList<Float> yAxisDatal1,yAxisDatal2, yAxisDatal3, yAxisDatao1, yAxisDatao2, yAxisDatao3, yAxisDatao4;
    private List yAxisValuesl1,yAxisValuesl2,yAxisValuesl3,yAxisValueso1,yAxisValueso2,yAxisValueso3,yAxisValueso4;
    private List axisValues;
    private Line linel1,linel2,linel3,lineo1,lineo2,lineo3,lineo4;
    private Calendar cld;
    private SimpleDateFormat curr_date;

    public EnergyComponentFragment() {}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(EnergyComponentViewModel.class);
        root = inflater.inflate(R.layout.fragment_componentgraph, container, false);
        sendViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {}
        });

        lineChartView = root.findViewById(R.id.chart);

        initializeObjects();
        setupListeners();
        return root;
    }

    public void initializeObjects() {

        lineChartView = root.findViewById(R.id.chart);
        yAxisValuesl1 = new ArrayList();
        yAxisValuesl2 = new ArrayList();
        yAxisValuesl3 = new ArrayList();
//        yAxisValueso1 = new ArrayList();
//        yAxisValueso2 = new ArrayList();
//        yAxisValueso3 = new ArrayList();
//        yAxisValueso4 = new ArrayList();
        axisValues = new ArrayList();
        axisData = new ArrayList<>();
        yAxisDatal1 = new ArrayList<>();
        yAxisDatal2 = new ArrayList<>();
        yAxisDatal3 = new ArrayList<>();
//        yAxisDatao1 = new ArrayList<>();
//        yAxisDatao2 = new ArrayList<>();
//        yAxisDatao3 = new ArrayList<>();
//        yAxisDatao4 = new ArrayList<>();
        cld = Calendar.getInstance();
        energy_consumption = FirebaseDatabase.getInstance().getReference("EnergyConsumption_Daily");
    }

    public void setupListeners() {

        SimpleDateFormat actual_date = new SimpleDateFormat("M-dd-yyyy");
        String current_date = actual_date.format(cld.getTime());
        final String[] split_current_date = current_date.split("-");

        cld.add(Calendar.DATE, -2);
        curr_date = new SimpleDateFormat("MMMM");
        int days_month = cld.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i=1; i<= Integer.valueOf(split_current_date[1]); i++){
            axisData.add(""+i);
            yAxisDatal1.add(0.0f);
            yAxisDatal2.add(0.0f);
            yAxisDatal3.add(0.0f);
//            yAxisDatao1.add(0.0f);
//            yAxisDatao2.add(0.0f);
//            yAxisDatao3.add(0.0f);
//            yAxisDatao4.add(0.0f);

        }

        energy_consumption.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dsp:dataSnapshot.getChildren()) {
                    String[] fb_split_date = dsp.getKey().split("-");
                    if(fb_split_date[0].equals(split_current_date[0])) {
                        for (int i=0;i<axisData.size();i++) {
                            if (i+1 == Integer.valueOf(fb_split_date[1])) {
                                yAxisDatal1.set(i,Float.valueOf(dsp.child("light1").getValue().toString()));
                                yAxisDatal2.set(i,Float.valueOf(dsp.child("light2").getValue().toString()));
                                yAxisDatal3.set(i,Float.valueOf(dsp.child("light3").getValue().toString()));
//                                yAxisDatao1.set(i,Float.valueOf(dsp.child("outlet1").getValue().toString()));
//                                yAxisDatao2.set(i,Float.valueOf(dsp.child("outlet2").getValue().toString()));
//                                yAxisDatao3.set(i,Float.valueOf(dsp.child("outlet3").getValue().toString()));
//                                yAxisDatao4.set(i,Float.valueOf(dsp.child("outlet4").getValue().toString()));
                            }
                        }
                    }
                }
                setupChart();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setupChart() {
//        docu: https://www.codingdemos.com/draw-android-line-chart/
//        MPAndroidChart: https://github.com/PhilJay/MPAndroidChart/wiki/Getting-Started

        linel1 = new Line(yAxisValuesl1).setColor(Color.RED);
        linel2 = new Line(yAxisValuesl2).setColor(Color.parseColor("#7F00FF"));
        linel3 = new Line(yAxisValuesl3).setColor(Color.GREEN);
//        lineo1 = new Line(yAxisValueso1).setColor(Color.BLUE);
//        lineo2 = new Line(yAxisValueso2).setColor(Color.parseColor("#FF00B8D4"));
//        lineo3 = new Line(yAxisValueso3).setColor(Color.BLACK);
//        lineo4 = new Line(yAxisValueso4).setColor(Color.GRAY);

        for (int i = 0; i < axisData.size(); i++) {
            axisValues.add(i, new AxisValue(i).setLabel(axisData.get(i)));
        }

        for (int i = 0; i < yAxisDatal1.size(); i++) {
            yAxisValuesl1.add(new PointValue(i, Float.valueOf(yAxisDatal1.get(i).toString())));
        }

        for (int i = 0; i < yAxisDatal2.size(); i++) {
            yAxisValuesl2.add(new PointValue(i, Float.valueOf(yAxisDatal2.get(i).toString())));
        }

        for (int i = 0; i < yAxisDatal3.size(); i++) {
            yAxisValuesl3.add(new PointValue(i, Float.valueOf(yAxisDatal3.get(i).toString())));
        }

//        for (int i = 0; i < yAxisDatao1.size(); i++) {
//            yAxisValueso1.add(new PointValue(i, Float.valueOf(yAxisDatao1.get(i).toString())));
//        }
//
//        for (int i = 0; i < yAxisDatao2.size(); i++) {
//            yAxisValueso2.add(new PointValue(i, Float.valueOf(yAxisDatao2.get(i).toString())));
//        }
//
//        for (int i = 0; i < yAxisDatao3.size(); i++) {
//            yAxisValueso3.add(new PointValue(i, Float.valueOf(yAxisDatao3.get(i).toString())));
//        }
//
//        for (int i = 0; i < yAxisDatao4.size(); i++) {
//            yAxisValueso4.add(new PointValue(i, Float.valueOf(yAxisDatao4.get(i).toString())));
//        }

        List<Line> lines = new ArrayList<Line>();
        lines.add(linel1);
        lines.add(linel2);
        lines.add(linel3);
//        lines.add(lineo1);
//        lines.add(lineo2);
//        lines.add(lineo3);
//        lines.add(lineo4);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axis = new Axis();
        axis.setValues(axisValues);
        axis.setName(curr_date.format(cld.getTime()));
        axis.setTextSize(16);
        axis.setTextColor(Color.parseColor("#03A9F4"));
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
        yAxis.setName("Electricity Bill");
        yAxis.setTextColor(Color.parseColor("#03A9F4"));
        yAxis.setTextSize(16);
        data.setAxisYLeft(yAxis);

        lineChartView.setLineChartData(data);
        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.top = 20;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);
    }
}