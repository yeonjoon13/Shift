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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityJobDescriptionBinding.inflate(getLayoutInflater());
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getSupportActionBar().hide();
        setContentView(binding.getRoot());

//        setSupportActionBar(binding.toolbar);
        String json = getIntent().getStringExtra("job");

        Gson gson = new Gson();
        currJob = gson.fromJson(json, Job.class);

        ImageView logo = (ImageView) findViewById(R.id.imageViewLogo);
        TextView company = (TextView) findViewById(R.id.textCompany);
        TextView address = (TextView) findViewById(R.id.textAddress);
        TextView date = (TextView) findViewById(R.id.textDate);
        TextView training = (TextView) findViewById(R.id.textComplete);
        Button apply = (Button) findViewById(R.id.buttonQuit);
        TextView description = (TextView) findViewById(R.id.textDescriptionWord);
        TextView role = (TextView) findViewById(R.id.textRoleName);


        logo.setImageResource(R.drawable.fedex_logo);

        company.setText(currJob.getCompany());
        address.setText(currJob.getAddress());
        date.setText(currJob.getDate());

        String completion = currJob.getTraining() ? "Requirements Complete" : "Incomplete Requirements";
        training.setText(completion);

        String status = currJob.getchecked_in() ? "Quit" : "Apply";
        apply.setText(status);

        description.setText(currJob.getDescription());
        role.setText(currJob.getRole());
    }



    public void goBack(View view) {
        Intent intent = new Intent(JobDescriptionActivity.this, HomeActivity.class);
        startActivity(intent);
//        Gson gson = new Gson();
//        String json = gson.toJson(currJob);
//        intent.putExtra("job", json);
//        setResult(Activity.RESULT_OK);
        finish();
    }

//    public void jobLike(View view) {
//        ImageView star = (ImageView) findViewById(R.id.star_fill);
//        if (star.getVisibility() == View.INVISIBLE) {
//            star.setVisibility(View.VISIBLE);
//            currJob.setSaved();
//        } else {
//            star.setVisibility(View.INVISIBLE);
//            currJob.setSaved();
//        }
//        // update firebase
//
//    }

    public void jobLike(View view) {
        ImageView star = (ImageView) findViewById(R.id.star_fill);
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
        }

        String status = currJob.getchecked_in() ? "Quit" : "Apply";
        apply.setText(status);


        // update firebase
    }

    public void goTraining(View view) {
        // Make Training Section and go there
    }
}