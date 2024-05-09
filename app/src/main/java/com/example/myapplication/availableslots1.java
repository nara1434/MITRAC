package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class availableslots1 extends AppCompatActivity {
    private EditText Epid,Ename;
    Button btn;

    private String pid,name;
    private String URL = "http://10.0.2.2/PHP/bookings.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availableslots1);
        Intent intent = getIntent();
        String date = intent.getStringExtra("key");
        Epid = findViewById(R.id.pid1);
        Ename= findViewById(R.id.name1);
        Log.d("tag1", String.valueOf(Epid));
        Log.d("tag1", String.valueOf(Ename));
        btn = findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = getIntent();
               String value = intent1.getStringExtra("patient_id");
                //String value = "id";
                pid =value;
                pid = Epid.getText().toString();
                name = Ename.getText().toString();
                Log.d("tag1",pid);
                Log.d("tag1", name);
                if (!pid.isEmpty() && !name.isEmpty()) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Handle the response

                                    handleResponse(response);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            handleError(error);
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            // Send the username and password as POST parameters
                            Map<String, String> data = new HashMap<>();
                            data.put("pid",pid);
                            data.put("name", name);
                            data.put("date",date);
                            return data;
                        }
                    };

                    // Customize the retry policy
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    // Initialize the Volley request queue and add the request
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                } else {
                    Toast.makeText(availableslots1.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void handleResponse(String response) {
        Gson gson = new Gson();
        Log.d("JSON Response", response);
        JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
        String status = jsonObject.get("status").getAsString();
        Log.d("JSON Response", status);

        if ("success".equals(status)) {
            Toast.makeText(this, "Successfully submitted", Toast.LENGTH_SHORT).show();
            Intent intent2 = new Intent(availableslots1.this,calender.class);
            startActivity(intent2);

        } else if ("failure".equals(status)) {
            Toast.makeText(this, "not submitted", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleError(VolleyError error) {
        if (error instanceof TimeoutError) {
            Toast.makeText(this, "Request timed out. Check your internet connection.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, error.toString().trim(), Toast.LENGTH_SHORT).show();
        }
    }

}