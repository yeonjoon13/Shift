package com.example.shift;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.shift.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private String CompanyName;
    private String roleName1;
    private String JobDesc;
    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textCompany = binding.textCompany;
        TextView roleName = binding.roleName;
        TextView textRoleDesc = binding.textRoleName;
        String companyName = savedInstanceState.getString(CompanyName);
        textCompany.setText(companyName);
        String role = savedInstanceState.getString(roleName1);
        roleName.setText(role);
        String roleDesc = savedInstanceState.getString(JobDesc);
        roleName.setText(roleDesc);
        binding.buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}