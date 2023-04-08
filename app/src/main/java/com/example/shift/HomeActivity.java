package com.example.shift;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shift.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    Button logOutButton;
    FirebaseAuth mAuth;

    public static ArrayList<Job> recommended = new ArrayList<>();

    public ArrayList<Job> upcoming = new ArrayList<>();

    public ArrayList<Job> liked = new ArrayList<>();

    public ArrayList<Job> completed = new ArrayList<>();

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

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_library, R.id.navigation_chat)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        recommended.add(new Job("Cashier", "McDonalds", "Manage People and Learn to Have Fun", "05/08/2023", "Purple Street",
                "2:00 pm", "$18/hr", false, R.drawable.mcdonalds_logo));

        recommended.add(new Job("Delivery", "Fedex", "Drive and Learn to Have Fun", "05/08/2023", "Purple Street",
                "2:00 pm", "$18/hr", false,R.drawable.fedex_logo));

        upcoming.add(new Job("Cashier", "McDonalds", "Manage People and Learn to Have Fun", "05/08/2023", "Purple Street",
                "2:00 pm", "$18/hr", false, R.drawable.mcdonalds_logo));

        upcoming.add(new Job("Delivery", "Fedex", "Drive and Learn to Have Fun", "05/08/2023", "Purple Street",
                "2:00 pm", "$18/hr", false,R.drawable.fedex_logo));









        //logOutButton = findViewById(R.id.logOut);
//        logOutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(HomeActivity.this, LogInActivity.class);
//                startActivity(i);
//            }
//        });

    }

    public void showJobs(){
        RecyclerView recyclerView = findViewById(R.id.recommendedRecycler);
        JobAdapter recommendedAdapter = new JobAdapter(recommended, this, jobListener);
        recyclerView.setAdapter(recommendedAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        RecyclerView upcomingView = findViewById(R.id.upcommingRecycler);
        JobAdapter upcomingAdapter = new JobAdapter(upcoming, this, jobListener);
        upcomingView.setAdapter(upcomingAdapter);

        upcomingView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
    }

    @Override
    protected void onStart() {
        super.onStart();
        RecyclerView recyclerView = findViewById(R.id.recommendedRecycler);
        JobAdapter recommendedAdapter = new JobAdapter(recommended, this, jobListener);
        recyclerView.setAdapter(recommendedAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(HomeActivity.this, LogInActivity.class));
        }
    }

}