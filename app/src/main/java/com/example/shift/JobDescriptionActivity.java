package com.example.shift;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.navigation.ui.AppBarConfiguration;

//import com.google.gson.Gson;

import com.example.shift.databinding.ActivityJobDescriptionBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.HashMap;

public class JobDescriptionActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityJobDescriptionBinding binding;

    private Job currJob;

    FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        Button star = findViewById(R.id.star_fill);
        if (currJob.getSaved()) {
            star.setBackgroundResource(R.drawable.baseline_star_24);
        } else {
            star.setBackgroundResource(R.drawable.baseline_star_outline_24);
        }
        Button btn = (Button)findViewById(R.id.buttonTraining);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobDescriptionActivity.this, TrainingTasksActivity.class);

                Gson gson = new Gson();
                String json = gson.toJson(currJob);
                intent.putExtra("currJob", json);
                JobDescriptionActivity.this.startActivity(intent);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJobDescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        setSupportActionBar(binding.toolbar);
        String json = getIntent().getStringExtra("job");

        Gson gson = new Gson();
        currJob = gson.fromJson(json, Job.class);

        ImageView logo = findViewById(R.id.imageViewLogo);
        TextView company = findViewById(R.id.textCompany);
        TextView address = findViewById(R.id.textAddress);
        TextView date = findViewById(R.id.textDate);
        TextView training = findViewById(R.id.textComplete);
        Button apply = findViewById(R.id.buttonQuit);
        TextView description = findViewById(R.id.textDescriptionWord);
        TextView role = findViewById(R.id.textRoleName);

        logo.setImageResource(currJob.getImageId());

        company.setText(currJob.getCompany());
        address.setText(currJob.getAddress());
        date.setText(currJob.getDate());

        String completion = currJob.getCompleted() ? "Requirements Complete" : "Incomplete Requirements";
        training.setText(completion);

        String status = currJob.getchecked_in() ? "QUIT" : "APPLY";
        apply.setText(status);

        description.setText(currJob.getDescription());
        role.setText(currJob.getRole());
    }



    public void goBack(View view) {
        finish();
    }

    public void jobLike(View view) {
        Button star = findViewById(R.id.star_fill);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getCurrentUser().getUid();
        if (currJob.getSaved()) {
            star.setBackgroundResource(R.drawable.baseline_star_outline_24);
            currJob.setSaved();
        } else {
            star.setBackgroundResource(R.drawable.baseline_star_24);
            currJob.setSaved();
        }

        DatabaseReference masterReference = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("masterJobs");
        masterReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    Job j = d.getValue(Job.class);
                    if (j.getCompany().equals(currJob.getCompany())) {
                        HashMap<String, Object> User = new HashMap<>();
                        User.put("saved", !j.getSaved());
                        masterReference.child(d.getKey()).updateChildren(User);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void changeStatus(View view) {

        Button apply = (Button) findViewById(R.id.buttonQuit);
        mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getCurrentUser().getUid();

        if (currJob.getchecked_in()) {
            // inflate the layout of the popup window
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.popup, null);

            // create the popup window
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true; // lets taps outside the popup also dismiss it
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

            // show the popup window
            // which view you pass in doesn't matter, it is only used for the window tolken
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

            TextView leaveAnywaysButton = popupView.findViewById(R.id.leave_anyway);
            ImageView closePopUp = popupView.findViewById(R.id.close_popup);

            leaveAnywaysButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currJob.setchecked_in();
                    popupWindow.dismiss();
                    String status = currJob.getchecked_in() ? "Quit" : "Apply";
                    apply.setText(status);
                    DatabaseReference masterReference = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("masterJobs");
                    masterReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot d : snapshot.getChildren()) {
                                Job j = d.getValue(Job.class);
                                if ( j.getCompany().equals(currJob.getCompany())) {
                                    HashMap<String, Object> User = new HashMap<>();
                                    User.put("checked_in", !j.getchecked_in());
                                    masterReference.child(d.getKey()).updateChildren(User);
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                }
            });

            // dismiss the popup window when touched
            closePopUp.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });
        } else {
            currJob.setchecked_in();
            DatabaseReference masterReference = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("masterJobs");
            masterReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot d : snapshot.getChildren()) {
                        Job j = d.getValue(Job.class);
                        if ( j.getCompany().equals(currJob.getCompany())) {
                            HashMap<String, Object> User = new HashMap<>();
                            User.put("checked_in", !j.getchecked_in());
                            masterReference.child(d.getKey()).updateChildren(User);
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }

        String status = currJob.getchecked_in() ? "Quit" : "Apply";
        apply.setText(status);


        // update firebase
    }

    public void goTraining(View view) {
//        Button btn = (Button)findViewById(R.id.buttonTraining);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(JobDescriptionActivity.this, TrainingTasksActivity.class);
//
//                Gson gson = new Gson();
//                String json = gson.toJson(currJob);
//                intent.putExtra("currJob", json);
//                startActivity(intent);
//            }
//        });
        // Make Training Section and go there
//        Intent intent = new Intent(JobDescriptionActivity.this, TrainingTasksActivity.class);
//
//        Gson gson = new Gson();
//        String json = gson.toJson(currJob);
//        intent.putExtra("currJob", json);
//        startActivity(intent);
    }
}