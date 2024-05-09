package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CareTakerDashBoard extends AppCompatActivity {
    String patientId;
    TextView patientRelationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_taker_dash_board);

        // Retrieve patient ID from Intent extras
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("patient_id")) {
            patientId = intent.getStringExtra("patient_id");
        } else {
            // Handle the case where patient ID is not provided
            Log.e("CareTakerDashBoard", "Patient ID not provided");
            // Optionally, you may finish the activity here or handle the error in another way
            finish();
            return;
        }
        Log.d("patient id", patientId);
        patientRelationTextView = findViewById(R.id.relation1);

        // Fetch and display patient name
        fetchPatientRelation(patientId);

        // Initialize ImageButtons
        ImageButton weeklyQuestions = findViewById(R.id.image1);
        ImageButton careTakerProfile = findViewById(R.id.image);
        ImageButton medicineCheckup = findViewById(R.id.image2);
        ImageButton bookAppointments = findViewById(R.id.image3);
        ImageButton bookedSlots = findViewById(R.id.image4);
        ImageButton videos = findViewById(R.id.btn6);
        ImageButton viewProgress = findViewById(R.id.progress);
        ImageButton dailyQuestions = findViewById(R.id.image11);

        // Set OnClickListener for each ImageButton
        dailyQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CareTakerDashBoard.this, sq1.class);
                intent.putExtra("patient_id", patientId);
                startActivity(intent);
            }
        });

        viewProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CareTakerDashBoard.this, progressChart.class);
                intent.putExtra("patient_id", patientId);
                startActivity(intent);
            }
        });

        weeklyQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CareTakerDashBoard.this, q1.class);
                intent.putExtra("patient_id", patientId);
                startActivity(intent);
            }
        });

        careTakerProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CareTakerDashBoard.this, caretakerprofiledispaly.class);
                startActivity(intent);
            }
        });

        bookAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CareTakerDashBoard.this, calender.class);
                intent.putExtra("patient_id", patientId);
                startActivity(intent);
            }
        });

        bookedSlots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CareTakerDashBoard.this, bookedslots.class);
                intent.putExtra("patient_id", patientId);
                startActivity(intent);
            }
        });

        medicineCheckup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CareTakerDashBoard.this, medicineMonitoring.class);
                intent.putExtra("patient_id", patientId);
                startActivity(intent);
            }
        });

        videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CareTakerDashBoard.this, displayVideos.class);
                startActivity(intent);
            }
        });
    }

    private void fetchPatientRelation(String patientId) {
        // Replace the URL with the actual URL of your PHP endpoint to fetch patient relation
        String apiUrl = "http://10.0.2.2/php/ctrelation.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parse the JSON response
                            JSONObject jsonObject = new JSONObject(response);
                            String patientRelation = jsonObject.getString("relation");

                            // Display the patient relation in the TextView
                            patientRelationTextView.setText(patientRelation);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Error", "Error parsing JSON: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle errors
                Log.e("Error", "Error fetching patient relation: " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Pass patientId as a parameter to the PHP script
                Map<String, String> params = new HashMap<>();
                params.put("patient_id", patientId);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
