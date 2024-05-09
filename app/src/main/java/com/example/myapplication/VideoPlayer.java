package com.example.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoPlayer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);


        VideoView videoView = findViewById(R.id.videoView);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        String videoUrl = getIntent().getStringExtra("VIDEO_URL");
        if (videoUrl != null && !videoUrl.isEmpty()) {
            Log.d("VideoPlayer", "Video URL: " + videoUrl);
            Uri uri = Uri.parse(videoUrl);
            videoView.setVideoURI(uri);
            videoView.requestFocus();
            videoView.start();
        }
        else {
            Log.e("VideoPlayer", "Video URL is null or empty.");
        }
    }
}
