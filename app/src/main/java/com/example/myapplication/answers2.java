package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class answers2 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<DataModel> dataList;
    private String patientId; // Variable to store patient ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers2);

        // Retrieve patient ID passed from the previous activity
        patientId = getIntent().getStringExtra("patient_id");
        Log.d("tag1", "patient id "+patientId);

        recyclerView = findViewById(R.id.ans_recycler_view2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataList = new ArrayList<>();

        // Instantiate the adapter
        adapter = new MyAdapter(dataList);

        // Set the adapter to the RecyclerView
        recyclerView.setAdapter(adapter);

        // Now, fetch data and add it to the dataList

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2/Php/ans2.php";

        // Create a JSON object to send the patient ID
        Log.d("PatientID", "Patient ID: " + patientId);
        JSONObject jsonObject = new JSONObject();
        if (patientId != null && !patientId.isEmpty()) {
            try {
                jsonObject.put("patient_id", patientId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("PatientID", "Patient ID is null or empty");
        }

        // Request a JSON response from the provided URL with the patient ID
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("Response", "Response received: " + response.toString());
                            boolean success = response.getBoolean("success");
                            if (success) {
                                JSONArray dataArray = response.getJSONArray("data");
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject dataObject = dataArray.getJSONObject(i);
                                    String patientId = dataObject.getString("patient_id");
                                    String q1 = dataObject.getString("q1");
                                    String q2 = dataObject.getString("q2");
                                    String q3 = dataObject.getString("q3");
                                    String q4 = dataObject.getString("q4");
                                    String q5 = dataObject.getString("q5");
                                    String q6 = dataObject.getString("q6");
                                    String q7 = dataObject.getString("q7");
                                    String q8 = dataObject.getString("q8");
                                    String q9 = dataObject.getString("q9");
                                    String q10 = dataObject.getString("q10");
                                    Log.d("DataResponse", "Data received for patientId: " + patientId);
                                    Log.d("DataResponse", "q1: " + q1);
                                    Log.d("DataResponse", "q2: " + q2);
                                    Log.d("DataResponse", "q3: " + q3);
                                    Log.d("DataResponse", "q4: " + q4);
                                    Log.d("DataResponse", "q5: " + q5);
                                    Log.d("DataResponse", "q6: " + q6);
                                    Log.d("DataResponse", "q7: " + q7);
                                    Log.d("DataResponse", "q8: " + q8);
                                    DataModel data = new DataModel(patientId, q1, q2, q3, q4, q5, q6, q7, q8,q9,q10);
                                    dataList.add(data);
                                }
                                // Notify the adapter that the data has changed
                                adapter.notifyDataSetChanged();
                            } else {
                                // Handle unsuccessful response
                                Log.d("Response", "Unsuccessful response received");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Handle JSON parsing error
                            Log.e("Response", "Error parsing JSON: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                Log.e("Response", "Error: " + error.toString());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    public static class DataModel {
        private String patientId;
        private String q1;
        private String q2;
        private String q3;
        private String q4;
        private String q5;
        private String q6;
        private String q7;
        private String q8;
        private String q9;
        private String q10;


        public DataModel(String patientId, String q1, String q2, String q3, String q4, String q5, String q6, String q7, String q8, String q9, String q10) {
            this.patientId = patientId;
            this.q1 = q1;
            this.q2 = q2;
            this.q3 = q3;
            this.q4 = q4;
            this.q5 = q5;
            this.q6 = q6;
            this.q7 = q7;
            this.q8 = q8;
            this.q9 = q9;
            this.q10 = q10;
        }

        public String getPatientId() {
            return patientId;
        }

        public String getQ1() {
            return q1;
        }

        public String getQ2() {
            return q2;
        }

        public String getQ3() {
            return q3;
        }

        public String getQ4() {
            return q4;
        }

        public String getQ5() {
            return q5;
        }

        public String getQ6() {
            return q6;
        }

        public String getQ7() {
            return q7;
        }

        public String getQ8() {
            return q8;
        }
        public String getQ9() {
            return q9;
        }
        public String getQ10() {
            return q10;
        }
    }

    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private List<DataModel> dataList;

        public MyAdapter(List<DataModel> dataList) {
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Inflate the item layout for the answers activity
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer2cardview, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            // Bind data to the views
            DataModel data = dataList.get(position);
            holder.patientIdTextView.setText(data.getPatientId());
            holder.q1TextView.setText(data.getQ1());
            holder.q2TextView.setText(data.getQ2());
            holder.q3TextView.setText(data.getQ3());
            holder.q4TextView.setText(data.getQ4());
            holder.q5TextView.setText(data.getQ5());
            holder.q6TextView.setText(data.getQ6());
            holder.q7TextView.setText(data.getQ7());
            holder.q8TextView.setText(data.getQ8());
            holder.q9TextView.setText(data.getQ9());
            holder.q10TextView.setText(data.getQ10());
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            // Views within each item
            public TextView patientIdTextView, q1TextView, q2TextView, q3TextView, q4TextView, q5TextView, q6TextView, q7TextView, q8TextView, q9TextView, q10TextView;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                // Initialize views
                patientIdTextView = itemView.findViewById(R.id.id1);
                q1TextView = itemView.findViewById(R.id.q1);
                q2TextView = itemView.findViewById(R.id.q2);
                q3TextView = itemView.findViewById(R.id.q3);
                q4TextView = itemView.findViewById(R.id.q4);
                q5TextView = itemView.findViewById(R.id.q5);
                q6TextView = itemView.findViewById(R.id.q6);
                q7TextView = itemView.findViewById(R.id.q7);
                q8TextView = itemView.findViewById(R.id.q8);
                q9TextView = itemView.findViewById(R.id.q9);
                q10TextView = itemView.findViewById(R.id.q10);
            }
        }
    }
}
