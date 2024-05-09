package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class progressChart extends AppCompatActivity {

    private ScoreProgressView scoreProgressView;
    private TextView percentageTextView; // TextView to display the percentage
    private String patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_chart);

        // Initialize views
        scoreProgressView = findViewById(R.id.scoreProgressView);
        percentageTextView = findViewById(R.id.streak);

        // Get patientId from Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("patient_id")) {
            patientId = intent.getStringExtra("patient_id");
            // Fetch totalStreaks for the specified patient ID
            fetchTotalStreaks(patientId);
        } else {
            // Handle the case where patient_id is not provided in the Intent
            Toast.makeText(this, "Patient ID not provided", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchTotalStreaks(String patientId) {
        // Replace the URL with the actual URL of your PHP endpoint
        String apiUrl = "http://10.0.2.2/php/progress.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Log the complete response
                        Log.d("Response", "Complete Response: " + response);

                        // Check if the response contains the word "Total"
                        if (response.contains("Total")) {
                            // Handle the case where the response is not a valid JSON object
                            Log.e("Error", "Response contains the word 'Total': " + response);
                            Toast.makeText(progressChart.this, "Error parsing JSON: Invalid response format", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        try {
                            // Attempt to parse the response as JSON
                            JSONObject json = new JSONObject(response);

                            // Log the totalStreaks value
                            if (json.has("totalStreaks")) {
                                int totalStreaks = json.getInt("totalStreaks");
                                Log.d("TotalStreaks", "Total Streaks: " + totalStreaks);

                                // Calculate percentage completed
                                int currentScore = totalStreaks; // Use the value obtained from the response
                                int maximumScore = 180; // Assuming 100 represents 100% completion
                                double percentageCompleted = ((double) currentScore / maximumScore) * 100;
                                Date currentDate = new Date();

                                // Format the date if needed
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                String formattedDate = dateFormat.format(currentDate);
                                if (percentageCompleted == 0 ){
                                    String message = "Hello doctor , patient  id: "+patientId+" missed streak on "+formattedDate;
                                    insertIntoDB(message);
                                }
                                // Update the ScoreProgressView
                                scoreProgressView.setCurrentScore(currentScore);

                                // Display the percentage in the TextView
                                percentageTextView.setText(String.format(Locale.getDefault(), "%.2f%%", percentageCompleted));

                            } else {
                                // Handle the case where "totalStreaks" key is not present in the response
                                Log.e("Error", "Missing 'totalStreaks' key in the response");
                                Toast.makeText(progressChart.this, "Error parsing JSON: Missing 'totalStreaks' key", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            // Handle JSON parsing error
                            e.printStackTrace();
                            Toast.makeText(progressChart.this, "Error parsing JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle errors
                Toast.makeText(progressChart.this, "Error fetching data", Toast.LENGTH_SHORT).show();
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

    private void insertIntoDB(String message) {
        try {
            // URL of your PHP file
            String apiUrl = "http://10.0.2.2/php/message.php";

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, apiUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Handle response if needed
                            Log.d("InsertResponse", response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error
                    error.printStackTrace();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    // Pass message as a parameter to the PHP script
                    Map<String, String> params = new HashMap<>();
                    params.put("message", message);
                    return params;
                }
            };

            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
