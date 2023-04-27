package com.example.shift;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

public class VideoActivity extends AppCompatActivity {

    WebView webView;

    Video video;
    Job currJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getSupportActionBar().hide();

        String json = getIntent().getStringExtra("video");
        String json2 = getIntent().getStringExtra("currJob");

        Gson gson = new Gson();
        video = gson.fromJson(json, Video.class);
        currJob = gson.fromJson(json2, Job.class);

        TextView heading = findViewById(R.id.textView4);
        String heading_text =  video.getTitle();
        heading.setText(heading_text);

        ImageView logo = findViewById(R.id.imageView3);
        logo.setImageResource(currJob.getImageId());

        Button takeQuiz = findViewById(R.id.button_quiz);

        if (currJob.getCompleted()) {
            takeQuiz.setVisibility(View.INVISIBLE);
        }

        String link = video.getVideoLink();

        String frameVideo = "<html><body><iframe width=\"400\" height=\"200\" src=\"" + link + "\" frameborder=\"0\" allow=\"fullscreen;\"></iframe></body></html>";

        WebView displayYoutubeVideo = findViewById(R.id.mWebView);

        WebSettings webSettings = displayYoutubeVideo.getSettings();
        webSettings.setJavaScriptEnabled(true);
        displayYoutubeVideo.loadData(frameVideo, "text/html", "utf-8");
    }

    public void goBackTraining(View view) {
        Intent intent = new Intent(VideoActivity.this, TrainingTasksActivity.class);
        Gson gson = new Gson();
        String json = gson.toJson(currJob);
        intent.putExtra("currJob", json);
        startActivity(intent);
        finish();
    }

    public void goQuiz(View view) {
        Intent i = new Intent(VideoActivity.this, StartQuizActivity.class);
        Gson gson = new Gson();
        String json2 = gson.toJson(currJob);
        i.putExtra("currJob", json2);
        startActivity(i);
        finish();
    }
}