package com.example.myfoodplannerapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    private FirebaseAuth mAuth;
    private EditText enterEmail;
    private EditText enterPassword;
    private TextView forgotPassword;
    private Button login;
    private TextView createAnAccount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mAuth = FirebaseAuth.getInstance();
        enterEmail = view.findViewById(R.id.et_enterEmail);
        enterPassword = view.findViewById(R.id.et_enterPassword);
        forgotPassword = view.findViewById(R.id.tv_forgotPassword);
        login = view.findViewById(R.id.btn_login);
        createAnAccount = view.findViewById(R.id.tv_createAccount);

        login.setOnClickListener(v -> {
            String email = enterEmail.getText().toString();
            String password = enterPassword.getText().toString();

            if (email.isEmpty()) {
                enterEmail.setError("Email is required!");
                enterEmail.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                enterPassword.setError("Password is required!");
                enterPassword.requestFocus();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("loginTag", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment2);
                            } else {
                                Log.w("loginTag", "signInWithEmail:failure", task.getException());
                                Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

        createAnAccount.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), SignupFragment.class);
            startActivity(intent);
        });

        return view;
    }
}
