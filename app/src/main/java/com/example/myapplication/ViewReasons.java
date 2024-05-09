package com.example.myapplication;

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

import java.util.HashMap;
import java.util.Map;

public class ViewReasons extends AppCompatActivity {
    TextView t1;
    //String patient_id="1434";

    // Replace this URL with your server URL
    private static final String fetchDataUrl = "http://10.0.2.2/PHP/viewreasons.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reasons);

        final String patientId = getIntent().getStringExtra("patient_id");
        //Log.d("ViewReasons",patient_id );

        //Toast.makeText(getApplicationContext(), "patientId is " + patientId, Toast.LENGTH_SHORT).show();

        t1 = findViewById(R.id.note);

        fetchData(patientId);
    }

    private void fetchData(final String patientId) {
        StringRequest request = new StringRequest(Request.Method.POST, fetchDataUrl,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        t1.setText(jsonObject.optString("reasons"));
                        //t2.setText(jsonObject.optString("time1"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getApplicationContext(), "Error fetching data: " + error.toString(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                // Check if patientId is not null before adding to params
                if (patientId != null) {
                    params.put("patient_id", patientId);
                } else {
                    // Handle the case where patientId is null (optional, depending on your logic)
                    Log.e("ViewReasons", "Patient ID is null");
                }

                return params;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}
