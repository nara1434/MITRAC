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

public class updatePatientProfile extends AppCompatActivity {
    EditText editName, editAge, editSex, editMobile, editEducation, editAddress, editMaritalStatus, editDiseaseStatus, editDuration;
    Button saveButton;

    // Replace this URL with your server URL
    private static final String fetchDataUrl = "http://10.0.2.2/PHP/viewpatient.php";
    private static final String updateDataUrl = "http://10.0.2.2/PHP/update_patient_profile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_patient_profile);

        String patient_id = getIntent().getStringExtra("patient_id");

        editName = findViewById(R.id.name);
        editAge = findViewById(R.id.age);
        editSex = findViewById(R.id.sex);
        editMobile = findViewById(R.id.mobile_number);
        editEducation = findViewById(R.id.education);
        editAddress = findViewById(R.id.address);
        editMaritalStatus = findViewById(R.id.marital_status);
        editDiseaseStatus = findViewById(R.id.disease_status);
        editDuration = findViewById(R.id.duration);

        saveButton = findViewById(R.id.addpa);

        fetchData(patient_id);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call method to handle save functionality
                updateData(patient_id);
                Intent intent = new Intent(updatePatientProfile.this, PatientProfile.class);
                intent.putExtra("patient_id", patient_id);
                startActivity(intent);
            }
        });
    }

    private void fetchData(final String patient_id) {
        StringRequest request = new StringRequest(Request.Method.POST, fetchDataUrl,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        editName.setText(jsonObject.optString("name"));
                        editAge.setText(jsonObject.optString("age"));
                        editSex.setText(jsonObject.optString("sex"));
                        editMobile.setText(jsonObject.optString("mobile_number"));
                        editEducation.setText(jsonObject.optString("education"));
                        editAddress.setText(jsonObject.optString("address"));
                        editMaritalStatus.setText(jsonObject.optString("marital_status"));
                        editDiseaseStatus.setText(jsonObject.optString("disease_status"));
                        editDuration.setText(jsonObject.optString("duration"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getApplicationContext(), "Error fetching data: " + error.toString(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("patient_id", patient_id);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private void updateData(final String patient_id) {
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
                Map<String, String> params = new HashMap<>();
                params.put("patient_id", patient_id);
                params.put("name", editName.getText().toString());
                params.put("age", editAge.getText().toString());
                params.put("sex", editSex.getText().toString());
                params.put("mobile_number", editMobile.getText().toString());
                params.put("education", editEducation.getText().toString());
                params.put("address", editAddress.getText().toString());
                params.put("marital_status", editMaritalStatus.getText().toString());
                params.put("disease_status", editDiseaseStatus.getText().toString());
                params.put("duration", editDuration.getText().toString());
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}
