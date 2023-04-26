package com.example.shift;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shift.databinding.ActivityTrainingTasksBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class TrainingTasksActivity extends AppCompatActivity {

    private ActivityTrainingTasksBinding binding;

    public ArrayList<Video> training_videos = new ArrayList<>();

    public ArrayList<Job> jobs = new ArrayList<>();
    FirebaseAuth mAuth;

    Job currJob;

    public OnClickJobListener jobListener = new OnClickJobListener() {
        @Override
        public void onJobClick(Job job) {
            if (!currJob.getCompleted()) {
                Intent intent = new Intent(TrainingTasksActivity.this, StartQuizActivity.class);
                Gson gson = new Gson();
                String json = gson.toJson(job);
                intent.putExtra("currJob", json);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(TrainingTasksActivity.this, "User has already completed this quiz", Toast.LENGTH_SHORT).show();
            }

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
            finish();
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
        String subheading_text = "Required Training Videos";
        subheading1.setText(subheading_text);

        TextView subheading2 = (TextView) findViewById(R.id.textView2);
        String subheading_text2 = "Quizzes to Complete";
        subheading2.setText(subheading_text2);



        String userID = mAuth.getCurrentUser().getUid();
        DatabaseReference masterReference = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("masterJobs");
        masterReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Video> training_array = new ArrayList<>();
                for (DataSnapshot d : snapshot.getChildren()) {
                    Job j = d.getValue(Job.class);
                    if (j.getCompany().equals(currJob.getCompany())) {
                        ArrayList<Training> trainingVids = j.getTrainingVids();
                        for (Training t : trainingVids) {
                            Video video = new Video(t.getDescription(), t.getImageId(), t.getVideoLink());
                            training_array.add(video);
                        }
                        break;
                    }
                }
                RecyclerView videoView = findViewById(R.id.videoRecycler);
                VideoAdapter videoAdapter = new VideoAdapter(training_array, getApplicationContext(), jobListener, currJob);
                videoView.setAdapter(videoAdapter);
                videoView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL,false));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

/*
       Hardcoding the adapter
 */

//        Video video1 = new Video("How to operate cashier", R.drawable.cashier, "https://www.youtube.com/embed/3ZrlcDgS7qc");
//        Video video2 = new Video("How to interact with customers", R.drawable.customer, "https://www.youtube.com/embed/f3A5d7pvWlM");
//        training_videos.add(video1);
//        training_videos.add(video2);
//        RecyclerView videoView = findViewById(R.id.videoRecycler);
//        VideoAdapter videoAdapter = new VideoAdapter(training_videos, getApplicationContext(), jobListener, currJob);
//        videoView.setAdapter(videoAdapter);
//        videoView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL,false));

        jobs.add(currJob);

        RecyclerView quizView = findViewById(R.id.quizRecycler);
        QuizAdapter quizAdapter = new QuizAdapter(jobs, getApplicationContext(), jobListener);
        quizView.setAdapter(quizAdapter);
        quizView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL,false));

    }

    public void goBackJob(View view) {
        finish();
    }
}