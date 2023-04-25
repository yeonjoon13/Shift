package com.example.shift;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shift.databinding.FragmentStartQuizBinding;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StartQuiz#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartQuiz extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String CompanyName;
    private String roleName1;
    private String JobDesc;

    private FragmentStartQuizBinding binding;

    public StartQuiz() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param company Parameter 1.
     * @param role Parameter 2.
     * @return A new instance of fragment StartQuiz.
     */
    // TODO: Rename and change types and number of parameters
    public static StartQuiz newInstance(String company, String role, String roleDesc) {
        StartQuiz fragment = new StartQuiz();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, company);
        args.putString(ARG_PARAM2, role);
        args.putString(ARG_PARAM3, roleDesc);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            CompanyName = getArguments().getString(ARG_PARAM1);
            roleName1 = getArguments().getString(ARG_PARAM2);
            JobDesc = getArguments().getString(ARG_PARAM3);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentStartQuizBinding.inflate(inflater, container, false);
        return binding.getRoot();
        //return inflater.inflate(R.layout.fragment_start_quiz, container, false);


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textCompany = binding.textCompany;
        TextView roleName = binding.roleName;
        TextView textRoleDesc = binding.textRoleDesc;
        String companyName = savedInstanceState.getString(CompanyName);
        textCompany.setText(companyName);
        String role = savedInstanceState.getString(roleName1);
        roleName.setText(role);
        String roleDesc = savedInstanceState.getString(JobDesc);
        roleName.setText(roleDesc);

        binding.buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((StartQuiz) getParentFragment()).JobDesc(view);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                StartQuiz first = new StartQuiz();
                fragmentTransaction.replace(R.id.fragment_content_main, first);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((StartQuiz) getParentFragment()).JobDesc(view);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                StartQuiz first = new StartQuiz();
                fragmentTransaction.replace(R.id.fragment_content_main, first);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

    }

    public void goBack(View view) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}