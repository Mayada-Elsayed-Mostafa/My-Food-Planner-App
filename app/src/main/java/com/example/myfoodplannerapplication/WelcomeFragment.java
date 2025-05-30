package com.example.myfoodplannerapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myfoodplannerapplication.databinding.FragmentWelcomeBinding;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeFragment extends Fragment {

    private FirebaseAuth mAuth;
    private SharedPreferences preferences;
    private FragmentWelcomeBinding binding;

    public WelcomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false);
        preferences = requireActivity().getSharedPreferences("userData", MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.progressBar.setVisibility(View.GONE);

        binding.btnSignup.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_signupFragment));

        binding.tvLogin.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_loginFragment));

        binding.btnSkip.setOnClickListener(v -> {
            binding.progressBar.setVisibility(View.VISIBLE);

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.apply();

            mAuth.signInAnonymously().addOnCompleteListener(requireActivity(), task -> {
                binding.progressBar.setVisibility(View.GONE);

                if (task.isSuccessful()) {
                    Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_homeFragment);
                } else {
                    Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
