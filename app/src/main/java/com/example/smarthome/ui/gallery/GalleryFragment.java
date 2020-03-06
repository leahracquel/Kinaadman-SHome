package com.example.smarthome.ui.gallery;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.smarthome.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Calendar;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private View root;
    private Switch outlet4;
    private DatabaseReference automatic;
    private DatabaseReference manual;
    private DatabaseReference outlet4_auto;
    private String outlet4_manual;
    private EditText starttime;
    private EditText endtime;
    private Spinner day;
    private TextView lbl_day;
    private TextView lbl_endtime;
    private TextView lbl_starttime;
    private Button set;
    private String outlet_start, outlet_end;

    public GalleryFragment () {}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        root = inflater.inflate(R.layout.fragment_automate, container, false);
        galleryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {}
        });

        initializeObjects();
        setupListener();
        return root;
    }

    public void initializeObjects() {
        outlet4 = root.findViewById(R.id.sw_aoutlet4);
        lbl_day = root.findViewById(R.id.textView31);
        lbl_endtime = root.findViewById(R.id.textView30);
        lbl_starttime = root.findViewById(R.id.textView7);
        starttime = root.findViewById(R.id.txt_starttime);
        endtime = root.findViewById(R.id.txt_endtime);
        day = root.findViewById(R.id.txt_day);
        set = root.findViewById(R.id.btn_set);

        manual = FirebaseDatabase.getInstance().getReference("ManualMode");
        automatic = FirebaseDatabase.getInstance().getReference("AutoMode");
        outlet4_auto = FirebaseDatabase.getInstance().getReference("AutoMode").child("Outlet4");

        getOutlet4_Automate();
        getOutlet4_Manual();
    }

    public void setupListener() {
        outlet4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if (outlet4_manual != null && outlet4_manual.equals("on")) {
                        Toast.makeText(getActivity(),"Set Outlet4 Manual to off.",Toast.LENGTH_SHORT).show();
                        manual.child("Outlet4").setValue("off");
                    }
                    lbl_day.setVisibility(View.VISIBLE);
                    lbl_endtime.setVisibility(View.VISIBLE);
                    lbl_starttime.setVisibility(View.VISIBLE);
                    starttime.setVisibility(View.VISIBLE);
                    endtime.setVisibility(View.VISIBLE);
                    day.setVisibility(View.VISIBLE);
                    set.setVisibility(View.VISIBLE);
                } else {
                    outlet4_auto.child("Set_Status").setValue("off");
                }
            }
        });

        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currenttime = Calendar.getInstance();
                int hour = currenttime.get(Calendar.HOUR_OF_DAY);
                int minute = currenttime.get(Calendar.MINUTE);
                TimePickerDialog time_Picker;
                time_Picker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        starttime.setText(hourOfDay+" : "+minute);
                    }
                },hour,minute,true);
                time_Picker.setTitle("Set Start Time");
                time_Picker.show();
            }
        });

        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currenttime = Calendar.getInstance();
                int hour = currenttime.get(Calendar.HOUR_OF_DAY);
                int minute = currenttime.get(Calendar.MINUTE);
                TimePickerDialog time_Picker;
                time_Picker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endtime.setText(hourOfDay+" : "+minute);
                    }
                },hour,minute,true);
                time_Picker.setTitle("Set End Time");
                time_Picker.show();
            }
        });

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outlet4_auto.child("Set_DayOfWeek").setValue(day.getSelectedItem().toString());
                outlet4_auto.child("Set_StartTime").setValue(starttime.getText().toString());
                outlet4_auto.child("Set_EndTime").setValue(endtime.getText().toString());
                outlet4_auto.child("Set_Status").setValue("on");
                Toast.makeText(getActivity(),"Automated time set.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getOutlet4_Automate() {

        outlet4_auto.child("Set_Status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue().toString().equals("on")) {
                    outlet4.setChecked(true);
                    lbl_day.setVisibility(View.VISIBLE);
                    lbl_endtime.setVisibility(View.VISIBLE);
                    lbl_starttime.setVisibility(View.VISIBLE);
                    set.setVisibility(View.VISIBLE);
                    starttime.setVisibility(View.VISIBLE);
                    endtime.setVisibility(View.VISIBLE);
                    day.setVisibility(View.VISIBLE);
                } else {
                    outlet4.setChecked(false);
                    lbl_day.setVisibility(View.INVISIBLE);
                    lbl_endtime.setVisibility(View.INVISIBLE);
                    lbl_starttime.setVisibility(View.INVISIBLE);
                    set.setVisibility(View.INVISIBLE);
                    starttime.setVisibility(View.INVISIBLE);
                    endtime.setVisibility(View.INVISIBLE);
                    day.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        outlet4_auto.child("Set_DayOfWeek").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Days, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                day.setAdapter(adapter);
                if (dataSnapshot.getValue().toString() != null) {
                    int spinnerPosition = adapter.getPosition(dataSnapshot.getValue().toString());
                    day.setSelection(spinnerPosition);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        outlet4_auto.child("Set_StartTime").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                starttime.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        outlet4_auto.child("Set_EndTime").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                endtime.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void getOutlet4_Manual() {

        manual.child("Outlet4").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                outlet4_manual = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
}