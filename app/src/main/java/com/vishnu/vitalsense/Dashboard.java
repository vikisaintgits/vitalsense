package com.vishnu.vitalsense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    LineChart lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        CardView spo2 = findViewById(R.id.spo2);
        CardView rr = findViewById(R.id.resp_rate);
        CardView hr = findViewById(R.id.heart_rate);
        CardView healthAssistant = findViewById(R.id.health_assistant);


        spo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, TimerActivity.class);
                intent.putExtra("name", "spo2");
                startActivity(intent);
                finish();
            }
        });


        hr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, TimerActivity.class);
                intent.putExtra("name", "hr");
                startActivity(intent);
                finish();
            }
        });

        rr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, TimerRespiration.class);
                intent.putExtra("name", "resp");
                startActivity(intent);
                finish();
            }
        });
        healthAssistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, HealthAssistant.class);
                startActivity(intent);
            }
        });



        ///👇 Code For Graph
        lineChart = findViewById(R.id.lineChart);
        lineChart.getDescription().setEnabled(false);
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.getXAxis().setDrawLabels(false);

        LineData lineData = new LineData();

        int user_id = new SessionManager(getApplicationContext()).getUserId();
        DBHelper dbHelper = new DBHelper(this);
        int[] hrList = dbHelper.getHeartRate(user_id);
        int[] spo2List = dbHelper.getSpo2(user_id);
        int[] respList = dbHelper.getRespRate(user_id);
        //add to chart
        if(hrList!=null) {
            ArrayList<Entry> hrentries = new ArrayList<>();
            for (int i = 0; i < hrList.length; i++) {
                hrentries.add(new Entry(i, hrList[i]));
            }
            LineDataSet hrDataSet = new LineDataSet(hrentries, "Heart Rate");
            hrDataSet.setColor(Color.RED); // Set line color to green
            hrDataSet.setLineWidth(2f);
            hrDataSet.setCircleColor(Color.RED);
            hrDataSet.setCircleRadius(5f);
            hrDataSet.setValueTextSize(10f);
            lineData.addDataSet(hrDataSet);
        }
        if (spo2List!=null) {
            ArrayList<Entry> spo2entries = new ArrayList<>();
            for (int i = 0; i < spo2List.length; i++) {
                spo2entries.add(new Entry(i, spo2List[i]));
            }
            LineDataSet spo2DataSet = new LineDataSet(spo2entries, "SpO2");
            spo2DataSet.setColor(Color.BLUE); // Set line color to red
            spo2DataSet.setLineWidth(2f);
            spo2DataSet.setCircleColor(Color.BLUE);
            spo2DataSet.setCircleRadius(5f);
            spo2DataSet.setValueTextSize(10f);
            lineData.addDataSet(spo2DataSet);
        }
        if (respList!=null) {
            ArrayList<Entry> respentries = new ArrayList<>();
            for (int i = 0; i < respList.length; i++) {
                respentries.add(new Entry(i, respList[i]));
            }
            LineDataSet respDataSet = new LineDataSet(respentries, "Respiration Rate");
            respDataSet.setColor(Color.GREEN); // Set line color to blue
            respDataSet.setLineWidth(2f);
            respDataSet.setCircleColor(Color.GREEN);
            respDataSet.setCircleRadius(5f);
            respDataSet.setValueTextSize(10f);
            lineData.addDataSet(respDataSet);
        }



        lineChart.setData(lineData);
        lineChart.invalidate(); // Refresh chart


    }

    public void userProfile(View view) {
        Intent intent = new Intent(this, UserProfile.class);
        startActivity(intent);
    }
}