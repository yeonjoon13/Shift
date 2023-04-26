package com.example.shift;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
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
        ImageView background = findViewById(R.id.imageView2);


        if (currJob.getCompleted()) {
            congratsText.setText(R.string.congrats);
            String passed = "You passed the quiz for " + currJob.getCompany() + " " + currJob.getRole() +"!";
            completedText.setText(passed);
            background.setImageResource(R.drawable.baseline_check_circle_outline_24);
        } else {
            String failed = "You failed the quiz for " + currJob.getCompany() + " " + currJob.getRole() +"!";
            completedText.setText(failed);
            congratsText.setText(R.string.try_again);
            background.setImageResource(R.drawable.baseline_close_24);
        }
    }

    protected void onStart() {
        super.onStart();
        Button btn = (Button)findViewById(R.id.toJobsButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizFinishActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}