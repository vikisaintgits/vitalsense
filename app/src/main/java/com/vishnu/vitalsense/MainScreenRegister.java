package com.vishnu.vitalsense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainScreenRegister extends AppCompatActivity {
    EditText name, email, password, age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_register);
        name = findViewById(R.id.editTextName);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        age = findViewById(R.id.editTextAge);
    }

    public void loginScreen(View view) {
        //intent to MainScreen activity
        Intent intent = new Intent(this, MainScreen.class);
        startActivity(intent);
        finish();
    }

    public void RegisterUser(View view) {
        //register user
        String nameStr = name.getText().toString();
        String emailStr = email.getText().toString();
        String passwordStr = password.getText().toString();
        String ageStr = age.getText().toString();
        if (ageStr.isEmpty()) {
            ageStr = "0";
        }
        //check if any field is empty
        if (nameStr.isEmpty() || emailStr.isEmpty() || passwordStr.isEmpty() || ageStr =="0") {
            if (nameStr.isEmpty()){
                name.setError("Name is required");
            }if (emailStr.isEmpty()) {
                email.setError("Email is required");
            }if (passwordStr.isEmpty()) {
                password.setError("Password is required");
            }if(ageStr=="0"){
                age.setError("Age is required");
            }
        } else {
            if (!nameStr.matches("[a-zA-Z]+")) {
                name.setError("Name should only contain letters");
                return;
            }

            int ageInt = Integer.parseInt(ageStr);
            if (ageInt < 5 || ageInt > 100) {
                age.setError("Age should be between 5 and 100");
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
                email.setError("Invalid email address");
                return;
            }

            if (passwordStr.length() < 8) {
                password.setError("Password should be at least 8 characters long");
                return;
            }

            //register user using DBHelper
            DBHelper dbHelper = new DBHelper(this);
            int result = dbHelper.addUser(nameStr, emailStr, passwordStr, Integer.parseInt(ageStr)  );
            if (result == 1) {
                Toast.makeText(this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainScreen.class);
                startActivity(intent);
                finish();
            }
        }
    }
}