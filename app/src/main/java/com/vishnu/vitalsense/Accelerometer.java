package com.vishnu.vitalsense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Accelerometer extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorManager;
    ArrayList<Float> zList = new ArrayList<>();
    boolean ready = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        TextView timer = findViewById(R.id.timer);

        new CountDownTimer(30000, 800) {

            public void onTick(long millisUntilFinished) {
                timer.setText(Long.toString(millisUntilFinished/1000) + "s");
                ready = true;
            }

            public void onFinish() {
                timer.setText("0s");
                ready = false;
                double[] arr = new double[zList.size()];

                for (int i = 0; i < zList.size(); i++) {
                    arr[i] = zList.get(i);
                }


                double arr2[] = ComputeMovingAverage(arr, arr.length, 3);

                int peaks = printPeaksTroughs(arr2, arr2.length);

                double sum = 0;
                for (int i = 0; i < arr.length; i++) {
                    sum += arr[i];
                }

                double avg = (double) sum/arr.length;

                double sum2 = 0;
                for (int i = 0; i < arr.length; i++) {
                    sum2 += Math.pow(arr[i] - avg, 2);
                }

                double var = (double) sum2/(arr.length - 1);
                double sd = Math.sqrt(var);

                //add to db
                DBHelper dbHelper = new DBHelper(Accelerometer.this);
                int user_id = new SessionManager(Accelerometer.this).getUserId();
                String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                dbHelper.addRespRate(peaks*2, user_id, currentTime);

                Intent intent = new Intent(Accelerometer.this, ScoreCard.class);
                intent.putExtra("name", "Respiratory Rate");
                intent.putExtra("score",  sd < 0.035 ? -1 : peaks*2);
                intent.putExtra("normal", "12 - 20 BPM");
                startActivity(intent);
                finish();
            }

        }.start();

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            if (ready) {
                ready = false;
                zList.add(sensorEvent.values[2]);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public double[] ComputeMovingAverage(double arr[], int N, int K)
    {
        double[] arr_new = new double[N];
        int i;
        float sum = 0;
        for (i = 0; i < K; i++) {
            sum += arr[i];
            arr_new[i] = (sum / K);
        }
        for (i = K; i < N; i++) {
            sum -= arr[i - K];
            sum += arr[i];
            arr_new[i] = (sum / K);
        }
        return arr_new;
    }


    public boolean isPeak(double arr[], int n, double num, int i, int j)
    {
        if (i >= 0 && arr[i] > num)
            return false;

        if (j < n && arr[j] > num)
            return false;
        return true;
    }

    public int printPeaksTroughs(double arr[], int n)
    {
        int count=0;

        for (int i = 0; i < n; i++)
        {
            if (isPeak(arr, n, arr[i], i - 1, i + 1))
            {
                count++;
            }
        }
        return count;
    }
}