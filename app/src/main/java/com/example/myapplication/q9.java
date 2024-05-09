package com.example.myapplication;

import android.content.Intent;
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

public class q9 extends AppCompatActivity {

    private int totalMark; // Total marks obtained from the previous and current activities
    Button btn;
    private String URL = "http://10.0.2.2/PHP/samplequestions.php";
    // private RadioButton lastSelectedRadioButton; // Keep track of the last selected RadioButton

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q9);
        Intent intent = getIntent();
        String patientId = intent.getStringExtra("patient_id");
        int previousTotalMark = intent.getIntExtra("Marks", 0);
        totalMark = previousTotalMark; // Assign the previous total mark to totalMark
        initializeActivity(patientId,previousTotalMark);
    }

    // Find the RadioButtons by their IDs and set their onClickListeners
    private void initializeActivity(final String patientId,final int previousTotalMark) {
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
                        Toast.makeText(q9.this, "Please select an option", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                    String q9Value = (selectedRadioButton.getId() == R.id.tr) ? "Yes" : "No";

                    // Intent to move to the next activity
                    int updatedTotalMark = totalMark + previousTotalMark;
                    Intent intent = new Intent(q9.this, q10.class); // Replace NextActivity with your actual next activity
                    intent.putExtra("patient_id", patientId);
                    intent.putExtra("Marks", updatedTotalMark);
                    Log.d("marks", "marks: " + updatedTotalMark);
                    startActivity(intent);

                    // Send data to the server
                    sendDataToServer(patientId, q9Value);
                    sendToDatabase(updatedTotalMark);

                } else {
                    Toast.makeText(q9.this, "Please Select an Option", Toast.LENGTH_SHORT).show();
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

    // Method to make an HTTP request to send data to the database using Volley
    private void sendToDatabase(final int marks) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the server response (if needed)
                        Toast.makeText(q9.this, "Data sent to the database", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors
                        Toast.makeText(q9.this, "Error sending data to the database", Toast.LENGTH_SHORT).show();
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

    // Method to send data to the server
    private void sendDataToServer(final String patientId, final String q9Value) {
        String url = "http://10.0.2.2/Php/updatescreening2.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the server response (if needed)
                        Toast.makeText(q9.this, "Data sent to the database", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors
                        Toast.makeText(q9.this, "Error sending data to the database", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("patient_id", patientId);
                params.put("q9", q9Value);
                return params;
            }
        };

        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
