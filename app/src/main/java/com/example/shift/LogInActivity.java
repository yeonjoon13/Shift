package com.example.shift;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.shift.databinding.ActivityLoginBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityLoginBinding binding;
    private SharedPreferences myPrefs;
    private Context context;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);

        // set firstFragment as default view

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getSupportActionBar().hide();
        setContentView(binding.getRoot());

//        setSupportActionBar(binding.toolbar);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        LoginScreenFragment loginScreenFragment = new LoginScreenFragment();
        fragmentTransaction.replace(R.id.fragment_content_main, loginScreenFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        context = getApplicationContext();
        myPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        // Get the parent layout
        FrameLayout parentLayout = findViewById(R.id.my_framelayout);


        // don't need the fab for now
//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void logInClick(View view) {
        EditText email = findViewById(R.id.userNameInput);
        EditText password = findViewById(R.id.passwordInput);

        String e = email.getText().toString();
        String p = password.getText().toString();

        if (TextUtils.isEmpty(e)) {
            email.setError(("Email can't be empty"));
            email.requestFocus();
        } else if (TextUtils.isEmpty((p))) {
            password.setError(("Password can't be empty"));
            password.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(e, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LogInActivity.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LogInActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(LogInActivity.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    public void registerClick(View view) {
        EditText email = findViewById(R.id.editTextTextEmailAddress);
        EditText password = findViewById(R.id.editPassword);
        EditText firstName = findViewById(R.id.editFirstName);
        EditText lastName = findViewById(R.id.editLastName);

        String email2 = email.getText().toString().trim();
        String password2 = password.getText().toString().trim();
        String firstName2 = firstName.getText().toString();
        String lastName2 = lastName.getText().toString();
        ArrayList<Job> upcomingJobs = new ArrayList<>();
        ArrayList<Job> likedJobs = new ArrayList<>();
        ArrayList<Job> recommendedJobs = new ArrayList<>();

        if (email2.isEmpty()) {
            email.setError("Email can't be empty");
        } if (password2.isEmpty()) {
            password.setError("Password can't be empty");
        } else {
            mAuth.createUserWithEmailAndPassword(email2, password2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LogInActivity.this, "Authentication created.",
                                Toast.LENGTH_SHORT).show();
                        userID = mAuth.getCurrentUser().getUid();
                        Users user = new Users(firstName2, lastName2, email2, upcomingJobs, likedJobs, recommendedJobs);
                        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("Users");
                        userReference.child(userID).setValue(user);
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_content_main, new LoginScreenFragment());
                        transaction.addToBackStack(null);
                        transaction.commit();

                    } else {
                        Toast.makeText(LogInActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }


}