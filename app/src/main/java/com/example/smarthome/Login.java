package com.example.smarthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Login extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private Intent dashboard;
    private TextView empty_error;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initializeObjects();
        setupListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getDelegate().onStart();
        currentUser = mAuth.getCurrentUser();
    }

    public void initializeObjects() {
        username = findViewById(R.id.txt_username);
        password = findViewById(R.id.txt_password);
        login = findViewById(R.id.btn_login);
        empty_error = findViewById(R.id.txt_emptyfields);

        dashboard = new Intent(getApplicationContext(),Dashboard.class);

        mAuth = FirebaseAuth.getInstance();
    }

    public void setupListeners() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    public void loginUser() {
        String email = username.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if(!email.isEmpty() && !pass.isEmpty()) {
            empty_error.setVisibility(View.INVISIBLE);
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                currentUser = mAuth.getCurrentUser();
                                startActivity(dashboard);
                                finish();
                            } else {
                                empty_error.setText("Incorrect email or password.");
                                empty_error.setVisibility(View.VISIBLE);
                            }
                        }
                    }
            );
        } else {
            empty_error.setText("Please fill up fields.");
            empty_error.setVisibility(View.VISIBLE);
        }
    }

}
