package com.example.shift;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.shift.databinding.ActivityQuizFinishBinding;
import com.example.shift.databinding.ActivityStartQuizBinding;
import com.google.gson.Gson;

public class QuizFinishActivity extends AppCompatActivity {

    private ActivityQuizFinishBinding binding;

    Job currJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = ActivityQuizFinishBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String json = getIntent().getStringExtra("currJob");
        Gson gson = new Gson();
        currJob = gson.fromJson(json, Job.class);

        TextView congratsText = findViewById(R.id.congratsText);
        TextView completedText = findViewById(R.id.completedText);

        if (currJob.getCompleted()) {
            congratsText.setText(R.string.congrats);
            completedText.setText(R.string.finished_quiz);
        } else {
            congratsText.setText(R.string.failed);
            completedText.setText(R.string.try_again);
        }
    }

    protected void onStart() {
        super.onStart();
        Button btn = (Button)findViewById(R.id.toJobsButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizFinishActivity.this, TrainingTasksActivity.class);

                Gson gson = new Gson();
                String json = gson.toJson(currJob);
                intent.putExtra("currJob", json);
                QuizFinishActivity.this.startActivity(intent);
                finish();
            }
        });
    }
}