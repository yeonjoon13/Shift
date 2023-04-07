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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.view.Menu;
import android.view.MenuItem;
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

        // get rid of actionbar
        if(this.getSupportActionBar()!=null)
            this.getSupportActionBar().hide();

        // set firstFragment as default view

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
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
        EditText email = (EditText) findViewById(R.id.userNameInput);
        EditText password = (EditText) findViewById(R.id.passwordInput);
        /*
        if (!myPrefs.contains(userText) || !myPrefs.contains(passwordText)) {
            TextView wrong = (TextView) findViewById(R.id.wrongInputText);
            wrong.setVisibility(View.VISIBLE);
            return;
        }*/

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
        SharedPreferences.Editor myEditor = myPrefs.edit();
        EditText email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        EditText password = (EditText) findViewById(R.id.editPassword);
        EditText firstName = (EditText) findViewById(R.id.editFirstName);
        EditText lastName = (EditText) findViewById(R.id.editLastName);

        //if (email.getText().toString().trim().length() == 0 || password.getText().toString().trim().length() == 0) {
          //  Toast.makeText(LogInActivity.this, "Hello, world!", Toast.LENGTH_SHORT).show();
            //return;
        //}
        //myEditor.putString("username", email.getText().toString());
        //myEditor.putString("password", password.getText().toString());
        //myEditor.apply();

        String email2 = email.getText().toString().trim();
        String password2 = password.getText().toString().trim();
        String firstName2 = firstName.getText().toString();
        String lastName2 = lastName.getText().toString();
        ArrayList<Job> upcomingJobs = new ArrayList<>();
        ArrayList<Job> likedJobs = new ArrayList<>();

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
                        DocumentReference documentReference = fStore.collection("users").document(userID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("fName", firstName2);
                        user.put("lName", lastName2);
                        user.put("email", email2);
                        user.put("upcomingJobs", upcomingJobs);
                        user.put("likedJobs", likedJobs);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG", "onSuccess: user profile is created for " + userID);
                            }
                        });
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