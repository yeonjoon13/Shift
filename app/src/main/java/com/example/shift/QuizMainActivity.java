package com.example.shift;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.shift.databinding.ActivityQuizMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class QuizMainActivity extends AppCompatActivity {

    private ActivityQuizMainBinding binding;

    private ArrayList<Question> questionBank;

    private int numCorrect;
    private int currQIndex;

    public ArrayList<Question> mcDonaldQuestions = new ArrayList<>();

    Job currJob;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = ActivityQuizMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String json = getIntent().getStringExtra("currJob");
        Gson gson = new Gson();
        currJob = gson.fromJson(json, Job.class);

        TextView question = this.findViewById(R.id.questionBox);
        questionBank = currJob.getQuestions();
        //Collections.shuffle(questionBank);
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
        TextView progress = findViewById(R.id.progressText);
        progress.setText("Question " + (currQIndex + 1) + "/" + questionBank.size());

        numCorrect = 0;
        currQIndex = 0;

    }

    public void doQuiz(View view) {

        Button q1 = findViewById(R.id.q1);
        Button q2 = findViewById(R.id.q2);
        Button q3 = findViewById(R.id.q3);
        Button q4 = findViewById(R.id.q4);
        TextView progress = findViewById(R.id.progressText);
        TextView question = findViewById(R.id.questionBox);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getCurrentUser().getUid();

        q1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currQIndex < questionBank.size() - 1 && q1.getText().equals(questionBank.get(currQIndex).getCorrectAns())){
                    numCorrect ++;
                }

                if (currQIndex >= questionBank.size() - 1) {
                    //if numright score is greater than or equal to target score
                    if (q1.getText().equals(questionBank.get(currQIndex).getCorrectAns())){
                        numCorrect ++;
                    }

                    if(numCorrect >= currJob.getRequiredCorrect()) {
                        //TODO: move back to main activity
                        currJob.setCompleted();

                        DatabaseReference masterReference = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("masterJobs");
                        masterReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot d : snapshot.getChildren()) {
                                    Job j = d.getValue(Job.class);
                                    if (j.getCompany().equals(currJob.getCompany())) {
                                        HashMap<String, Object> User = new HashMap<>();
                                        User.put("completed", !j.getCompleted());
                                        masterReference.child(d.getKey()).updateChildren(User);
                                        break;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });

                        Intent intent = new Intent(QuizMainActivity.this, QuizFinishActivity.class);

                        Gson gson = new Gson();
                        String json = gson.toJson(currJob);
                        intent.putExtra("currJob", json);
                        QuizMainActivity.this.startActivity(intent);
                        finish();
                        return;
                    } else {
                        //Make toast that they were incorrect and send back to main activity
                        Intent intent = new Intent(QuizMainActivity.this, QuizFinishActivity.class);

                        Gson gson = new Gson();
                        String json = gson.toJson(currJob);
                        intent.putExtra("currJob", json);
                        QuizMainActivity.this.startActivity(intent);
                        finish();
                        return;
                    }
                }

                currQIndex++;

                //set new texts for questions
                Question nextQuestion = questionBank.get(currQIndex);
                question.setText(nextQuestion.getQuestion());
                progress.setText("Question " + (currQIndex + 1) + "/" + questionBank.size());
                q1.setText(nextQuestion.getqBank().get(0));
                q2.setText(nextQuestion.getqBank().get(1));
                q3.setText(nextQuestion.getqBank().get(2));
                q4.setText(nextQuestion.getqBank().get(3));
            }
        });

        q2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currQIndex < questionBank.size() - 1 && q2.getText().equals(questionBank.get(currQIndex).getCorrectAns())){
                    numCorrect ++;
                }

                if (currQIndex >= questionBank.size() - 1) {
                    //if numright score is greater than or equal to target score
                    if (q2.getText().equals(questionBank.get(currQIndex).getCorrectAns())){
                        numCorrect ++;
                    }

                    if(numCorrect >= currJob.getRequiredCorrect()) {
                        //TODO: move back to main activity
                        currJob.setCompleted();

                        DatabaseReference masterReference = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("masterJobs");
                        masterReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot d : snapshot.getChildren()) {
                                    Job j = d.getValue(Job.class);
                                    if (j.getCompany().equals(currJob.getCompany())) {
                                        HashMap<String, Object> User = new HashMap<>();
                                        User.put("completed", !j.getCompleted());
                                        masterReference.child(d.getKey()).updateChildren(User);
                                        break;
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });

                        Intent intent = new Intent(QuizMainActivity.this, QuizFinishActivity.class);

                        Gson gson = new Gson();
                        String json = gson.toJson(currJob);
                        intent.putExtra("currJob", json);
                        QuizMainActivity.this.startActivity(intent);
                        finish();
                        return;
                    } else {
                        //Make toast that they were incorrect and send back to main activity
                        Intent intent = new Intent(QuizMainActivity.this, QuizFinishActivity.class);

                        Gson gson = new Gson();
                        String json = gson.toJson(currJob);
                        intent.putExtra("currJob", json);
                        QuizMainActivity.this.startActivity(intent);
                        finish();
                        return;
                    }
                }

                currQIndex++;

                //set new texts for questions
                Question nextQuestion = questionBank.get(currQIndex);
                question.setText(nextQuestion.getQuestion());
                progress.setText("Question " + (currQIndex + 1) + "/" + questionBank.size());
                q1.setText(nextQuestion.getqBank().get(0));
                q2.setText(nextQuestion.getqBank().get(1));
                q3.setText(nextQuestion.getqBank().get(2));
                q4.setText(nextQuestion.getqBank().get(3));
            }
        });

        q3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currQIndex < questionBank.size() - 1 && q3.getText().equals(questionBank.get(currQIndex).getCorrectAns())){
                    numCorrect ++;
                }

                if (currQIndex >= questionBank.size() - 1) {
                    //if numright score is greater than or equal to target score
                    if (q3.getText().equals(questionBank.get(currQIndex).getCorrectAns())){
                        numCorrect ++;
                    }

                    if(numCorrect >= currJob.getRequiredCorrect()) {
                        //TODO: move back to main activity
                        currJob.setCompleted();

                        DatabaseReference masterReference = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("masterJobs");
                        masterReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot d : snapshot.getChildren()) {
                                    Job j = d.getValue(Job.class);
                                    if (j.getCompany().equals(currJob.getCompany())) {
                                        HashMap<String, Object> User = new HashMap<>();
                                        User.put("completed", !j.getCompleted());
                                        masterReference.child(d.getKey()).updateChildren(User);
                                        break;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });

                        Intent intent = new Intent(QuizMainActivity.this, QuizFinishActivity.class);

                        Gson gson = new Gson();
                        String json = gson.toJson(currJob);
                        intent.putExtra("currJob", json);
                        QuizMainActivity.this.startActivity(intent);
                        finish();
                        return;
                    } else {
                        //Make toast that they were incorrect and send back to main activity
                        Intent intent = new Intent(QuizMainActivity.this, QuizFinishActivity.class);

                        Gson gson = new Gson();
                        String json = gson.toJson(currJob);
                        intent.putExtra("currJob", json);
                        QuizMainActivity.this.startActivity(intent);
                        finish();
                        return;
                    }
                }

                currQIndex++;

                //set new texts for questions
                Question nextQuestion = questionBank.get(currQIndex);
                question.setText(nextQuestion.getQuestion());
                progress.setText("Question " + (currQIndex + 1) + "/" + questionBank.size());
                q1.setText(nextQuestion.getqBank().get(0));
                q2.setText(nextQuestion.getqBank().get(1));
                q3.setText(nextQuestion.getqBank().get(2));
                q4.setText(nextQuestion.getqBank().get(3));
            }
        });

        q4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (currQIndex < questionBank.size() - 1 && q4.getText().equals(questionBank.get(currQIndex).getCorrectAns())){
                    numCorrect ++;
                }

                if (currQIndex >= questionBank.size() - 1) {
                    //if numright score is greater than or equal to target score
                    if (q4.getText().equals(questionBank.get(currQIndex).getCorrectAns())){
                        numCorrect ++;
                    }

                    if(numCorrect >= currJob.getRequiredCorrect()) {
                        //TODO: move back to main activity
                        currJob.setCompleted();

                        DatabaseReference masterReference = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("masterJobs");
                        masterReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot d : snapshot.getChildren()) {
                                    Job j = d.getValue(Job.class);
                                    if (j.getCompany().equals(currJob.getCompany())) {
                                        HashMap<String, Object> User = new HashMap<>();
                                        User.put("completed", !j.getCompleted());
                                        masterReference.child(d.getKey()).updateChildren(User);
                                        break;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });

                        Intent intent = new Intent(QuizMainActivity.this, QuizFinishActivity.class);

                        Gson gson = new Gson();
                        String json = gson.toJson(currJob);
                        intent.putExtra("currJob", json);
                        QuizMainActivity.this.startActivity(intent);
                        finish();
                        return;
                    } else {
                        //Make toast that they were incorrect and send back to main activity
                        Intent intent = new Intent(QuizMainActivity.this, QuizFinishActivity.class);

                        Gson gson = new Gson();
                        String json = gson.toJson(currJob);
                        intent.putExtra("currJob", json);
                        QuizMainActivity.this.startActivity(intent);
                        finish();
                        return;
                    }
                }

                currQIndex++;


                //set new texts for questions
                Question nextQuestion = questionBank.get(currQIndex);
                question.setText(nextQuestion.getQuestion());
                progress.setText("Question " + (currQIndex + 1) + "/" + questionBank.size());
                q1.setText(nextQuestion.getqBank().get(0));
                q2.setText(nextQuestion.getqBank().get(1));
                q3.setText(nextQuestion.getqBank().get(2));
                q4.setText(nextQuestion.getqBank().get(3));
            }
        });
    }
}