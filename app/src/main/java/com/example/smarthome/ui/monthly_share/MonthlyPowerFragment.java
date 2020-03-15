package com.example.smarthome.ui.monthly_share;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smarthome.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MonthlyPowerFragment extends Fragment {

    private MonthlyFragmentViewModel shareViewModel;
    private View root;
    private DatabaseReference energy_consumption;
    private DatabaseReference time_consumed;
    private DatabaseReference veco_rating;
    private TextView txt_date, bill, rating;
    private TextView l1_current, l1_power, l1_duration, l1_energy;
    private TextView l2_current, l2_power, l2_duration, l2_energy;
    private TextView l3_current, l3_power, l3_duration, l3_energy;
    private TextView o1_current, o1_power, o1_duration, o1_energy;
    private TextView o2_current, o2_power, o2_duration, o2_energy;
    private TextView o3_current, o3_power, o3_duration, o3_energy;
    private TextView o4_current, o4_power, o4_duration, o4_energy;
    private String current_date;
    private ArrayList<Double> energy, duration, power, current;

    public MonthlyPowerFragment() {}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(MonthlyFragmentViewModel.class);
        root = inflater.inflate(R.layout.fragment_powerconsumption, container, false);
        shareViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {    }
        });

        initializeObjects();
        getRating();
        getEnergy();
        getTime();
        return root;
    }

    public void initializeObjects() {
        Calendar cld = Calendar.getInstance();
        cld.add(Calendar.DATE, -1);
        SimpleDateFormat curr_date = new SimpleDateFormat("MMMM");
        current_date = curr_date.format(cld.getTime());

        energy_consumption = FirebaseDatabase.getInstance().getReference("EnergyConsumption_Daily");
        veco_rating = FirebaseDatabase.getInstance().getReference();
        time_consumed = FirebaseDatabase.getInstance().getReference("Output_TimeConsumed_Daily");

        txt_date = root.findViewById(R.id.txt_date);
        bill = root.findViewById(R.id.bill);
        rating = root.findViewById(R.id.kwph);
        txt_date.setText(current_date);
        energy = new ArrayList<>();
        duration = new ArrayList<>();
        power = new ArrayList<>();
        current = new ArrayList<>();

        l1_current = root.findViewById(R.id.l1_current);
        l1_power = root.findViewById(R.id.l1_power);
        l1_duration = root.findViewById(R.id.l1_duration);
        l1_energy = root.findViewById(R.id.l1_energy);

        l2_current = root.findViewById(R.id.l2_current);
        l2_power = root.findViewById(R.id.l2_power);
        l2_duration = root.findViewById(R.id.l2_duration);
        l2_energy = root.findViewById(R.id.l2_energy);

        l3_current = root.findViewById(R.id.l3_current);
        l3_power = root.findViewById(R.id.l3_power);
        l3_duration = root.findViewById(R.id.l3_duration);
        l3_energy = root.findViewById(R.id.l3_energy);

        o1_current = root.findViewById(R.id.o1_current);
        o1_power = root.findViewById(R.id.o1_power);
        o1_duration = root.findViewById(R.id.o1_duration);
        o1_energy = root.findViewById(R.id.o1_energy);

        o2_current = root.findViewById(R.id.o2_current);
        o2_power = root.findViewById(R.id.o2_power);
        o2_duration = root.findViewById(R.id.o2_duration);
        o2_energy = root.findViewById(R.id.o2_energy);

        o3_current = root.findViewById(R.id.o3_current);
        o3_power = root.findViewById(R.id.o3_power);
        o3_duration = root.findViewById(R.id.o3_duration);
        o3_energy = root.findViewById(R.id.o3_energy);

        o4_current = root.findViewById(R.id.o4_current);
        o4_power = root.findViewById(R.id.o4_power);
        o4_duration = root.findViewById(R.id.o4_duration);
        o4_energy = root.findViewById(R.id.o4_energy);
    }

    public void getRating() {
        veco_rating.child("PricePerKWhr").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String resid = dataSnapshot.getValue().toString();
                rating.setText(resid == null ? "" : resid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getEnergy() {
        energy_consumption.child(current_date).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    Double total_kwh = 0.0;
                    for (DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        total_kwh = total_kwh + Double.valueOf(snapshot.getValue().toString());
                        energy.add(Double.valueOf(snapshot.getValue().toString()));
                    }

                    veco_rating.child("TotalEnergy").child(current_date).setValue(total_kwh);

                    bill.setText(String.format("%5f",total_kwh));
                    displayEnergy();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getTime() {
        time_consumed.child(current_date).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                        duration.add(Double.valueOf(snapshot.getValue().toString()));
                    }
                    displayDuration();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void displayEnergy() {
        l1_energy.setText(energy.get(0).toString());
        l2_energy.setText(energy.get(1).toString());
        l3_energy.setText(energy.get(2).toString());
        o1_energy.setText(energy.get(3).toString());
        o2_energy.setText(energy.get(4).toString());
        o3_energy.setText(energy.get(5).toString());
        o4_energy.setText(energy.get(6).toString());
        powerPrio(0);
    }

    public void displayDuration() {
        l1_duration.setText(duration.get(0).toString());
        l2_duration.setText(duration.get(1).toString());
        l3_duration.setText(duration.get(2).toString());
        o1_duration.setText(duration.get(3).toString());
        o2_duration.setText(duration.get(4).toString());
        o3_duration.setText(duration.get(5).toString());
        o4_duration.setText(duration.get(6).toString());
        powerPrio(1);
    }

    private Integer prio = 0;

    public void powerPrio(Integer selector) {

        if (selector == 0) {
            prio++;
        }
        if (selector == 1) {
            prio++;
        }

        if(prio == 2) {
            computePower();
        }
    }

    public void computePower() {

        for(int i=0; i<6; i++) {
            power.add(energy.get(i)/duration.get(i));
        }
        l1_power.setText(String.format("%.3f",energy.get(0)/duration.get(0)));
        l2_power.setText(String.format("%.3f",energy.get(1)/duration.get(1)));
        l3_power.setText(String.format("%.3f",energy.get(2)/duration.get(2)));
        o1_power.setText(String.format("%.3f",energy.get(3)/duration.get(3)));
        o2_power.setText(String.format("%.3f",energy.get(4)/duration.get(4)));
        o3_power.setText(String.format("%.3f",energy.get(5)/duration.get(5)));
        o4_power.setText(String.format("%.3f",energy.get(6)/duration.get(6)));

        computeCurrent();
    }

    public void computeCurrent() {

        l1_current.setText(String.format("%.5f",Double.valueOf(l1_power.getText().toString())/220));
        l2_current.setText(String.format("%.5f",Double.valueOf(l2_power.getText().toString())/220));
        l3_current.setText(String.format("%.5f",Double.valueOf(l3_power.getText().toString())/220));
        o1_current.setText(String.format("%.5f",Double.valueOf(o1_power.getText().toString())/220));
        o2_current.setText(String.format("%.5f",Double.valueOf(o2_power.getText().toString())/220));
        o3_current.setText(String.format("%.5f",Double.valueOf(o3_power.getText().toString())/220));
        o4_current.setText(String.format("%.5f",Double.valueOf(o4_power.getText().toString())/220));
    }
}