package com.example.shift;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shift.ui.Question;
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
import com.google.firebase.ktx.Firebase;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    Button logOutButton;
    FirebaseAuth mAuth;
    DatabaseReference jobDBRef;
    private SearchView searchView;


    String userID;

    public ArrayList<Job> master = new ArrayList<>();

    public static ArrayList<Job> recommended = new ArrayList<>();

    public ArrayList<Job> upcoming = new ArrayList<>();

    public ArrayList<Job> liked = new ArrayList<>();

    public ArrayList<Job> training = new ArrayList<>();

    public ArrayList<Job> previous = new ArrayList<>();

    public ArrayList<Question> mcDonaldQuestions = new ArrayList<>();
    public ArrayList<Question> fedexQuestions = new ArrayList<>();

    ArrayList<Question> blankQs = new ArrayList<>();


    public OnClickJobListener jobListener = new OnClickJobListener() {
        @Override
        public void onJobClick(Job job) {

            Intent intent = new Intent(HomeActivity.this, JobDescriptionActivity.class);

            Gson gson = new Gson();
            String json = gson.toJson(job);
            intent.putExtra("job", json);
            startActivity(intent);
        }

        @Override
        public void onVideoClick(Video video) {}
    };

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(HomeActivity.this, LogInActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        Question mcdQ1 = initalizeMcDQ1();
        Question mcdQ2 = initalizeMcDQ2();
        Question mcdQ3 = initalizeMcDQ3();
        mcDonaldQuestions.add(mcdQ1);
        mcDonaldQuestions.add(mcdQ2);
        mcDonaldQuestions.add(mcdQ3);


        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.hasChild("Jobs")) {
                    jobDBRef = FirebaseDatabase.getInstance().getReference("Jobs");
                    Job j = new Job("Cashier", 1, "McDonalds", "Manage People and Learn to Have Fun", "05/08/2023", "Purple Street",
                            "2:00 pm", "$18/hr", false, 1.2, R.drawable.mcdonalds_logo, mcDonaldQuestions, 2);
                    Job k = new Job("Delivery", 4, "FedEx", "Drive and Learn to Have Fun", "05/08/2023", "Purple Street",
                            "2:00 pm", "$18/hr", true, 2.2, R.drawable.fedex_logo, blankQs, 1);
                    Job a = new Job("Package Handler", 3, "Amazon", "Chuck packages across the warehouse", "05/10/2023", "Yellow Street",
                            "4:00 am", "$20/hr", false, 1.5, R.drawable.amazon_logo, blankQs, 1);
                    Job b = new Job("Barista", 2, "Starbucks", "Cook up some coffee and serve it to caffeine to people who are addicted", "05/09/2023", "Green Street",
                            "6:00 am", "$20/hr", false, 3.6, R.drawable.starbucks_logo, blankQs, 1);
                    Job c = new Job("Boba Barista", 2, "7 Leaves", "Make some matcha thai teas for the homies", "05/10/2023", "Leaves Boulevard",
                            "2:00 pm", "$19/hr", false, 5.7, R.drawable.leaves_logo, blankQs, 1);
                    Job d = new Job("Cashier", 1, "Wendys", "Make some 4 for 4s for the people", "5/11/2023", "Red Street",
                            "1:00 am", "$16/hr", false, 8.5, R.drawable.wendys_logo, blankQs, 1);
                    Job e = new Job("Delivery", 4, "UPS", "Drive and Learn to have Fun", "05/09/2023", "Brown Street",
                            "4:00 am", "$20/hr", true, 0.8, R.drawable.ups_logo, blankQs, 1);

                    jobDBRef.push().setValue(j);
                    jobDBRef.push().setValue(k);
                    jobDBRef.push().setValue(a);
                    jobDBRef.push().setValue(b);
                    jobDBRef.push().setValue(c);
                    jobDBRef.push().setValue(d);
                    jobDBRef.push().setValue(e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.print("hello");
            }


        });
        DatabaseReference jobReference2 = FirebaseDatabase.getInstance().getReference("Jobs");
        jobReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String company;
                for (DataSnapshot d : snapshot.getChildren()) {
                    Job j = d.getValue(Job.class);
                    String jobKey = d.getKey();
                    if (!j.getHaveQuestions()) {
                        company = j.getCompany();
                        if ((company).equals("McDonalds")) {
                            HashMap<String, Object> User = new HashMap<>();
                            User.put("haveQuestions", !j.getHaveQuestions());
                            jobReference2.child(jobKey).updateChildren(User);
                            j.setHaveQuestions();
                            DatabaseReference jobReference = FirebaseDatabase.getInstance().getReference("Jobs");
                            jobReference.child(jobKey).child("questions").setValue(mcDonaldQuestions);
                        }
                        if ((company).equals("Fedex")) {
                            HashMap<String, Object> User = new HashMap<>();
                            User.put("haveQuestions", !j.getHaveQuestions());
                            j.setHaveQuestions();
                            jobReference2.child(jobKey).updateChildren(User);
                            DatabaseReference jobReference = FirebaseDatabase.getInstance().getReference("Jobs");
                            jobReference.child(jobKey).child("questions").setValue(fedexQuestions);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                if (!snapshot.hasChild("searchFilter")) {
                    Filter f = new Filter();
                    userReference.child("searchFilter").push().setValue(f);
                }

                if (!snapshot.hasChild("homeFilter")) {
                    Filter g = new Filter();
                    userReference.child("homeFilter").push().setValue(g);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private Question initalizeMcDQ3() {
        ArrayList<String> qBank = new ArrayList<>();
        qBank.add("Yes, it is on the menu!");
        qBank.add("No, but it may be back soon!");
        qBank.add("It is part of our secret menu. Would you like one?");
        qBank.add("I do not know what you are talking about it?");
        Question retQ = new Question(qBank, "What is an example of something you could say " +
                "to a customer after they ask about a menu item that is no longer on the menu?", "No, but it may be back soon!");
        return retQ;
    }

    private Question initalizeMcDQ2() {
        ArrayList<String> qBank = new ArrayList<>();
        qBank.add("We're about to close and our ice cream machine does not work");
        qBank.add("What do you want?");
        qBank.add("Hi, Welcome to Wendys! How can I help you today?");
        qBank.add("Hi, Welcome to McDonalds! How can I help you today?");
        Question retQ = new Question(qBank, "What is an example of a proper way to greet a customer", "Hi, Welcome to McDonalds! How can I help you today?");
        return retQ;
    }

    private Question initalizeMcDQ1() {
        ArrayList<String> qBank = new ArrayList<>();
        qBank.add("Cash");
        qBank.add("Calculate");
        qBank.add("Tip");
        qBank.add("Receive");
        Question retQ = new Question(qBank, "What button on the POS system opens the register", "Cash");
        return retQ;
    }


    public void showJobs(){
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Users").child(userID);
        DatabaseReference masterReference = userReference.child("masterJobs");
        DatabaseReference homeFilterReference = userReference.child("homeFilter");
        final Filter[] f = new Filter[1];

        homeFilterReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    boolean fastFood = d.child("fastFood").getValue(Boolean.class);
                    boolean cafe = d.child("cafe").getValue(Boolean.class);
                    boolean warehouse = d.child("warehouse").getValue(Boolean.class);
                    boolean transportation = d.child("transportation").getValue(Boolean.class);
                    double dist = d.child("distance").getValue(Double.class);
                    f[0] = new Filter(fastFood, cafe, warehouse, transportation, dist);
                    break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                    } else if (!j.getchecked_in() && j.getSaved()) {
                        liked.add(j);
                    } else {
                        if (j.getDistance() <= f[0].getDistance() && f[0].isIn(j.getJobType())) {
                            recommended.add(j);
                        }
                    }
                }

                RecyclerView recyclerView = findViewById(R.id.recommendedRecycler);
                JobAdapter recommendedAdapter = new JobAdapter(recommended, getApplicationContext(), jobListener);
                recyclerView.setAdapter(recommendedAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));

                RecyclerView upcomingView = findViewById(R.id.upcommingRecycler);
                JobAdapter upcomingAdapter = new JobAdapter(upcoming, getApplicationContext(), jobListener);
                upcomingView.setAdapter(upcomingAdapter);
                upcomingView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));

                RecyclerView likedView = findViewById(R.id.likedRecycler);
                JobAdapter likedAdapter = new JobAdapter(liked, getApplicationContext(), jobListener);
                likedView.setAdapter(likedAdapter);
                likedView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void filterJobs() {
        searchView = findViewById(R.id.searchV);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return true;
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

                previous.add(new Job("Barista", 2, "Starbucks", "Cook up some coffee", "04/20/2023", "Orange Street",
                        "2:00 pm", "$20/hr", false, 1.2, R.drawable.fedex_logo, blankQs, 1));

                for (DataSnapshot jobDatasnap : snapshot.getChildren()) {
                    Job j = jobDatasnap.getValue(Job.class);

                    if (j.getchecked_in() && j.getTraining()) {
                        training.add(j);
                    }
                }
                RecyclerView previousView = findViewById(R.id.previousRecycler);
                JobAdapter previousAdapter = new JobAdapter(previous, getApplicationContext(), jobListener);
                previousView.setAdapter(previousAdapter);
                previousView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));

                RecyclerView trainingView = findViewById(R.id.trainingRecycler);
                JobAdapter trainingAdapter = new JobAdapter(training, getApplicationContext(), jobListener);
                trainingView.setAdapter(trainingAdapter);
                trainingView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void homeFilterClick(View view) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.filter_popup, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        CheckBox one = popupView.findViewById(R.id.checkBoxFastFood);
        CheckBox two = popupView.findViewById(R.id.checkBoxCafe);
        CheckBox three = popupView.findViewById(R.id.checkBoxWarehouse);
        CheckBox four = popupView.findViewById(R.id.checkBoxTransportation);

        EditText dist = popupView.findViewById(R.id.distanceInput);

        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Users").child(userID);
        DatabaseReference homeFilterReference = userReference.child("homeFilter");
        homeFilterReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    one.setChecked(d.child("fastFood").getValue(Boolean.class));
                    two.setChecked(d.child("cafe").getValue(Boolean.class));
                    three.setChecked(d.child("warehouse").getValue(Boolean.class));
                    four.setChecked(d.child("transportation").getValue(Boolean.class));
                    dist.setText(String.format(Locale.US, "%.1f", d.child("distance").getValue(Double.class)));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        TextView ApplyChangesButton = popupView.findViewById(R.id.apply_changes_button);
        ImageView closeFilterPopUp = popupView.findViewById(R.id.close_filter_popup);


        ApplyChangesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Users").child(userID);
                DatabaseReference homeFilterReference = userReference.child("homeFilter");
                homeFilterReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot d : snapshot.getChildren()) {
                            Filter f = d.getValue(Filter.class);
                            HashMap<String, Object> filter = new HashMap<>();
                            filter.put("fastFood", one.isChecked());
                            filter.put("cafe", two.isChecked());
                            filter.put("warehouse", three.isChecked());
                            filter.put("transportation", four.isChecked());
                            filter.put("distance", Double.parseDouble(dist.getText().toString()));
                            homeFilterReference.child(d.getKey()).updateChildren(filter);
                        }
                        showJobs();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                showJobs();
                popupWindow.dismiss();
            }


        });

        // dismiss the popup window when touched
        closeFilterPopUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }


    private void addQuestions(ArrayList<String> questions) {

    }

    private void filterList(String text) {
        ArrayList<Job> jobs = new ArrayList<>();
        TextView textView = findViewById(R.id.searchJob);
        DatabaseReference jobReference = FirebaseDatabase.getInstance().getReference("Jobs");
        jobReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    Job j = d.getValue(Job.class);
                    if (j.getCompany().contains(text)) {
                        jobs.add(j);
                        textView.setText(j.getCompany());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}