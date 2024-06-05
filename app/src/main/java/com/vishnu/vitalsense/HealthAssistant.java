package com.vishnu.vitalsense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class HealthAssistant extends AppCompatActivity {
    LinearLayout chatbox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_assistant);
        chatbox = findViewById(R.id.chatbox);

        DBHelper dbHelper = new DBHelper(this);
        SessionManager sessionManager = new SessionManager(this);
        int userid = sessionManager.getUserId();
        HashMap lastData = dbHelper.getLastData(userid);
        loadText(chatbox,true);
        String url = "https://vitsense.pythonanywhere.com/generate_response";
        try {
            String prompt = "These are the last readings of the app. spo02:"+lastData.get("spo2") + ", heart rate:" + lastData.get("hr") + ", respiration rate:" + lastData.get("resp_rate")+" give me some insights and advise based on this data try to include food and exercise suggestions";
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("prompt", prompt);
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonParams,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                loadText(chatbox,false);
                                String message = response.getString("response");
                                androidx.cardview.widget.CardView card = new androidx.cardview.widget.CardView(getApplicationContext());
                                card.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                card.setCardBackgroundColor(getResources().getColor(R.color.chat_bg_color));
                                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) card.getLayoutParams();
                                params.setMargins(0, 20, 0, 0);
                                card.setRadius(70);
                                TextView textView = new TextView(getApplicationContext());
                                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                textView.setText(message);
                                textView.setPadding(30, 30, 30, 30);
                                textView.setTextColor(getResources().getColor(R.color.white));
                                Typeface typeface_pop = ResourcesCompat.getFont(getApplicationContext(), R.font.poppins_semibold);
                                textView.setTypeface(typeface_pop);
                                card.addView(textView);
                                chatbox.addView(card);

                            } catch (Exception e) {
                                e.printStackTrace();
                                loadText(chatbox,false);
                            }
                        }
                    },
                    error -> {
                        error.printStackTrace();
                    }
            );
            Volley.newRequestQueue(getApplicationContext()).
                    add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    //loading text from the server
    public void loadText(LinearLayout chatbox,Boolean loading) {
        if (loading) {
            androidx.cardview.widget.CardView card = new androidx.cardview.widget.CardView(getApplicationContext());
            card.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            card.setCardBackgroundColor(getResources().getColor(R.color.chat_load_bg_color));
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) card.getLayoutParams();
            params.setMargins(0, 20, 0, 0);
            card.setRadius(70);
            TextView textView = new TextView(getApplicationContext());
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setText("Please wait while we fetch the data from the server...🐢");
            textView.setPadding(30, 30, 30, 30);
            textView.setTextColor(getResources().getColor(R.color.white));
            Typeface typeface_pop = ResourcesCompat.getFont(getApplicationContext(), R.font.poppins_semibold);
            textView.setTypeface(typeface_pop);
            card.addView(textView);
            chatbox.addView(card);
        } else {
            chatbox.removeViewAt(chatbox.getChildCount() - 1);
        }
    }

    public void backToDashboard(View view) {
        Intent intent = new Intent(HealthAssistant.this, Dashboard.class);
        startActivity(intent);
        finish();
    }
}