package com.example.smarthome.ui.share;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.smarthome.R;
import com.example.smarthome.ui.gallery.GalleryViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;
    private View root;
    private DatabaseReference energy_consumption;
    private DatabaseReference time_consumed, totalkwh1;
    private DatabaseReference veco_rating, node_parent;
    private TextView txt_date, bill, estimate_bill, rating;
    private TextView l1_current, l1_power, l1_duration, l1_energy;
    private TextView l2_current, l2_power, l2_duration, l2_energy;
    private TextView l3_current, l3_power, l3_duration, l3_energy;
    private TextView o1_current, o1_power, o1_duration, o1_energy;
    private TextView o2_current, o2_power, o2_duration, o2_energy;
    private TextView o3_current, o3_power, o3_duration, o3_energy;
    private TextView o4_current, o4_power, o4_duration, o4_energy;
    private String current_date;
    private ArrayList<Double> energy, duration, power, current;
    private String[] split_date;
    private Double monthly_bill;
    private Double total_kwh;

    public ShareFragment() {}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
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
        SimpleDateFormat curr_date = new SimpleDateFormat("M-dd-yyyy");
        current_date = curr_date.format(cld.getTime());
        split_date = current_date.split("-");

        energy_consumption = FirebaseDatabase.getInstance().getReference("EnergyConsumption_Daily");
        veco_rating = FirebaseDatabase.getInstance().getReference();
        time_consumed = FirebaseDatabase.getInstance().getReference("Output_TimeConsumed_Daily");
        node_parent = FirebaseDatabase.getInstance().getReference("MonthlyBill");
        totalkwh1 = FirebaseDatabase.getInstance().getReference("DailyBill");

        txt_date = root.findViewById(R.id.txt_date);
        bill = root.findViewById(R.id.bill);
        estimate_bill = root.findViewById(R.id.estimate_bill);
        rating = root.findViewById(R.id.kwph);
        txt_date.setText(current_date);
        energy = new ArrayList<>();
        duration = new ArrayList<>();
        power = new ArrayList<>();
        current = new ArrayList<>();
        monthly_bill = 0.0;
        total_kwh = 0.0;

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
                    for (DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        total_kwh = total_kwh + Double.valueOf(snapshot.getValue().toString());
                        energy.add(Double.valueOf(snapshot.getValue().toString()));
                    }

                    veco_rating.child("DailyBill").child(current_date).setValue(total_kwh);

                    bill.setText(String.format("%5f",total_kwh));
                    displayEnergy();
                }
                computeTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Calendar cld = Calendar.getInstance();
        SimpleDateFormat actual_date = new SimpleDateFormat("M-dd-yyyy");
        String current_date = actual_date.format(cld.getTime());
        final String[] split_current_date = current_date.split("-");

        totalkwh1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Double monthly_bill = 0.0;
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
    }

    public void computeTotal () {
        node_parent.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dsp:dataSnapshot.getChildren()) {
                    String[] fb_split_date = dsp.getKey().split("-");
                    if(fb_split_date[0].equals(split_date[0])) {
                        if(Float.valueOf(dsp.getValue().toString()) != 0.0f){
                            monthly_bill = monthly_bill + Double.valueOf(dsp.getValue().toString());
                        }
                    }
                }
                node_parent.child(split_date[0]).setValue(monthly_bill);
                estimate_bill.setText(monthly_bill.toString());
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
                    Double total_duration = 0.0;
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                        total_duration = total_duration + Double.valueOf(snapshot.getValue().toString());
                        duration.add(Double.valueOf(snapshot.getValue().toString()));
                    }
                    veco_rating.child("DailyDuration").child(current_date).setValue(total_duration);
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

        Double total_power = 0.0;
        for(int i=0; i<6; i++) {
            total_power = total_power + Double.valueOf(energy.get(i)/duration.get(i));
            power.add(energy.get(i)/duration.get(i));
        }

        veco_rating.child("DailyPower").child(current_date).setValue(total_power);
        l1_power.setText(String.format("%.3f",energy.get(0)/duration.get(0)));
        l2_power.setText(String.format("%.3f",energy.get(1)/duration.get(1)));
        l3_power.setText(String.format("%.3f",energy.get(2)/duration.get(2)));
        o1_power.setText(String.format("%.3f",energy.get(3)/duration.get(3)));
        o2_power.setText(String.format("%.3f",energy.get(4)/duration.get(4)));
        o3_power.setText(String.format("%.3f",energy.get(5)/duration.get(5)));
        o4_power.setText(String.format("%.3f",energy.get(6)/duration.get(6)));

        computeCurrent(power);
    }

    public void computeCurrent(ArrayList<Double> power) {

        Double total_current = 0.0;
        for(int i=0; i<6; i++) {
            total_current = total_current + (Double.valueOf(power.get(i))/220);
        }

        veco_rating.child("DailyCurrent").child(current_date).setValue(total_current);

        l1_current.setText(String.format("%.5f",Double.valueOf(l1_power.getText().toString())/220));
        l2_current.setText(String.format("%.5f",Double.valueOf(l2_power.getText().toString())/220));
        l3_current.setText(String.format("%.5f",Double.valueOf(l3_power.getText().toString())/220));
        o1_current.setText(String.format("%.5f",Double.valueOf(o1_power.getText().toString())/220));
        o2_current.setText(String.format("%.5f",Double.valueOf(o2_power.getText().toString())/220));
        o3_current.setText(String.format("%.5f",Double.valueOf(o3_power.getText().toString())/220));
        o4_current.setText(String.format("%.5f",Double.valueOf(o4_power.getText().toString())/220));
    }
}