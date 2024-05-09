package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class addvideos extends Activity {

    private static final int REQUEST_CODE = 100;
    private String selectedVideoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addvideos);

        Button btnSelectVideo = findViewById(R.id.btnSelectVideo);
        Button btnUploadVideo = findViewById(R.id.btnUploadVideo);

        btnSelectVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectVideoFromGallery();
            }
        });

        btnUploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedVideoPath != null) {
                    uploadVideo(selectedVideoPath);
                } else {
                    Toast.makeText(addvideos.this, "Please select a video first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void selectVideoFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedVideoUri = data.getData();
            selectedVideoPath = getPathFromUri(selectedVideoUri);

            // Add a toast message here
            Toast.makeText(addvideos.this, "Video selected: " + selectedVideoPath, Toast.LENGTH_SHORT).show();
        }
    }

    private String getPathFromUri(Uri contentUri) {
        String[] proj = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        return path;
    }

    private void uploadVideo(final String selectedPath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String serverURL = "http://10.0.2.2/PHP/addvideos.php";
                    HttpURLConnection connection = null;
                    DataOutputStream dataOutputStream = null;
                    FileInputStream fileInputStream = new FileInputStream(selectedPath);

                    URL url = new URL(serverURL);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Connection", "Keep-Alive");
                    connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                    connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=*****");
                    connection.setRequestProperty("uploaded_file", selectedPath);

                    dataOutputStream = new DataOutputStream(connection.getOutputStream());
                    dataOutputStream.writeBytes("--*****\r\n");
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                            + selectedPath + "\"" + "\r\n");
                    dataOutputStream.writeBytes("\r\n");

                    int bytesRead, bufferSize;
                    byte[] buffer;
                    int maxBufferSize = 1 * 1024 * 1024;

                    bufferSize = Math.min(fileInputStream.available(), maxBufferSize);
                    buffer = new byte[bufferSize];

                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {
                        dataOutputStream.write(buffer, 0, bufferSize);
                        bufferSize = Math.min(fileInputStream.available(), maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    }

                    dataOutputStream.writeBytes("\r\n");
                    dataOutputStream.writeBytes("--*****--\r\n");

                    int serverResponseCode = connection.getResponseCode();
                    Log.e("rr", "response" + serverResponseCode);
                    String serverResponseMessage = connection.getResponseMessage();

                    fileInputStream.close();
                    dataOutputStream.flush();
                    dataOutputStream.close();

                    if (serverResponseCode == 200) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(addvideos.this, "Video uploaded successfully", Toast.LENGTH_SHORT).show();
                                // Add any additional logic you want to perform after successful upload
                                // For example, you can navigate to another activity or refresh the UI.
                                // Here, I'm displaying a message after successful upload.
                                Toast.makeText(addvideos.this, "Additional logic after upload", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(addvideos.this, "Failed to upload video", Toast.LENGTH_SHORT).show();
                                // Add any additional error handling or logic if needed
                            }
                        });
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
