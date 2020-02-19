package com.example.smarthome.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.smarthome.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private DatabaseReference outlet4_auto;
    private DatabaseReference manual;
    private String l1_automatic, l2_automatic, l3_automatic, outlet4_automatic;

    public HomeFragment () {}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_control, container, false);

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
        outlet4_auto = FirebaseDatabase.getInstance().getReference("AutoMode").child("Outlet4");

        getLight1_Manual();
        getLight2_Manual();
        getLight3_Manual();
        getOutlet1_Manual();
        getOutlet2_Manual();
        getOutlet3_Manual();
        getOutlet4_Manual();
        getLight1_Automatic();
        getLight2_Automatic();
        getLight3_Automatic();
        getOutlet4_Automatic();
    }

    public void setupListener() {
        light1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if (l1_automatic.equals("on")) {
                        Toast.makeText(getActivity(),"Set Light1 Automate to off.",Toast.LENGTH_SHORT).show();
                        automatic.child("Light1").setValue("off");
                    }
                    manual.child("Light1").setValue("on");
                } else {
                    manual.child("Light1").setValue("off");
                }
            }
        });

        light2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if (l2_automatic.equals("on")) {
                        Toast.makeText(getActivity(),"Set Light2 Automate to off.",Toast.LENGTH_SHORT).show();
                        automatic.child("Light2").setValue("off");
                    }
                    manual.child("Light2").setValue("on");
                } else {
                    manual.child("Light2").setValue("off");
                }
            }
        });

        light3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if (l3_automatic.equals("on")) {
                        Toast.makeText(getActivity(),"Set Light3 Automate to off.",Toast.LENGTH_SHORT).show();
                        automatic.child("Light3").setValue("off");
                    }
                    manual.child("Light3").setValue("on");
                } else {
                    manual.child("Light3").setValue("off");
                }
            }
        });

        outlet1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    manual.child("Outlet1").setValue("on");
                } else {
                    manual.child("Outlet1").setValue("off");
                }
            }
        });

        outlet2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    manual.child("Outlet2").setValue("on");
                } else {
                    manual.child("Outlet2").setValue("off");
                }
            }
        });

        outlet3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    manual.child("Outlet3").setValue("on");
                } else {
                    manual.child("Outlet3").setValue("off");
                }
            }
        });

        outlet4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if (outlet4_automatic.equals("on")) {
                        Toast.makeText(getActivity(),"Set Outlet4 Automate to off.",Toast.LENGTH_SHORT).show();
                        outlet4_auto.child("Set_Status").setValue("off");
                    }
                    manual.child("Outlet4").setValue("on");
                } else {
                    manual.child("Outlet4").setValue("off");
                }
            }
        });
    }

    public void getLight1_Automatic() {

        automatic.child("Light1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                l1_automatic = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void getLight2_Automatic() {

        automatic.child("Light2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                l2_automatic = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void getLight3_Automatic() {

        automatic.child("Light3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                l3_automatic = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void getOutlet4_Automatic() {

        outlet4_auto.child("Set_Status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                outlet4_automatic = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void getLight1_Manual() {

        manual.child("Light1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue().toString().equals("on")) {
                    light1.setChecked(true);
                } else {
                    light1.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void getLight2_Manual() {

        manual.child("Light2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals("on")) {
                    light2.setChecked(true);
                } else {
                    light2.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void getLight3_Manual() {

        manual.child("Light3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals("on")) {
                    light3.setChecked(true);
                } else {
                    light3.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void getOutlet1_Manual() {

        manual.child("Outlet1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals("on")) {
                    outlet1.setChecked(true);
                } else {
                    outlet1.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void getOutlet2_Manual() {

        manual.child("Outlet2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals("on")) {
                    outlet2.setChecked(true);
                } else {
                    outlet2.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void getOutlet3_Manual() {

        manual.child("Outlet3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals("on")) {
                    outlet3.setChecked(true);
                } else {
                    outlet3.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void getOutlet4_Manual() {

        manual.child("Outlet4").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals("on")) {
                    outlet4.setChecked(true);
                } else {
                    outlet4.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
}