package com.example.myapplication;

import android.content.Context;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class sq2 extends AppCompatActivity {

    private static final String TAG = "Q2";
    private RadioGroup radioGroup;
    private Button saveButton;
    private RequestQueue requestQueue;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q2);

        context = this;
        requestQueue = Volley.newRequestQueue(context);

        // Retrieve patient ID from the Intent
        String patientId = getIntent().getStringExtra("patient_id");
        Log.d(TAG, "patient id "+patientId);

        radioGroup = findViewById(R.id.radioGroup);
        saveButton = findViewById(R.id.save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

                if (selectedRadioButtonId == -1) {
                    Toast.makeText(sq2.this, "Please select an option", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                String q2Value;
                if (selectedRadioButton.getId() == R.id.tr) {
                    q2Value = "Yes";
                } else {
                    q2Value = "No";
                }

                // Use the patient ID retrieved from the Intent
                sendDataToServer(patientId, q2Value);
                Intent intent = new Intent(sq2.this, sq3.class);
                intent.putExtra("patient_id", patientId);
                startActivity(intent);
            }
        });
    }

    private void sendDataToServer(String patientId, String q2Value) {
        String url = "http://10.0.2.2/Php/updatescreening.php";
        Log.d(TAG, "Value of q2: " + q2Value);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response: " + response);
                        // Handle response from the server
                        // You can parse the JSON response here if any
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                // Handle error
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("patient_id", patientId);
                params.put("q2", q2Value);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
