package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class addPatient extends AppCompatActivity {
    EditText pid, name, phno, sex, age, edu, ms, ds, dur, addr;
    String pid1, name1, phno1, sex1, age1, edu1, ms1, ds1, dur1, addr1;

    Button save;
    ImageView profile;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        pid = findViewById(R.id.patient_id);
        name = findViewById(R.id.name);
        phno = findViewById(R.id.mobile_number);
        age = findViewById(R.id.age);
        sex = findViewById(R.id.sex);
        edu = findViewById(R.id.education);
        ms = findViewById(R.id.marital_status);
        ds = findViewById(R.id.disease_status);
        dur = findViewById(R.id.duration);
        addr = findViewById(R.id.address);
        profile = findViewById(R.id.img);
        save = findViewById(R.id.addpa);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialog();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToDatabase();
                Intent intent = new Intent(addPatient.this, DoctorHomePage.class);
                startActivity(intent);
            }
        });
    }

    private void sendDataToDatabase() {
        showProgressBar();

        pid1 = pid.getText().toString().trim();
        name1 = name.getText().toString().trim();
        phno1 = phno.getText().toString().trim();
        age1 = age.getText().toString().trim();
        sex1 = sex.getText().toString().trim();
        edu1 = edu.getText().toString().trim();
        ms1 = ms.getText().toString().trim();
        ds1 = ds.getText().toString().trim();
        dur1 = dur.getText().toString().trim();
        addr1 = addr.getText().toString().trim();

        // Convert Bitmap to Base64
        String profileBase64 = "";
        if (profile.getDrawable() != null) {
            if (profile.getDrawable() instanceof BitmapDrawable) {
                Bitmap profileBitmap = ((BitmapDrawable) profile.getDrawable()).getBitmap();
                profileBase64 = convertBitmapToBase64(profileBitmap);
            } else if (profile.getDrawable() instanceof VectorDrawable) {
                Bitmap profileBitmap = getBitmapFromVectorDrawable((VectorDrawable) profile.getDrawable());
                profileBase64 = convertBitmapToBase64(profileBitmap);
            }
        }

        Log.d("TAG", "Sending data to the server");

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2/Php/add_patient.php";

        try {
            JSONObject jsonData = new JSONObject();
            jsonData.put("patient_id", pid1);
            jsonData.put("name", name1);
            jsonData.put("sex", sex1);
            jsonData.put("age", age1);
            jsonData.put("education", edu1);
            jsonData.put("marital_status", ms1);
            jsonData.put("mobile_number", phno1);
            jsonData.put("disease_status", ds1);
            jsonData.put("address", addr1);
            jsonData.put("duration", dur1);
            jsonData.put("profile_pic", profileBase64);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonData,
                    response -> {
                        try {
                            Log.d("TAG", "Server response: " + response.toString());
                            String status = response.getString("status");

                            if ("success".equals(status)) {
                                runOnUiThread(() -> {
                                    Toast.makeText(addPatient.this, "Details updated successfully", Toast.LENGTH_SHORT).show();
                                });
                            } else {

                                runOnUiThread(() -> {
                                    Toast.makeText(addPatient.this, "Failed to update details", Toast.LENGTH_SHORT).show();
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            hideProgressBar();
                        }
                    },
                    error -> {
                        Log.e("TAG", "Volley Error: " + error.getMessage());
                        runOnUiThread(() -> {
                            Toast.makeText(addPatient.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                        hideProgressBar();
                    }
            );

            queue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
            hideProgressBar();
        }
    }

    private Bitmap getBitmapFromVectorDrawable(VectorDrawable vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888
        );
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    private void showProgressBar() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                },
                1000
        );
    }

    private void hideProgressBar() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void showImageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image Source");
        builder.setItems(new CharSequence[]{"Camera", "Gallery"}, (dialog, which) -> {
            if (which == 0) {
                dispatchTakePictureIntent();
            } else if (which == 1) {
                pickImageFromGallery();
            }
        });
        builder.show();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageCaptureLauncher.launch(takePictureIntent);
    }

    private void pickImageFromGallery() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imageGalleryLauncher.launch(pickIntent);
    }

    ActivityResultLauncher<Intent> imageCaptureLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        profile.setImageBitmap(imageBitmap);
                    }
                }
            }
    );

    ActivityResultLauncher<Intent> imageGalleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        if (result.getData() != null) {
                            try {
                                Uri selectedImageUri = result.getData().getData();
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                                        addPatient.this.getContentResolver(),
                                        selectedImageUri
                                );
                                profile.setImageBitmap(bitmap);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
    );

    private String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
