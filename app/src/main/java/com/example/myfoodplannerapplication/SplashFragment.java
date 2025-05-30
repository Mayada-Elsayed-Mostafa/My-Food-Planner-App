package com.example.myfoodplannerapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myfoodplannerapplication.databinding.FragmentSplashBinding;

public class SplashFragment extends Fragment {

    private static final int SPLASH_DELAY = 3000;
    SharedPreferences preferences;
    private FragmentSplashBinding binding;

    public SplashFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentSplashBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.lottieSplash.setAnimation(R.raw.splash_animation);
        binding.lottieSplash.playAnimation();

        binding.title.post(() -> {
            LinearGradient gradient = new LinearGradient(0, 0, 0, binding.title.getHeight(), ContextCompat.getColor(getContext(), R.color.my_light_primary), ContextCompat.getColor(getContext(), R.color.my_light_tertiary), Shader.TileMode.CLAMP);
            binding.title.getPaint().setShader(gradient);
            binding.title.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            binding.title.setShadowLayer(8, 0, 0, ContextCompat.getColor(getContext(), R.color.accent_yellow));
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferences = requireActivity().getSharedPreferences("userData", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            new Handler().postDelayed(() -> Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_homeFragment), SPLASH_DELAY);
        } else {
            new Handler().postDelayed(() -> Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_welcomeFragment), SPLASH_DELAY);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
