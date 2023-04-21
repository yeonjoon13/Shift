package com.example.shift;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shift.databinding.ActivityHomeBinding;
import com.google.common.reflect.TypeToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    Button logOutButton;
    FirebaseAuth mAuth;
    DatabaseReference jobDBRef;

    String userID;

    public ArrayList<Job> master = new ArrayList<>();

    public static ArrayList<Job> recommended = new ArrayList<>();

    public ArrayList<Job> upcoming = new ArrayList<>();

    public ArrayList<Job> liked = new ArrayList<>();

    public ArrayList<Job> training = new ArrayList<>();

    public ArrayList<Job> previous = new ArrayList<>();

    public OnClickJobListener jobListener = new OnClickJobListener() {
        @Override
        public void onJobClick(Job job) {

//            Toast.makeText(getApplicationContext(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeActivity.this, JobDescriptionActivity.class);

            Gson gson = new Gson();
            String json = gson.toJson(job);
            intent.putExtra("job", json);
            startActivity(intent);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();

        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.hasChild("Jobs")) {
                    jobDBRef = FirebaseDatabase.getInstance().getReference("Jobs");
                    Job j = new Job("Cashier", "McDonalds", "Manage People and Learn to Have Fun", "05/08/2023", "Purple Street",
                            "2:00 pm", "$18/hr", false, R.drawable.mcdonalds_logo);
                    Job k = new Job("Delivery", "Fedex", "Drive and Learn to Have Fun", "05/08/2023", "Purple Street",
                            "2:00 pm", "$18/hr", false, R.drawable.fedex_logo);
                    jobDBRef.push().setValue(j);
                    jobDBRef.push().setValue(k);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.print("hello");
            }


        });
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_library, R.id.navigation_chat)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        userID = mAuth.getCurrentUser().getUid();

        // REMOVE LATER, HARD CODED ONLY FOR THIS SPRINT
        /*
        Job j = new Job("Barista", "Starbucks", "Cook up some coffee", "04/20/2023", "Orange Street",
                "2:00 pm", "$20/hr", false, R.drawable.mcdonalds_logo);
        Job k = new Job("Package Handler", "Amazon", "Chuck some packages across the facility", "04/24/2023", "Red Street",
                "2:00 pm", "$18/hr", false, R.drawable.fedex_logo);

        previous.add(j);
        previous.add(k);

         */
        DatabaseReference userReference;
        DatabaseReference jobReference = FirebaseDatabase.getInstance().getReference("Jobs");
        if (FirebaseDatabase.getInstance().getReference("Users") == null) {
            throw new RuntimeException("A");
        } else {
            userReference = FirebaseDatabase.getInstance().getReference("Users").child(userID);
        }

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChild("masterJobs")) {
                    jobReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            master.clear();
                            for (DataSnapshot jobDatasnap : snapshot.getChildren()) {
                                Job j = jobDatasnap.getValue(Job.class);
                                master.add(j);
                            }
                            DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Users");
                            userReference.child(userID).child("masterJobs").setValue(master);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showJobs(){
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Users").child(userID);
        DatabaseReference masterReference = userReference.child("masterJobs");
        masterReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Job> liked = new ArrayList<>();
                ArrayList<Job> upcoming = new ArrayList<>();
                ArrayList<Job> recommended = new ArrayList<>();
                for (DataSnapshot jobDatasnap : snapshot.getChildren()) {
                    Job j = jobDatasnap.getValue(Job.class);


                    if (j.getchecked_in()) {
                        upcoming.add(j);
                    }
                    if (!j.getchecked_in() && j.getSaved()) {
                        liked.add(j);
                    }
                    if (!j.getSaved() && !j.getchecked_in()) {
                        recommended.add(j);
                    }

                }

                if (!recommended.isEmpty()) {
                    RecyclerView recyclerView = findViewById(R.id.recommendedRecycler);
                    JobAdapter recommendedAdapter = new JobAdapter(recommended, getApplicationContext(), jobListener);
                    recyclerView.setAdapter(recommendedAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
                }

                if (!upcoming.isEmpty()) {
                    RecyclerView upcomingView = findViewById(R.id.upcommingRecycler);
                    JobAdapter upcomingAdapter = new JobAdapter(upcoming, getApplicationContext(), jobListener);
                    upcomingView.setAdapter(upcomingAdapter);
                    upcomingView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
                }

                if (!liked.isEmpty()) {
                    RecyclerView likedView = findViewById(R.id.likedRecycler);
                    JobAdapter likedAdapter = new JobAdapter(liked, getApplicationContext(), jobListener);
                    likedView.setAdapter(likedAdapter);
                    likedView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showLibraryJobs() {
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Users").child(userID);
        DatabaseReference masterReference = userReference.child("masterJobs");
        masterReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Job> previous = new ArrayList<>();
                ArrayList<Job> training = new ArrayList<>();

                for (DataSnapshot jobDatasnap : snapshot.getChildren()) {
                    Job j = jobDatasnap.getValue(Job.class);

                    if (j.getCompleted()) {
                        previous.add(j);
                    }
                    if (j.getchecked_in()) {
                        training.add(j);
                    }
                }
                if (!previous.isEmpty()) {
                    RecyclerView previousView = findViewById(R.id.previousRecycler);
                    JobAdapter trainingAdapter = new JobAdapter(previous, getApplicationContext(), jobListener);
                    previousView.setAdapter(trainingAdapter);
                    previousView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
                }
                if (!training.isEmpty()) {
                    RecyclerView trainingView = findViewById(R.id.trainingRecycler);
                    JobAdapter trainingAdapter = new JobAdapter(training, getApplicationContext(), jobListener);
                    trainingView.setAdapter(trainingAdapter);
                    trainingView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        RecyclerView previousView = findViewById(R.id.previousRecycler);

        JobAdapter previousAdapter = new JobAdapter(previous, this, jobListener);
        previousView.setAdapter(previousAdapter);
        previousView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(recommended);
        editor.putString("listt", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("listt", null);

        Type type = new TypeToken<ArrayList<Job>>() {}.getType();
        recommended = gson.fromJson(json, type);

    }

    @Override
    protected void onStart() {
        super.onStart();
//        RecyclerView recyclerView = findViewById(R.id.recommendedRecycler);
//        JobAdapter recommendedAdapter = new JobAdapter(recommended, this, jobListener);
//        recyclerView.setAdapter(recommendedAdapter);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(HomeActivity.this, LogInActivity.class));
        }
    }
}