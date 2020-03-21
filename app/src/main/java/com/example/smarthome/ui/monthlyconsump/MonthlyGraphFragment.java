package com.example.smarthome.ui.monthlyconsump;

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

public class MonthlyGraphFragment extends Fragment {

    private MonthlyGraphViewModel sendViewModel;
    private LineChartView lineChartView;
    private View root;
    private DatabaseReference totalkwh, node_parent;
    private String[] axisData;
    private ArrayList<Float> yAxisData,yAxisDatadummy;
    private List yAxisValues,yAxisValuesdummy;
    private List axisValues;
    private Line line,linedummy;
    private Calendar cld;
    private SimpleDateFormat curr_date;
    private Double monthly_bill;

    public MonthlyGraphFragment() {}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(MonthlyGraphViewModel.class);
        root = inflater.inflate(R.layout.fragment_consumptiongraph, container, false);
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
        yAxisValues = new ArrayList();
        yAxisValuesdummy = new ArrayList();
        axisValues = new ArrayList();
        axisData = new String[] {"January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December"};
        yAxisData = new ArrayList<>();
        yAxisDatadummy = new ArrayList<>();
        cld = Calendar.getInstance();
        totalkwh = FirebaseDatabase.getInstance().getReference("DailyBill");
        node_parent = FirebaseDatabase.getInstance().getReference("MonthlyBill");
        monthly_bill = 0.0;
    }

    public void setupListeners() {

        SimpleDateFormat actual_date = new SimpleDateFormat("M-dd-yyyy");
        String current_date = actual_date.format(cld.getTime());
        final String[] split_current_date = current_date.split("-");

        cld.add(Calendar.DATE, -2);
        curr_date = new SimpleDateFormat("MMMM");
        for (int i=1; i<= Integer.valueOf(split_current_date[0]); i++){
            yAxisData.add(0.0f);
        }

        totalkwh.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot dsp:dataSnapshot.getChildren()) {
                    String[] fb_split_date = dsp.getKey().split("-");
                    if(fb_split_date[0].equals(split_current_date[0])) {
                        for (int i=0;i<Integer.valueOf(split_current_date[1]);i++) {
                            if (i + 1 == Integer.valueOf(fb_split_date[1])) {
                                if(Float.valueOf(dsp.getValue().toString()) != 0.0f){
                                    monthly_bill = monthly_bill + Double.valueOf(dsp.getValue().toString());
                                }
                            }
                        }
                    }
                }
                node_parent.child(split_current_date[0]).setValue(monthly_bill);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        node_parent.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    for (int i = 0; i < Integer.valueOf(split_current_date[0]); i++) {
                        if (i + 1 == Integer.valueOf(dsp.getKey())) {
                            if (Float.valueOf(dsp.getValue().toString()) != 0.0f) {
                                yAxisData.set(i, Float.valueOf(dsp.getValue().toString()));
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

        line = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0"));
        linedummy = new Line(yAxisValuesdummy).setColor(Color.parseColor("#77ee27"));

        for (int i = 0; i < axisData.length; i++) {
            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
        }

        for (int i = 0; i < yAxisData.size(); i++) {
            yAxisValues.add(new PointValue(i, Float.valueOf(yAxisData.get(i).toString())));
        }

        for (int i = 0; i < yAxisDatadummy.size(); i++) {
            yAxisValuesdummy.add(new PointValue(i, Float.valueOf(yAxisDatadummy.get(i).toString())));
        }

        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axis = new Axis();
        axis.setValues(axisValues);
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
        viewport.top = Float.valueOf(monthly_bill.toString()) + 1;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);
    }
}