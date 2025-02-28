package com.example.myfoodplannerapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
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
    private SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mAuth = FirebaseAuth.getInstance();
        enterEmail = view.findViewById(R.id.et_enterEmail_val);
        enterPassword = view.findViewById(R.id.et_enterPassword_val);
        forgotPassword = view.findViewById(R.id.tv_forgotPassword);
        login = view.findViewById(R.id.btn_login);
        createAnAccount = view.findViewById(R.id.tv_createAccount);

        preferences = getActivity().getSharedPreferences("userData", MODE_PRIVATE);


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
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean("isLoggedIn", true);
                                editor.apply();
                                FirebaseUser user = mAuth.getCurrentUser();

                                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment2);
                            } else {
                                Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

        createAnAccount.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signupFragment);

        });

        return view;
    }
}
