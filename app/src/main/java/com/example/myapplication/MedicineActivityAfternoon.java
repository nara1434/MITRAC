package com.example.myapplication;

//import static com.example.myapplication.q1.saveDataUrl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MedicineActivityAfternoon extends AppCompatActivity {
    TextView m, d, t;
    private RadioButton trueRadioButton;
    private RadioButton falseRadioButton;
    private Button saveButton;

    // Replace this URL with your server URL
    private static final String fetchDataUrl = "http://10.0.2.2/PHP/get.php";
    private static final String saveDataUrl = "http://10.0.2.2/PHP/medicine_monitoring.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_afternoon);

        String id = getIntent().getStringExtra("id");
        String type = getIntent().getStringExtra("Type");

        m = findViewById(R.id.m);
        d = findViewById(R.id.d);
        t = findViewById(R.id.t);

        fetchData(id, type);

        trueRadioButton = findViewById(R.id.tr);
        falseRadioButton = findViewById(R.id.fl);
        saveButton = findViewById(R.id.save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean selectedValue = trueRadioButton.isChecked(); // or falseRadioButton.isChecked() depending on your logic
                // Toast.makeText(.this,"saved",Toast.LENGTH_SHORT).show();
                // Send data to server, including SSL bypass
                sendDataToServer(selectedValue);
            }
        });
    }

    private void fetchData(final String id, final String type) {
        StringRequest request = new StringRequest(Request.Method.POST, fetchDataUrl,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.d("tag", response);
                        m.setText(jsonObject.optString("Medicine_name"));
                        d.setText(jsonObject.optString("Dose"));
                        t.setText(jsonObject.optString("Type"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("Volley Error", "Error fetching data: " + error.toString());
                    Toast.makeText(getApplicationContext(), "Error fetching data: " + error.toString(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", "1234");
                params.put("Type", "afternoon");
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private void sendDataToServer(boolean selectedValue) {
        // Bypass SSL
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            };

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create a new request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Build the JSON data to be sent
        JSONObject postData = new JSONObject();
        try {
            postData.put("submit", "true");
            postData.put("q1", selectedValue ? 1 : 0);
            postData.put("q2", selectedValue ? 0 : 1);// Send integer values directly
            // Convert boolean to 1 or 0
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create the request
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                saveDataUrl,
                postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response if needed
                        try {
                            String message = response.getString("message");
                            Log.d("Volley Response", message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log the error for debugging
                        Log.e("Volley Error", error.toString());
                        // Handle the error
                    }
                }
        );

        // Add the request to the queue
        requestQueue.add(request);
    }
}
