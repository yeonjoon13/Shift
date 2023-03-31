package com.example.shift;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.shift.databinding.ActivityLoginBinding;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set firstFragment as default view

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

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

    public void logInClick(View view) {
        EditText username = (EditText) findViewById(R.id.userNameInput);
        EditText password = (EditText) findViewById(R.id.passwordInput);
        String userText = username.getText().toString();
        String passwordText = password.getText().toString();
        if (!myPrefs.contains(userText) || !myPrefs.contains(passwordText)) {
            TextView wrong = (TextView) findViewById(R.id.wrongInputText);
            wrong.setVisibility(View.VISIBLE);
            return;
        }

        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    public void registerClick(View view) {
        SharedPreferences.Editor myEditor = myPrefs.edit();
        EditText username = (EditText) findViewById(R.id.userNameInput);
        EditText password = (EditText) findViewById(R.id.passwordInput);
        if (username.getText().toString().trim().length() == 0 || password.getText().toString().trim().length() == 0) {
            Toast.makeText(LogInActivity.this, "Hello, world!", Toast.LENGTH_SHORT).show();
            return;
        }
        myEditor.putString("username", username.getText().toString());
        myEditor.putString("password", password.getText().toString());
        myEditor.apply();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.SecondFragment, new FirstFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}