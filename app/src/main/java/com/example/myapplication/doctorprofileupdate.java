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

public class doctorprofileupdate extends AppCompatActivity {
    EditText t2, t3, t4, t5, t6, t7, t8;
    Button update;

    // Replace these URLs with your server URLs
    private static final String fetchDataUrl = "http://10.0.2.2/PHP/d_profiledisplay.php";
    private static final String updateDataUrl = "http://10.0.2.2/PHP/d_profileupdate.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorprofileupdate);
        String d_id = getIntent().getStringExtra("d_id");

        t2 = findViewById(R.id.name);
        t3 = findViewById(R.id.dis);
        t4 = findViewById(R.id.sex);
        t5 = findViewById(R.id.mn);
        t6 = findViewById(R.id.spec);
        t7 = findViewById(R.id.addr);
        t8 = findViewById(R.id.ms);

        update = findViewById(R.id.save);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData(d_id);
                Intent intent = new Intent(doctorprofileupdate.this, doctorprofiledisplay.class);
                intent.putExtra("d_id", d_id);
                startActivity(intent);
            }
        });

        // Fetch data when the activity is created
        fetchData(d_id); // Pass the actual doctor ID here
    }

    private void fetchData(final String doctor_id) {
        StringRequest request = new StringRequest(Request.Method.POST, fetchDataUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            t2.setText(jsonObject.optString("name"));
                            t3.setText(jsonObject.optString("designation"));
                            t4.setText(jsonObject.optString("sex"));
                            t5.setText(jsonObject.optString("mobile_number"));
                            t6.setText(jsonObject.optString("specialization"));
                            t7.setText(jsonObject.optString("address"));
                            t8.setText(jsonObject.optString("marital_status"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error fetching data: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("d_id", doctor_id);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private void updateData(final String doctor_id) {
        final String name = t2.getText().toString().trim();
        final String dis = t3.getText().toString().trim();
        final String sex = t4.getText().toString().trim();
        final String mn = t5.getText().toString().trim();
        final String spec = t6.getText().toString().trim();
        final String addr = t7.getText().toString().trim();
        final String ms = t8.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, updateDataUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                t2.setText("");
                t3.setText("");
                t4.setText("");
                t5.setText("");
                t6.setText("");
                t7.setText("");
                t8.setText("");
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                // Intent to navigate to the desired activity
                // Replace DoctorHomePage.class with the actual activity class
                Intent intent = new Intent(doctorprofileupdate.this, DoctorHomePage.class);
                intent.putExtra("d_id", doctor_id);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error updating data: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("d_id", doctor_id);
                param.put("name", name);
                param.put("designation", dis);
                param.put("sex", sex);
                param.put("mobile_number", mn);
                param.put("specialization", spec);
                param.put("address", addr);
                param.put("marital_status", ms);
                return param;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}
