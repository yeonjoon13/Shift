package com.example.shift;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shift.databinding.ActivityHomeBinding;
import com.example.shift.databinding.ActivityTrainingTasksBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.w3c.dom.Text;

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
        public void onVideoClick(Video video) {


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

        ImageView logo = findViewById(R.id.imageView2);
        logo.setImageResource(currJob.getImageId());

        TextView subheading1 = (TextView) findViewById(R.id.textView5);
        String subheading_text = "Required Training videos";
        subheading1.setText(subheading_text);


        Video video1 = new Video("How to operate cashier");
        Video video2 = new Video("How to interact with customers");
        training_videos.add(video1);
        training_videos.add(video2);
        RecyclerView videoView = findViewById(R.id.videoRecycler);
        VideoAdapter videoAdapter = new VideoAdapter(training_videos, getApplicationContext(), jobListener);
        videoView.setAdapter(videoAdapter);
        videoView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL,false));
    }
}