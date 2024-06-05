package com.vishnu.vitalsense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class TimerRespiration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_respiration);

        String type = "";
        Intent intent = getIntent();
        if (intent != null)
            type = intent.getStringExtra("name");

        TextView timer = findViewById(R.id.timer);

        String finalType = type;
        new CountDownTimer(5100, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText(Long.toString(millisUntilFinished / 1000) + "s");
            }

            public void onFinish() {
                timer.setText("0s");
                Intent intent;
                intent = new Intent(TimerRespiration.this, Accelerometer.class);
                startActivity(intent);
                finish();
            }

        }.start();
    }
}