package com.github.ser1zard.randlol.ui.dashboard;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.ser1zard.randlol.R;
import com.github.ser1zard.randlol.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardFragment extends Fragment {
    private FragmentDashboardBinding binding;
    private DashboardViewModel dashboardViewModel;
    private ListView championsList;
    private ChampionAdapter adapter;
    private HashMap<String, String> championsDB;
    private HashMap<String, String> preferenceDB;
    private List<Champion> champions;
    private List<Champion> filteredChampions;
    private SearchView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        championsList = binding.championsList;
        searchView = binding.searchView;
        displayChampions();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void displayChampions() {
        champions = new ArrayList<>();
        championsDB = new HashMap<>();
        dashboardViewModel = new DashboardViewModel();

        championsDB = dashboardViewModel.getChampions(getContext());
        Log.d("Champions", championsDB.toString());

        for (Map.Entry<String, String> entry : championsDB.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            Log.d("Champions", "Key: " + key + ", Value: " + value);
            int resourceId = getContext().getResources().getIdentifier(value, "drawable", getContext().getPackageName());
            Drawable drawable = getContext().getResources().getDrawable(resourceId);

            Champion champion = new Champion(key, drawable);

            champions.add(champion);
        }

        // Set the filtered list initially to all champions
        filteredChampions = new ArrayList<>(champions);

        adapter = new ChampionAdapter(getContext(), R.layout.list_item, filteredChampions);
        championsList.setAdapter(adapter);

        championsList.setOnItemClickListener((parent, view, position, id) -> {
            onItemClick(view, position); // Pass the position
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filteredChampions = filterChampions(newText); // Update filtered list
                adapter = new ChampionAdapter(getContext(), R.layout.list_item, filteredChampions);
                championsList.setAdapter(adapter);
                return true;
            }
        });
    }

    private List<Champion> filterChampions(String query) {
        List<Champion> filteredList = new ArrayList<>();
        for (Champion champion : champions) {
            if (champion.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(champion);
            }
        }
        return filteredList;
    }

    public void onItemClick(View view, int position) {
        // Use the position from the filtered list, and find the correct champion
        Champion selectedChampion = filteredChampions.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select lanes for " + selectedChampion.getName());

        View dialogView = checkPreferredLanes(selectedChampion);
        builder.setView(dialogView);

        builder.setPositiveButton("OK", (dialog, which) -> {
            //
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            //
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private View checkPreferredLanes(Champion champion) {
        preferenceDB = new HashMap<>();
        dashboardViewModel = new DashboardViewModel();

        preferenceDB = dashboardViewModel.getPreferredLanes(getContext(), champion.getName());

        LayoutInflater dialogInflater = LayoutInflater.from(getContext());
        View dialogView = dialogInflater.inflate(R.layout.dialog_selection, null);

        ImageView top = dialogView.findViewById(R.id.dialog_top);
        ImageView jungle = dialogView.findViewById(R.id.dialog_jungle);
        ImageView mid = dialogView.findViewById(R.id.dialog_mid);
        ImageView bot = dialogView.findViewById(R.id.dialog_bot);
        ImageView support = dialogView.findViewById(R.id.dialog_support);

        if (!preferenceDB.isEmpty()) {
            //
        }

        return dialogView;
    }
}