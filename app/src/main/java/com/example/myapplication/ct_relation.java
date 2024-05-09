package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ct_relation extends AppCompatActivity {

    EditText idEditText;
    Spinner relationSpinner;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ct_relation);

        idEditText = findViewById(R.id.id);
        relationSpinner = findViewById(R.id.relation_spinner);
        nextButton = findViewById(R.id.btn);

        // Populate the spinner with the provided options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.relations_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relationSpinner.setAdapter(adapter);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredId = idEditText.getText().toString().trim();
                String selectedRelation = relationSpinner.getSelectedItem().toString().trim();
                // Call insertDataToServer method to send data to the table
                insertDataToServer(enteredId, selectedRelation);
                // Optionally, you can also reset the total count here if needed
                resetTotalCountAndInsertData();
                screeningInsertion(enteredId);
                screening2Insertion(enteredId);
                // If successful, navigate to CareTakerDashBoard activity
                Intent intent = new Intent(ct_relation.this, CareTakerDashBoard.class);
                intent.putExtra("patient_id", enteredId); // Pass patient ID to the next activity
                startActivity(intent);
            }
        });
    }

    private void resetTotalCountAndInsertData() {
        String url = "http://10.0.2.2/Php/resetcount.php"; // Update with your PHP script URL
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from your PHP script
                        // This response will contain the output echoed by your PHP script
                        // You can parse this response if needed and perform any further actions
                        Toast.makeText(ct_relation.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ct_relation.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(stringRequest);
    }
    private void screeningInsertion(String enteredId) {
        String url = "http://10.0.2.2/Php/screening.php"; // Update with your PHP script URL
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from your PHP script
                        // This response will contain the output echoed by your PHP script
                        // You can parse this response if needed and perform any further actions
                        Toast.makeText(ct_relation.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ct_relation.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("patient_id", enteredId);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
    private void screening2Insertion(String enteredId) {
        String url = "http://10.0.2.2/Php/screening2.php"; // Update with your PHP script URL
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from your PHP script
                        // This response will contain the output echoed by your PHP script
                        // You can parse this response if needed and perform any further actions
                        Toast.makeText(ct_relation.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ct_relation.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("patient_id", enteredId);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void insertDataToServer(final String enteredId, final String enteredRelation) {
        String url = "http://10.0.2.2/Php/relation.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from PHP script
                        Toast.makeText(ct_relation.this, response, Toast.LENGTH_SHORT).show();
                        // Here you can handle success response, such as showing a success message or navigating to another activity
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ct_relation.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("patient_id", enteredId);
                params.put("relation", enteredRelation);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
