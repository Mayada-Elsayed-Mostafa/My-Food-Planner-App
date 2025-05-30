package com.example.myfoodplannerapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myfoodplannerapplication.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {
    private FirebaseAuth mAuth;
    private SharedPreferences preferences;
    private FragmentLoginBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        preferences = requireActivity().getSharedPreferences("userData", MODE_PRIVATE);

        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etEnterEmailVal.getText().toString().trim();
            String password = binding.etEnterPasswordVal.getText().toString().trim();

            if (email.isEmpty()) {
                binding.etEnterEmailVal.setError("Email is required!");
                binding.etEnterEmailVal.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.etEnterEmailVal.setError("Enter a valid email");
                binding.etEnterEmailVal.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                binding.etEnterPasswordVal.setError("Password is required!");
                binding.etEnterPasswordVal.requestFocus();
                return;
            }

            binding.progressBar.setVisibility(View.VISIBLE);
            binding.btnLogin.setEnabled(false);

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity(), task -> {
                        binding.progressBar.setVisibility(View.GONE);
                        binding.btnLogin.setEnabled(true);

                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean("isLoggedIn", true);
                                editor.putString("email", user.getEmail());
                                editor.putString("name", user.getDisplayName()); // إذا متوفر
                                editor.apply();
                            }

                            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment2);
                        } else {
                            Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        binding.tvCreateAccount.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signupFragment);

        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
