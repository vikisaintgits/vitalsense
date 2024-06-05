package com.vishnu.vitalsense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainScreen extends AppCompatActivity {
    EditText email, password;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        sessionManager = new SessionManager(getApplicationContext());
        if (sessionManager.isLoggedIn()) {
            Intent intent = new Intent(this, Dashboard.class);
            startActivity(intent);
            finish();
        }
    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, MainScreenRegister.class);
        startActivity(intent);
        finish();
    }

    public void loginUser(View view) {
        DBHelper dbHelper = new DBHelper(this);
        int user_id = dbHelper.checkUser(email.getText().toString(), password.getText().toString());
        if (user_id != -1) {
            Intent intent = new Intent(this, Dashboard.class);
            sessionManager.login(user_id);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }

    public void forgotpass(View view) {
        DBHelper dbHelper = new DBHelper(this);
        String pass = dbHelper.getPassword(email.getText().toString());
        if (pass != null) {
            Toast.makeText(this, "Your password is: " + pass, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
        }

    }
}