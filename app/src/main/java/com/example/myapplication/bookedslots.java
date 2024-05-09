package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class bookedslots extends AppCompatActivity {

    private LinearLayout appointmentsLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookedslots);

        appointmentsLayout = findViewById(R.id.appointmentsLayout);

        // Get patient_id from intent
        String patient_id = getIntent().getStringExtra("patient_id");
        Log.d("tag333", "Patient ID: " + patient_id);

        // Make an HTTP request to your PHP script
        if (patient_id != null) {
            fetchAppointmentsFromPHP(patient_id);
        } else {
            Log.e("tag333", "Invalid patient ID");
        }
    }

    private void fetchAppointmentsFromPHP(String patient_id) {
        String url = "http://10.0.2.2/PHP/bookedslots.php";

        // Create a JSON object to send data to the PHP script
        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("pid", patient_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleAppointmentsResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("tag333", "Error: " + error.getMessage());
                    }
                }) {
            @Override
            public byte[] getBody() {
                return jsonData.toString().getBytes();
            }
        };

        requestQueue.add(stringRequest);
    }

    private void handleAppointmentsResponse(String response) {
        try {
            // Clear the existing views in the appointmentsLayout
            appointmentsLayout.removeAllViews();

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response, JsonObject.class);

            if (jsonObject.has("data")) {
                JsonArray appointmentsArray = jsonObject.getAsJsonArray("data");
                if (appointmentsArray.size() > 0) {
                    for (int i = 0; i < appointmentsArray.size(); i++) {
                        JsonObject appointmentObject = appointmentsArray.get(i).getAsJsonObject();
                        String pid = appointmentObject.get("patient_id").getAsString();
                        String name = appointmentObject.get("name").getAsString();
                        String date = appointmentObject.get("date").getAsString();
                        String status = appointmentObject.get("status").getAsString();

                        Log.d("tag333", "Appointment " + i + ": PID=" + pid + ", Name=" + name + ", Date=" + date + ", Status=" + status);

                        // Inflate a new CardView for each appointment
                        CardView cardView = new CardView(this);
                        cardView.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        ));
                        cardView.setCardElevation(getResources().getDimension(R.dimen.card_elevation));
                        cardView.setUseCompatPadding(true);

                        // Inflate layout for each appointment
                        LinearLayout linearLayout = new LinearLayout(this);
                        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        ));
                        linearLayout.setOrientation(LinearLayout.VERTICAL);
                        linearLayout.setPadding(
                                getResources().getDimensionPixelSize(R.dimen.card_padding),
                                getResources().getDimensionPixelSize(R.dimen.card_padding),
                                getResources().getDimensionPixelSize(R.dimen.card_padding),
                                getResources().getDimensionPixelSize(R.dimen.card_padding)
                        );

                        // Populate appointment details in TextViews
                        TextView pidTextView = new TextView(this);
                        pidTextView.setText("PID          : " + pid);
                        pidTextView.setTextColor(getResources().getColor(R.color.black));
                        linearLayout.addView(pidTextView);

                        TextView nameTextView = new TextView(this);
                        nameTextView.setText("Name      : " + name);
                        nameTextView.setTextColor(getResources().getColor(R.color.black));
                        linearLayout.addView(nameTextView);

                        TextView dateTextView = new TextView(this);
                        dateTextView.setText("Date        : " + date);
                        dateTextView.setTextColor(getResources().getColor(R.color.black));
                        linearLayout.addView(dateTextView);

                        TextView statusTextView = new TextView(this);
                        statusTextView.setText("Status    : " + status);
                        linearLayout.addView(statusTextView);

                        // Add the layout to the CardView
                        cardView.addView(linearLayout);

                        // Add the CardView to the main layout
                        appointmentsLayout.addView(cardView);
                    }
                } else {
                    Log.e("tag333", "No appointments found");
                }
            } else if (jsonObject.has("message")) {
                String errorMessage = jsonObject.get("message").getAsString();
                Log.e("tag333", "Error message: " + errorMessage);
            } else {
                Log.e("tag333", "Invalid JSON response");
            }
        } catch (Exception e) {
            Log.e("tag333", "Error: " + e.getMessage());
        }
    }
}
