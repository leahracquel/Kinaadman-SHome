package com.example.smarthome.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.smarthome.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private View root;
    private Switch light1;
    private Switch light2;
    private Switch light3;
    private Switch outlet1;
    private Switch outlet2;
    private Switch outlet3;
    private Switch outlet4;
    private DatabaseReference automatic;
    private DatabaseReference manual;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_control, container, false);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {}
        });

        initializeObjects();
        setupListener();
        return root;
    }

    public void initializeObjects() {
        light1 = root.findViewById(R.id.sw_l1);
        light2 = root.findViewById(R.id.sw_l2);
        light3 = root.findViewById(R.id.sw_l3);
        outlet1 = root.findViewById(R.id.sw_outlet1);
        outlet2 = root.findViewById(R.id.sw_outlet2);
        outlet3 = root.findViewById(R.id.sw_outlet3);
        outlet4 = root.findViewById(R.id.sw_outlet4);

        manual = FirebaseDatabase.getInstance().getReference("ManualMode");
        automatic = FirebaseDatabase.getInstance().getReference("AutoMode");
    }

    public void setupListener() {
        light1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    manual.child("Light1").setValue("on");
                } else {
                    manual.child("Light1").setValue("off");
                }
            }
        });
    }
}