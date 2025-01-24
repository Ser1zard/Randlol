package com.github.ser1zard.randlol.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.ser1zard.randlol.R;
import com.github.ser1zard.randlol.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends Fragment {

    private final HomeViewModel homeViewModel = new HomeViewModel();
    private FragmentHomeBinding binding;
    private List<String> randomPick;
    private Button random;
    private ImageView championRandom, laneRandom;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        championRandom = root.findViewById(R.id.champion_random);
        laneRandom = root.findViewById(R.id.lane_random);

        random = root.findViewById(R.id.random);
        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomPick = homeViewModel.getRandom(getContext());

                championRandom.setImageResource(getResources().getIdentifier(randomPick.get(1), "drawable", getContext().getPackageName()));
                laneRandom.setImageResource(getResources().getIdentifier(randomPick.get(0), "drawable", getContext().getPackageName()));
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}