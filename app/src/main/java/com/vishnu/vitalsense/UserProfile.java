package com.vishnu.vitalsense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class UserProfile extends AppCompatActivity {
    SessionManager sessionManager;
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        sessionManager = new SessionManager(getApplicationContext());
        name=findViewById(R.id.usrname);
        int user_id = sessionManager.getUserId();
        DBHelper dbHelper = new DBHelper(this);
        HashMap user = dbHelper.getUser(user_id);
        String name = user.get("name").toString();
        this.name.setText(name);
    }

    public void logout(View view) {
        sessionManager.logout();
        Intent intent = new Intent(UserProfile.this, MainScreen.class);
        startActivity(intent);
    }
}