package com.example.shift;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shift.databinding.ActivityTrainingTasksBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.ArrayList;

public class TrainingTasksActivity extends AppCompatActivity {

    private ActivityTrainingTasksBinding binding;

    public ArrayList<Video> training_videos = new ArrayList<>();

    FirebaseAuth mAuth;

    Job currJob;

    public OnClickJobListener jobListener = new OnClickJobListener() {
        @Override
        public void onJobClick(Job job) {

        }

        @Override
        public void onVideoClick(Video video, Job job) {
            Intent intent = new Intent(TrainingTasksActivity.this, VideoActivity.class);

            Gson gson = new Gson();
            String json = gson.toJson(video);
            String json2 = gson.toJson(job);
            intent.putExtra("currJob", json2);
            intent.putExtra("video", json);
            startActivity(intent);
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTrainingTasksBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();

        String json = getIntent().getStringExtra("currJob");

        Gson gson = new Gson();
        currJob = gson.fromJson(json, Job.class);

        TextView heading = (TextView) findViewById(R.id.textView3);
        String heading_text = currJob.getCompany() + " Training";
        heading.setText(heading_text);

        ImageView logo = findViewById(R.id.imageView3);
        logo.setImageResource(currJob.getImageId());

        TextView subheading1 = (TextView) findViewById(R.id.textView5);
        String subheading_text = "Required Training videos";
        subheading1.setText(subheading_text);


        Video video1 = new Video("How to operate cashier", R.drawable.cashier, "https://www.youtube.com/embed/3ZrlcDgS7qc");
        Video video2 = new Video("How to interact with customers", R.drawable.customer, "https://www.youtube.com/embed/f3A5d7pvWlM");
        training_videos.add(video1);
        training_videos.add(video2);
        RecyclerView videoView = findViewById(R.id.videoRecycler);
        VideoAdapter videoAdapter = new VideoAdapter(training_videos, getApplicationContext(), jobListener, currJob);
        videoView.setAdapter(videoAdapter);
        videoView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL,false));
    }

    public void goBackJob(View view) {
        finish();
    }
}