package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class q1 extends AppCompatActivity {

    private int totalMark = 0; // Initialize totalMark to keep track of the sum
    Button btn;
    private String URL = "http://10.0.2.2/PHP/samplequestions.php";

    private static final String PREF_NAME = "MyPrefs";
    private static final String LAST_OPENED_KEY_PREFIX = "lastOpened_";
    private static final String CURRENT_PATIENT_ID_KEY = "currentPatientId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent1 = getIntent();
        String patientId = intent1.getStringExtra("patient_id");

        // Check if a week has passed since the last opening for the current patient
        if (isWeekPassed(patientId)) {
            setContentView(R.layout.activity_q1);
            initializeActivity(patientId);
        } else {
            // If not, display a message or redirect to a different activity
            Toast.makeText(this, "You can open this activity for patient " + patientId + " once a week.", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity
        }
    }

    private void initializeActivity(final String patientId) {
        // Find the RadioButtons by their IDs
        RadioButton trueRadioButton = findViewById(R.id.tr);
        RadioButton falseRadioButton = findViewById(R.id.fl);

        // Find the RadioGroup by its ID
        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        // Set the OnClickListener for the true RadioButton
        trueRadioButton.setOnClickListener(onClickListener);

        // Set the OnClickListener for the false RadioButton
        falseRadioButton.setOnClickListener(onClickListener);

        btn = findViewById(R.id.save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trueRadioButton.isChecked() || falseRadioButton.isChecked()) {
                    int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                    if (selectedRadioButtonId == -1) {
                        // No radio button is selected
                        Toast.makeText(q1.this, "Please select an option", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                    String q1Value = (selectedRadioButton.getId() == R.id.tr) ? "Yes" : "No";

                    // Intent to move to the next activity
                    Intent intent = new Intent(q1.this, q2.class);
                    intent.putExtra("patient_id", patientId);
                    intent.putExtra("Marks", totalMark);
                    Log.d("marks", "marks: "+totalMark);
                    startActivity(intent);

                    // Send data to the server
                    sendDataToServer(patientId, q1Value);
                    sendToDatabase(totalMark);

                } else {
                    Toast.makeText(q1.this, "Please Select an Option", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Handle RadioButton click
    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Check if the RadioButton is checked
            RadioButton radioButton = (RadioButton) v;
            if (radioButton.isChecked()) {
                // If checked, update the totalMark based on the selected RadioButton
                totalMark = (radioButton.getId() == R.id.tr) ? 1 : 0;
            }
        }
    };
    private void sendToDatabase(final int marks) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the server response (if needed)
                        Toast.makeText(q1.this, "Data sent to the database", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors
                        Toast.makeText(q1.this, "Error sending data to the database", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("totalMarks", String.valueOf(marks));
                return params;
            }
        };

        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(stringRequest);
    }


    // Method to make an HTTP request to send data to the database using Volley
    private void sendDataToServer(final String patientId, final String q1Value) {
        String url = "http://10.0.2.2/Php/updatescreening2.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the server response (if needed)
                        Toast.makeText(q1.this, "Data sent to the database", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors
                        Toast.makeText(q1.this, "Error sending data to the database", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("patient_id", patientId);
                params.put("q1", q1Value);
                return params;
            }
        };

        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private boolean isWeekPassed(String patientId) {
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        long lastOpenedTime = prefs.getLong(getLastOpenedKey(patientId), 0);

        long currentTime = System.currentTimeMillis();
        long oneWeekInMillis = 7 * 24 * 60 * 60 * 1000; // One week in milliseconds

        return (currentTime - lastOpenedTime) >= oneWeekInMillis;
    }

    private void saveLastOpenedTime(String patientId) {
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(getLastOpenedKey(patientId), System.currentTimeMillis());
        editor.putString(CURRENT_PATIENT_ID_KEY, patientId);
        editor.apply();
    }

    private String getLastOpenedKey(String patientId) {
        return LAST_OPENED_KEY_PREFIX + patientId;
    }
}
