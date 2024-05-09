package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class caretakerprofileupdate extends AppCompatActivity {
    EditText editName, editAge, editSex, editMobile, editQualification, editAddress, editMaritalStatus;
    Button saveButton;

    // Replace this URL with your server URL
    private static final String fetchDataUrl = "http://10.0.2.2/PHP/ct_profiledisplay.php";
    private static final String updateDataUrl = "http://10.0.2.2/PHP/ct_profileupdate.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caretakerprofileupdate);

        String c_id = getIntent().getStringExtra("c_id");
        Toast.makeText(getApplicationContext(), "c_id is" + c_id, Toast.LENGTH_SHORT).show();

        editName = findViewById(R.id.name);
        editAge = findViewById(R.id.age);
        editSex = findViewById(R.id.sex);
        editMobile = findViewById(R.id.mn);
        editQualification = findViewById(R.id.qua);
        editAddress = findViewById(R.id.addr);
        editMaritalStatus = findViewById(R.id.ms);

        saveButton = findViewById(R.id.save);

        fetchData(c_id);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call method to handle save functionality
                updateData(c_id);
                Intent intent = new Intent(caretakerprofileupdate.this, caretakerprofiledispaly.class);
                intent.putExtra("c_id", c_id);
                startActivity(intent);
            }
        });
    }

    private void fetchData(final String c_id) {
        StringRequest request = new StringRequest(Request.Method.POST, fetchDataUrl,
                response -> {
                    try {
                        // Parse the JSON response
                        JSONObject jsonObject = new JSONObject(response);

                        // Set the received data to corresponding EditText fields
                        editName.setText(jsonObject.optString("name"));
                        editAge.setText(jsonObject.optString("age"));
                        editSex.setText(jsonObject.optString("sex"));
                        editMobile.setText(jsonObject.optString("mobile_number"));
                        editQualification.setText(jsonObject.optString("qualification"));
                        editAddress.setText(jsonObject.optString("address"));
                        editMaritalStatus.setText(jsonObject.optString("marital_status"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getApplicationContext(), "Error fetching data: " + error.toString(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                // Set the parameters for the POST request
                Map<String, String> params = new HashMap<>();
                params.put("c_id", c_id);
                return params;
            }
        };

        // Add the request to the request queue
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private void updateData(final String c_id) {
        StringRequest request = new StringRequest(Request.Method.POST, updateDataUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response after updating data
                        Toast.makeText(getApplicationContext(), "Data updated successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Close the activity after saving
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error updating data: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Set the parameters for the POST request
                Map<String, String> params = new HashMap<>();
                params.put("c_id", c_id);
                params.put("name", editName.getText().toString());
                params.put("age", editAge.getText().toString());
                params.put("sex", editSex.getText().toString());
                params.put("mobile_number", editMobile.getText().toString());
                params.put("qualification", editQualification.getText().toString());
                params.put("address", editAddress.getText().toString());
                params.put("marital_status", editMaritalStatus.getText().toString());
                // Add other parameters as needed
                return params;
            }
        };

        // Add the request to the request queue
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}
