package com.example.shift;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//import com.google.gson.Gson;

import com.example.shift.databinding.ActivityJobDescriptionBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.Serializable;

public class JobDescriptionActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityJobDescriptionBinding binding;

    private Job currJob;

    FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        ImageView star = findViewById(R.id.star_fill);
        if (currJob.getSaved()) {
            star.setVisibility(View.VISIBLE);
        } else {
            star.setVisibility(View.INVISIBLE);
        }
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


//        logo.setImageResource()
//        ImageView star = findViewById(R.id.star_fill);
//        if (currJob.getSaved()) {
//            star.setVisibility(View.VISIBLE);
//        } else {
//            star.setVisibility(View.INVISIBLE);
//        }

        company.setText(currJob.getCompany());
        address.setText(currJob.getAddress());
        date.setText(currJob.getDate());

        String completion = currJob.getTraining() ? "Requirements Complete" : "Incomplete Requirements";
        training.setText(completion);

        final String[] a = {"Not there"};
        mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getCurrentUser().getUid();
        DatabaseReference uR = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("upcomingJobs");
        uR.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    if ((d.child("company").getValue(String.class)).equals(currJob.getCompany())) {
                        a[0] = "There";
                    }
                }
                if (a[0].equals("Not there")) {
                    String status = "APPLY";
                    apply.setText(status);
                } else {
                    currJob.setchecked_in();
                    String status = "QUIT";
                    apply.setText(status);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        description.setText(currJob.getDescription());
        role.setText(currJob.getRole());
    }



    public void goBack(View view) {
        Intent intent = new Intent(JobDescriptionActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void jobLike(View view) {
        ImageView star = findViewById(R.id.star_fill);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getCurrentUser().getUid();
        if (star.getVisibility() == View.INVISIBLE) {
            star.setVisibility(View.VISIBLE);
            currJob.setSaved();
            DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Users");
            userReference.child(userID).child("likedJobs").push().setValue(currJob);
        } else {
            star.setVisibility(View.INVISIBLE);
            currJob.setSaved();
            DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("likedJobs");
            userReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot d : snapshot.getChildren()) {
                        if (( d.child("company").getValue(String.class)).equals(currJob.getCompany())) {
                            userReference.child(d.getKey()).removeValue();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }
    }

    public void changeStatus(View view) {

        Button apply = (Button) findViewById(R.id.buttonQuit);
        mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getCurrentUser().getUid();
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("upcomingJobs");

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
                    userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot d : snapshot.getChildren()) {
                                if ((d.child("company").getValue(String.class)).equals(currJob.getCompany())) {
                                    userReference.child(d.getKey()).removeValue();
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
            userReference.push().setValue(currJob);
        }

        String status = currJob.getchecked_in() ? "Quit" : "Apply";
        apply.setText(status);


        // update firebase
    }

    public void goTraining(View view) {
        // Make Training Section and go there
    }
}