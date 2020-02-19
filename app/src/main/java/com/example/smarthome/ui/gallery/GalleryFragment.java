package com.example.smarthome.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Switch;

import com.example.smarthome.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.smarthome.R;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private View root;
    private Switch light1;
    private Switch light2;
    private Switch light3;
    private Switch outlet4;
    private DatabaseReference automatic;
    private DatabaseReference manual;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_automate, container, false);
        galleryViewModel.getText().observe(this, new Observer<String>() {
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
        outlet4 = root.findViewById(R.id.sw_outlet4);

        manual = FirebaseDatabase.getInstance().getReference("ManualMode");
        automatic = FirebaseDatabase.getInstance().getReference("AutoMode");
    }

    public void setupListener() {
        light1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    /*
                    if (manual.child("Light1").getValue() == "on") {
                        // Toast to set automatic child to off
                        manual.child("Light1").setValue("off");
                    }
                    */
                    automatic.child("Light1").setValue("on");
                } else {
                    automatic.child("Light1").setValue("off");
                }
            }
        });

        light2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    /*
                    if (manual.child("Light2").getValue() == "on") {
                        // Toast to set automatic child to off
                        manual.child("Light2").setValue("off");
                    }
                    */
                    automatic.child("Light2").setValue("on");
                } else {
                    automatic.child("Light2").setValue("off");
                }
            }
        });

        light3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    /*
                    if (manual.child("Light3").getValue() == "on") {
                        // Toast to set automatic child to off
                        manual.child("Light3").setValue("off");
                    }
                    */
                    automatic.child("Light3").setValue("on");
                } else {
                    automatic.child("Light3").setValue("off");
                }
            }
        });

        outlet4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    /*
                    if (manual.child("Outlet4").child("Set_Status").getValue() == "on") {
                        // Toast to set automatic child to off
                        manual.child("Outlet4").child("Set_Status").setValue("off");
                    }
                    //show set day, set start time, set end time
                    */
                    manual.child("Outlet4").setValue("on");
                } else {
                    manual.child("Outlet4").setValue("off");
                }
            }
        });
    }
}