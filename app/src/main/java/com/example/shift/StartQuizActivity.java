package com.example.shift;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shift.databinding.ActivityStartQuizBinding;
import com.google.gson.Gson;

public class StartQuizActivity extends AppCompatActivity {

    private ActivityStartQuizBinding binding;

    Job currJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = ActivityStartQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setContentView(R.layout.activity_start_quiz);

        String json = getIntent().getStringExtra("currJob");
        Gson gson = new Gson();
        currJob = gson.fromJson(json, Job.class);

        TextView compNameSmall = findViewById(R.id.textDate);
        compNameSmall.setText(currJob.getCompany());

        ImageView imageView = findViewById(R.id.imageViewLogo);
        imageView.setImageResource(currJob.getImageId());

        ImageView backBtn = this.findViewById(R.id.button_back);
        backBtn.setImageResource(R.drawable.baseline_arrow_back_24);

        TextView textCompany = findViewById(R.id.textCompany);
        textCompany.setText(currJob.getCompany());

        TextView roleName = findViewById(R.id.roleName);
        roleName.setText(currJob.getRole());

        TextView roleDesc = findViewById(R.id.textRoleName);
        roleDesc.setText(R.string.description);
    }

    protected void onStart() {
        super.onStart();
        Button btn = (Button)findViewById(R.id.startQuizButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartQuizActivity.this, QuizMainActivity.class);

                Gson gson = new Gson();
                String json = gson.toJson(currJob);
                intent.putExtra("currJob", json);
                StartQuizActivity.this.startActivity(intent);
                finish();
            }
        });
    }

    public void startBack(View view) {
        Intent intent = new Intent(StartQuizActivity.this, TrainingTasksActivity.class);
        Gson gson = new Gson();
        String json = gson.toJson(currJob);
        intent.putExtra("currJob", json);
        StartQuizActivity.this.startActivity(intent);
        finish();
    }

    public void beginQuiz(View view){

    }
}