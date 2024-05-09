package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class notifications extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<bookingInfo> dataList;
    private CustomAdapter adapter;
    String user_id ;
    BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        recyclerView = findViewById(R.id.recyclerView_notification);

        dataList = new ArrayList<>();
        adapter = new CustomAdapter(dataList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setBackgroundColor(getResources().getColor(android.R.color.white));
        recyclerView.setAdapter(adapter);

        new FetchCategoryDetailsTask().execute();
    }

    class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private List<bookingInfo> dataList;

        public CustomAdapter(List<bookingInfo> dataList) {
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.notification_cardview, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            bookingInfo booking = dataList.get(position);

            if (holder.nameTextView != null) {
                holder.nameTextView.setText(booking.getMessage() != null ? booking.getMessage() : "");
            }

            // Set OnClickListener for the itemView

        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView nameTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                nameTextView = itemView.findViewById(R.id.textFetch);

            }
        }
    }

    private class FetchCategoryDetailsTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://10.0.2.2/PHP/retrivemessage.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");  // Use POST method
                urlConnection.setDoOutput(true);  // Enable output for POST request
                // No parameters sent, so no need to write anything to the output stream

                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line).append("\n");
                }

                bufferedReader.close();
                inputStream.close();
                urlConnection.disconnect();

                return response.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("Response", "Result from server: " + result);
            dataList.clear();
            // Pass the response to handleCategoryDetailsResponse
            handleCategoryDetailsResponse(result);
        }
    }


    private void handleCategoryDetailsResponse(String response) {
        if (response != null) {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                String status = jsonResponse.optString("status", "");

                if ("success".equals(status)) {
                    JSONArray messagesArray = jsonResponse.optJSONArray("messages");

                    if (messagesArray != null) {
                        for (int i = 0; i < messagesArray.length(); i++) {
                            String message = messagesArray.optString(i);

                            if (message == null) {
                                continue;
                            }

                            // Create a bookingInfo object with only the message field
                            dataList.add(new bookingInfo(message));
                        }

                        adapter.notifyDataSetChanged();
                    }
                } else if ("NoData".equals(status)) {
                    // Handle case when no messages are available
                    Log.e("Response", "No messages available");
                } else {
                    // Handle other error cases
                    Log.e("Response", "Error: " + status);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                // Handle JSON parsing error
                Log.e("Response", "JSON parsing error");
            }
        } else {
            // Handle case when response is null
            Log.e("Response", "Response is null");
        }
    }

    static class bookingInfo {
        private String message;

        public bookingInfo(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
