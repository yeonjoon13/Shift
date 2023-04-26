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

        TextView textCompany = findViewById(R.id.textCompany);
        textCompany.setText(currJob.getCompany());

        TextView roleName = findViewById(R.id.roleName);
        roleName.setText(currJob.getRole());
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