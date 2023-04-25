package com.example.shift;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.shift.databinding.ActivityQuizStartBinding;
import com.example.shift.ui.Question;
import com.google.gson.Gson;

import java.util.ArrayList;

public class QuizStartActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityQuizStartBinding binding;

    private ArrayList<Question> questionBank;

    private int numCorrect;
    private int currQIndex;

    Job currJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_quiz_start);

        String json = getIntent().getStringExtra("currJob");

        Gson gson = new Gson();
        currJob = gson.fromJson(json, Job.class);

        binding = ActivityQuizStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_quiz_start);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        TextView compNameSmall = findViewById(R.id.textDate);
        compNameSmall.setText(currJob.getCompany());

        TextView textCompany = findViewById(R.id.textCompany);
        textCompany.setText(currJob.getCompany());

        TextView roleName = findViewById(R.id.roleName);
        roleName.setText(currJob.getRole());

        TextView roleDesc = findViewById(R.id.textRoleName);
        roleDesc.setText(R.string.description);

        TextView question = findViewById(R.id.questionBox);
        questionBank = currJob.getQuestions();
        Question firstQuestion = questionBank.get(0);
        question.setText(firstQuestion.getQuestion());
        Button q1 = findViewById(R.id.q1);
        q1.setText(firstQuestion.getqBank().get(0));
        Button q2 = findViewById(R.id.q2);
        q2.setText(firstQuestion.getqBank().get(1));
        Button q3 = findViewById(R.id.q3);
        q3.setText(firstQuestion.getqBank().get(2));
        Button q4 = findViewById(R.id.q4);
        q4.setText(firstQuestion.getqBank().get(3));

        numCorrect = 0;
        currQIndex = 0;

    }

    @Override
    protected void onStart() {
        super.onStart();
        //Set recyclerview to the first element in the arraylist
        Button btn = (Button)findViewById(R.id.buttonTraining);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizStartActivity.this, TrainingTasksActivity.class);

                Gson gson = new Gson();
                String json = gson.toJson(currJob);
                intent.putExtra("currJob", json);
                QuizStartActivity.this.startActivity(intent);
            }
        });
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
                        currJob.setQuestionsCompleted();

                        Intent intent = new Intent(QuizStartActivity.this, TrainingTasksActivity.class);

                        Gson gson = new Gson();
                        String json = gson.toJson(currJob);
                        intent.putExtra("currJob", json);
                        QuizStartActivity.this.startActivity(intent);

                    } else {
                        //Make toast that they were incorrect and send back to main activity
                        Intent intent = new Intent(QuizStartActivity.this, TrainingTasksActivity.class);

                        Gson gson = new Gson();
                        String json = gson.toJson(currJob);
                        intent.putExtra("currJob", json);
                        QuizStartActivity.this.startActivity(intent);
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
                        currJob.setQuestionsCompleted();

                        Intent intent = new Intent(QuizStartActivity.this, TrainingTasksActivity.class);

                        Gson gson = new Gson();
                        String json = gson.toJson(currJob);
                        intent.putExtra("currJob", json);
                        QuizStartActivity.this.startActivity(intent);

                    } else {
                        //Make toast that they were incorrect and send back to main activity
                        Intent intent = new Intent(QuizStartActivity.this, TrainingTasksActivity.class);

                        Gson gson = new Gson();
                        String json = gson.toJson(currJob);
                        intent.putExtra("currJob", json);
                        QuizStartActivity.this.startActivity(intent);
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
                        currJob.setQuestionsCompleted();

                        Intent intent = new Intent(QuizStartActivity.this, TrainingTasksActivity.class);

                        Gson gson = new Gson();
                        String json = gson.toJson(currJob);
                        intent.putExtra("currJob", json);
                        QuizStartActivity.this.startActivity(intent);

                    } else {
                        //Make toast that they were incorrect and send back to main activity
                        Intent intent = new Intent(QuizStartActivity.this, TrainingTasksActivity.class);

                        Gson gson = new Gson();
                        String json = gson.toJson(currJob);
                        intent.putExtra("currJob", json);
                        QuizStartActivity.this.startActivity(intent);
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
                        currJob.setQuestionsCompleted();

                        Intent intent = new Intent(QuizStartActivity.this, TrainingTasksActivity.class);

                        Gson gson = new Gson();
                        String json = gson.toJson(currJob);
                        intent.putExtra("currJob", json);
                        QuizStartActivity.this.startActivity(intent);

                    } else {
                        //Make toast that they were incorrect and send back to main activity
                        Intent intent = new Intent(QuizStartActivity.this, TrainingTasksActivity.class);

                        Gson gson = new Gson();
                        String json = gson.toJson(currJob);
                        intent.putExtra("currJob", json);
                        QuizStartActivity.this.startActivity(intent);
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

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_quiz_start);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void goBack(View view) {
        finish();
    }
}