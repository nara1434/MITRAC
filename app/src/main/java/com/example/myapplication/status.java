package com.example.myapplication;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class status extends AppCompatActivity {
    private Button pendingButton;
    private Button approvedButton;

    private ArrayList<ItemData> data = new ArrayList<>();
    private CustomArrayAdapter adapter;
    private ListView lv;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        pendingButton = findViewById(R.id.nl_btn1);
        approvedButton = findViewById(R.id.nl_btn2);
        lv =findViewById(R.id.li1);
        adapter = new CustomArrayAdapter(data);
        lv.setAdapter(adapter);



        pendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change color when Pending button is clicked
                int color = Color.parseColor("#62CECE");
                pendingButton.setBackgroundColor(color);
                approvedButton.setBackgroundColor(Color.GRAY );
                lv.setVisibility(View.VISIBLE);
                adapter = new CustomArrayAdapter(data);
                lv.setAdapter(adapter);
                flag =0;

                fetchStringFromPHP();

            }
        });

        approvedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change color when Approved button is clicked
                int color = Color.parseColor("#62CECE");
                approvedButton.setBackgroundColor(color);
                pendingButton.setBackgroundColor(Color.GRAY);

                flag = 1;
                adapter = new CustomArrayAdapter(data);
                lv.setAdapter(adapter);

                fetchStringFromPHP1();

            }
        });

    }
    private void fetchStringFromPHP1() {
        // Replace with your PHP script's URL
        String URL = "http://10.0.2.2/PHP/printBooked1.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JsonArray jsonArray = JsonParser.parseString(response).getAsJsonArray();
                    data.clear();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                        String pid = jsonObject.get("patient_id").getAsString();
                        String name = jsonObject.get("name").getAsString();
                        String date = jsonObject.get("date").getAsString();
                        String status = jsonObject.get("status").getAsString();

                        // Add the item data to the list
                        data.add(new ItemData(pid, name, date, status));
                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle any errors here
                error.printStackTrace();
            }
        });

        requestQueue.add(stringRequest);
    }
    private void fetchStringFromPHP() {
        // Replace with your PHP script's URL
        String URL = "http://10.0.2.2/PHP/printBooked.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JsonArray jsonArray = JsonParser.parseString(response).getAsJsonArray();
                    data.clear();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                        String pid = jsonObject.get("patient_id").getAsString();
                        String name = jsonObject.get("name").getAsString();
                        String date = jsonObject.get("date").getAsString();
                        String status = jsonObject.get("status").getAsString();

                        // Add the item data to the list
                        data.add(new ItemData(pid, name, date, status));
                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle any errors here
                error.printStackTrace();
            }
        });

        requestQueue.add(stringRequest);
    }
    private class CustomArrayAdapter extends ArrayAdapter<ItemData> {
        CustomArrayAdapter(ArrayList<ItemData> items) {
            super(status.this, 0, items);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list, parent, false);
            }

            TextView itemDetailsTextView = convertView.findViewById(R.id.itemDetailsTextView);
            Button updateButton = convertView.findViewById(R.id.updateButton);

            final ItemData item = getItem(position);
            if(flag == 1){
                updateButton.setVisibility(View.GONE);
                if (item != null) {
                    itemDetailsTextView.setText("PID: " + item.pid + "\nName: " + item.name + "\nDate: " + item.date + "\nStatus: " + item.status);
                }
            }
            else {


                if (item != null) {
                    itemDetailsTextView.setText("PID: " + item.pid + "\nName: " + item.name + "\nDate: " + item.date + "\nStatus: " + item.status);

                    // Set an onClickListener for the "Update" button
                    updateButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Handle the update operation for the selected item (item) here
                            Log.d("Update", "Updating item: " + item.pid);
                            // You can open a new activity or dialog for editing the item details
                            handleupdate(item.pid);
                        }
                    });
                }
            }


            return convertView;
        }
    }
    public void handleupdate(String pid){
        String URL = "http://10.0.2.2/PHP/updatestat.php";

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
                data.put("pid", pid);

                return data;
            }
        };

        // Customize the retry policy
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Initialize the Volley request queue and add the request
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
    private void handleResponse(String response) {
        Gson gson = new Gson();
        Log.d("JSON Response", response);
        JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
        String status = jsonObject.get("status").getAsString();
        Log.d("JSON Response", status);

        if ("success".equals(status)) {
            Toast.makeText(this, "Succesfully approved", Toast.LENGTH_SHORT).show();
            data.clear();
            fetchStringFromPHP();
        } else if ("failure".equals(status)) {
            Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle network request errors
    private void handleError(VolleyError error) {
        if (error instanceof TimeoutError) {
            Toast.makeText(this, "Request timed out. Check your internet connection.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, error.toString().trim(), Toast.LENGTH_SHORT).show();
        }
    }





    // Custom data class to hold item details
    private static class ItemData {
        String pid;
        String name;
        String date;
        String status;

        ItemData(String pid, String name, String date, String status) {
            this.pid = pid;
            this.name = name;
            this.date = date;
            this.status = status;
        }
    }

}


