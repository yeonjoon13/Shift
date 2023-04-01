package com.example.shift;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.shift.databinding.ActivityLoginBinding;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityLoginBinding binding;
    private SharedPreferences myPrefs;
    private Context context;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set firstFragment as default view

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        mAuth = FirebaseAuth.getInstance();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FirstFragment firstFragment = new FirstFragment();
        fragmentTransaction.replace(R.id.my_framelayout, firstFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        context = getApplicationContext();
        myPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Get the parent layout
        FrameLayout parentLayout = findViewById(R.id.my_framelayout);

        // Calculate the height of the view based on the parent's height
        int parentHeight = parentLayout.getHeight();
        int viewHeight = (int) (parentHeight * 0.6);

        // Get the view and set its height
        ImageView view = findViewById(R.id.imageView);
        View view2 = findViewById(R.id.view2);
        ConstraintLayout layout = findViewById(R.id.my_constraintlayout);

        view.getLayoutParams().height = viewHeight;
        view2.getLayoutParams().height = viewHeight;
        layout.getLayoutParams().height = viewHeight;

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void logInClick(View view) {
        EditText email = (EditText) findViewById(R.id.userNameInput);
        EditText password = (EditText) findViewById(R.id.passwordInput);
        String userText = email.getText().toString();
        String passwordText = password.getText().toString();
        if (!myPrefs.contains(userText) || !myPrefs.contains(passwordText)) {
            TextView wrong = (TextView) findViewById(R.id.wrongInputText);
            wrong.setVisibility(View.VISIBLE);
            return;
        }

        String e = email.getText().toString();
        String p = password.getText().toString();

        if (!e.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(e).matches()) {
            if (!p.isEmpty()) {
                mAuth.signInWithEmailAndPassword(e, p)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(LogInActivity.this, "Login Successful.",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LogInActivity.this, HomeActivity.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LogInActivity.this, "Login Failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                password.setError("Password can't be empty");
            }
        } else if (e.isEmpty()) {
            email.setError("Email can't be empty");
        } else {
            email.setError("Enter valid email");
        }

        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    public void registerClick(View view) {
        SharedPreferences.Editor myEditor = myPrefs.edit();
        EditText email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        EditText password = (EditText) findViewById(R.id.editPassword);
        //if (email.getText().toString().trim().length() == 0 || password.getText().toString().trim().length() == 0) {
          //  Toast.makeText(LogInActivity.this, "Hello, world!", Toast.LENGTH_SHORT).show();
            //return;
        //}
        //myEditor.putString("username", email.getText().toString());
        //myEditor.putString("password", password.getText().toString());
        //myEditor.apply();

        String email2 = email.getText().toString().trim();
        String password2 = password.getText().toString().trim();

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
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.SecondFragment, new FirstFragment());
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