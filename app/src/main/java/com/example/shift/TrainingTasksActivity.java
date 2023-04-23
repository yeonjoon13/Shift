package com.example.shift;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import com.example.shift.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;

public class TrainingTasksActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();


    }
}