package com.vishnu.vitalsense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ScoreCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_card);

        TextView name = findViewById(R.id.name);
        TextView score = findViewById(R.id.scoreText);
        TextView normal = findViewById(R.id.normal);

        Intent intent = getIntent();
        if (intent != null) {
            name.setText(intent.getStringExtra("name"));
            if (intent.getStringExtra("name").equals("Blood Pressure"))
                score.setText(intent.getStringExtra("score"));
            else
                score.setText(intent.getIntExtra("score", 0) != -1 ? Integer.toString(intent.getIntExtra("score", 0)): "Insufficient data");

            normal.setText("Normal range: \n" + intent.getStringExtra("normal"));
        }
    }

    public void backDash(View view) {
        Intent intent = new Intent(ScoreCard.this, Dashboard.class);
        startActivity(intent);
        finish();
    }
}