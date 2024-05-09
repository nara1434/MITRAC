package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class PatientProfile extends AppCompatActivity {

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    private TextView textView7;
    private TextView textView8;
    private TextView textView9;
    private TextView textView10;

    private Button btn;
    String pid;
    private ImageView img;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        img = findViewById(R.id.img);
        textView1 = findViewById(R.id.id);
        textView2 = findViewById(R.id.name);
        textView3 = findViewById(R.id.age);
        textView4 = findViewById(R.id.sex);
        textView5 = findViewById(R.id.mn);
        textView6 = findViewById(R.id.education);
        textView7 = findViewById(R.id.addr);
        textView8 = findViewById(R.id.ms);
        textView9 = findViewById(R.id.ds);
        textView10 = findViewById(R.id.duration);

        // Retrieve patient ID from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("patientId")) {
            pid = intent.getStringExtra("patientId");
        } else {
            // Handle the case where no patient ID is provided
            Log.e("PatientProfile", "Patient ID not provided");
            finish();
        }

        // Make an HTTP request to your PHP script
        fetchStringFromPHP();

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientProfile.this, updatePatientProfile.class);
                startActivity(intent);
            }
        });
    }

    private void fetchStringFromPHP() {
        String url = "http://10.0.2.2/Php/viewpatient.php";

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Gson gson = new Gson();
                    Log.d("JSON Response", response);
                    JsonObject jsonObject = gson.fromJson(response, JsonObject.class);

                    if (jsonObject.has("tx1")) {
                        String status = jsonObject.get("tx1").getAsString();
                        textView1.setText(status);

                        // Print patient ID for debugging
                        Log.d("Patient ID", "Patient ID: " + status);

                        status = jsonObject.get("tx2").getAsString();
                        textView2.setText(status);
                        status = jsonObject.get("tx3").getAsString();
                        textView3.setText(status);
                        status = jsonObject.get("tx4").getAsString();
                        textView4.setText(status);
                        status = jsonObject.get("tx5").getAsString();
                        textView5.setText(status);
                        status = jsonObject.get("tx6").getAsString();
                        textView6.setText(status);
                        status = jsonObject.get("tx7").getAsString();
                        textView7.setText(status);
                        status = jsonObject.get("tx8").getAsString();
                        textView8.setText(status);
                        status = jsonObject.get("tx9").getAsString();
                        textView9.setText(status);
                        status = jsonObject.get("tx10").getAsString();
                        textView10.setText(status);

                        status = jsonObject.get("img1").getAsString();

                        String completeImageUrl = "http://10.0.2.2/Php/" + status;
                        Log.d("img", completeImageUrl);
                        Picasso.get().load(completeImageUrl).resize(500, 500).into(img);
                    } else {
                        textView1.setText("D_name not found in JSON response");
                    }
                } catch (Exception e) {
                    textView1.setText("Error: " + e.getMessage());
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView1.setText("Error: " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                Log.d("giving", pid);
                data.put("id", pid);
                return data;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
