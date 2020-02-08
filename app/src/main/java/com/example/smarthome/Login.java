package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private Intent dashboard;
    private TextView empty_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initializeObjects();
        setupListeners();
    }

    public void initializeObjects() {
        username = findViewById(R.id.txt_username);
        password = findViewById(R.id.txt_password);
        login = findViewById(R.id.btn_login);
        empty_error = findViewById(R.id.txt_emptyfields);

        dashboard = new Intent(getApplicationContext(),Dashboard.class);
        dashboard.putExtra("username",username.getText().toString());
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
        String user = username.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if(!user.isEmpty() && !pass.isEmpty()) {
            empty_error.setVisibility(View.INVISIBLE);
            startActivity(dashboard);
            finish();
        } else {
            empty_error.setVisibility(View.VISIBLE);
        }
    }
}
