package com.example.shift;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.shift.databinding.ActivityQuizMainBinding;
import com.google.gson.Gson;

import java.util.ArrayList;

public class QuizMainActivity extends AppCompatActivity {

    private ActivityQuizMainBinding binding;

    private ArrayList<Question> questionBank;

    private int numCorrect;
    private int currQIndex;

    Job currJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.activity_quiz_main);
        binding = ActivityQuizMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String json = getIntent().getStringExtra("currJob");
        Gson gson = new Gson();
        currJob = gson.fromJson(json, Job.class);


        TextView question = this.findViewById(R.id.questionBox);
        questionBank = currJob.getQuestions();
        Question firstQuestion = questionBank.get(0);
        question.setText(firstQuestion.getQuestion());
        Button q1 = this.findViewById(R.id.q1);
        q1.setText(firstQuestion.getqBank().get(0));
        Button q2 = this.findViewById(R.id.q2);
        q2.setText(firstQuestion.getqBank().get(1));
        Button q3 = this.findViewById(R.id.q3);
        q3.setText(firstQuestion.getqBank().get(2));
        Button q4 = this.findViewById(R.id.q4);
        q4.setText(firstQuestion.getqBank().get(3));

        numCorrect = 0;
        currQIndex = 0;
    }

    public void doQuiz(View view) {

        Button q1 = findViewById(R.id.q1);
        Button q2 = findViewById(R.id.q2);
        Button q3 = findViewById(R.id.q3);
        Button q4 = findViewById(R.id.q4);
        TextView question = findViewById(R.id.questionBox);

        q1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (q1.getText().equals(currJob.getQuestions().get(currQIndex).getCorrectAns())){
                    numCorrect ++;
                }

                if (currQIndex >= currJob.getQuestions().size() - 1) {
                    //if numright score is greater than or equal to target score
                    if(numCorrect >= currJob.getRequiredCorrect()) {
                        //TODO: move back to main activity
                        currJob.setCompleted();

                        Intent intent = new Intent(QuizMainActivity.this, TrainingTasksActivity.class);

                        Gson gson = new Gson();
                        String json = gson.toJson(currJob);
                        intent.putExtra("currJob", json);
                        QuizMainActivity.this.startActivity(intent);

                    } else {
                        //Make toast that they were incorrect and send back to main activity
                        Intent intent = new Intent(QuizMainActivity.this, TrainingTasksActivity.class);

                        Gson gson = new Gson();
                        String json = gson.toJson(currJob);
                        intent.putExtra("currJob", json);
                        QuizMainActivity.this.startActivity(intent);
                    }

                    //return to main activity
                }

                currQIndex++;

                //set new texts for questions
                Question nextQuestion = questionBank.get(currQIndex);
                question.setText(nextQuestion.getQuestion());
                q1.setText(nextQuestion.getqBank().get(0));
                q2.setText(nextQuestion.getqBank().get(1));
                q3.setText(nextQuestion.getqBank().get(2));
                q4.setText(nextQuestion.getqBank().get(3));



            }
        });

        q2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (q2.getText().equals(currJob.getQuestions().get(currQIndex).getCorrectAns())){
                    numCorrect ++;
                }

                if (currQIndex >= currJob.getQuestions().size() - 1) {
                    //if numright score is greater than or equal to target score
                    if(numCorrect >= currJob.getRequiredCorrect()) {
                        //TODO: move back to main activity
                        currJob.setCompleted();

                        Intent intent = new Intent(QuizMainActivity.this, TrainingTasksActivity.class);

                        Gson gson = new Gson();
                        String json = gson.toJson(currJob);
                        intent.putExtra("currJob", json);
                        QuizMainActivity.this.startActivity(intent);

                    } else {
                        //Make toast that they were incorrect and send back to main activity
                        Intent intent = new Intent(QuizMainActivity.this, TrainingTasksActivity.class);

                        Gson gson = new Gson();
                        String json = gson.toJson(currJob);
                        intent.putExtra("currJob", json);
                        QuizMainActivity.this.startActivity(intent);
                    }

                    //return to main activity
                }

                currQIndex++;

                //set new texts for questions
                Question nextQuestion = questionBank.get(currQIndex);
                question.setText(nextQuestion.getQuestion());
                q1.setText(nextQuestion.getqBank().get(0));
                q2.setText(nextQuestion.getqBank().get(1));
                q3.setText(nextQuestion.getqBank().get(2));
                q4.setText(nextQuestion.getqBank().get(3));



            }
        });

        q3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (q3.getText().equals(currJob.getQuestions().get(currQIndex).getCorrectAns())){
                    numCorrect ++;
                }

                if (currQIndex >= currJob.getQuestions().size() - 1) {
                    //if numright score is greater than or equal to target score
                    if(numCorrect >= currJob.getRequiredCorrect()) {
                        //TODO: move back to main activity
                        currJob.setCompleted();

                        Intent intent = new Intent(QuizMainActivity.this, TrainingTasksActivity.class);

                        Gson gson = new Gson();
                        String json = gson.toJson(currJob);
                        intent.putExtra("currJob", json);
                        QuizMainActivity.this.startActivity(intent);

                    } else {
                        //Make toast that they were incorrect and send back to main activity
                        Intent intent = new Intent(QuizMainActivity.this, TrainingTasksActivity.class);

                        Gson gson = new Gson();
                        String json = gson.toJson(currJob);
                        intent.putExtra("currJob", json);
                        QuizMainActivity.this.startActivity(intent);
                    }

                    //return to main activity
                }

                currQIndex++;

                //set new texts for questions
                Question nextQuestion = questionBank.get(currQIndex);
                question.setText(nextQuestion.getQuestion());
                q1.setText(nextQuestion.getqBank().get(0));
                q2.setText(nextQuestion.getqBank().get(1));
                q3.setText(nextQuestion.getqBank().get(2));
                q4.setText(nextQuestion.getqBank().get(3));



            }
        });

        q4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (q4.getText().equals(currJob.getQuestions().get(currQIndex).getCorrectAns())){
                    numCorrect ++;
                }

                if (currQIndex >= currJob.getQuestions().size() - 1) {
                    //if numright score is greater than or equal to target score
                    if(numCorrect >= currJob.getRequiredCorrect()) {
                        //TODO: move back to main activity
                        currJob.setCompleted();

                        Intent intent = new Intent(QuizMainActivity.this, TrainingTasksActivity.class);

                        Gson gson = new Gson();
                        String json = gson.toJson(currJob);
                        intent.putExtra("currJob", json);
                        QuizMainActivity.this.startActivity(intent);

                    } else {
                        //Make toast that they were incorrect and send back to main activity
                        Intent intent = new Intent(QuizMainActivity.this, TrainingTasksActivity.class);

                        Gson gson = new Gson();
                        String json = gson.toJson(currJob);
                        intent.putExtra("currJob", json);
                        QuizMainActivity.this.startActivity(intent);
                    }
                }

                currQIndex++;

                //set new texts for questions
                Question nextQuestion = questionBank.get(currQIndex);
                question.setText(nextQuestion.getQuestion());
                q1.setText(nextQuestion.getqBank().get(0));
                q2.setText(nextQuestion.getqBank().get(1));
                q3.setText(nextQuestion.getqBank().get(2));
                q4.setText(nextQuestion.getqBank().get(3));



            }
        });





    }
}