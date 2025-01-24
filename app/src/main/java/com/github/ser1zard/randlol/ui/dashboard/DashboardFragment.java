package com.github.ser1zard.randlol.ui.dashboard;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.github.ser1zard.randlol.R;
import com.github.ser1zard.randlol.dao.DatabaseHelper;
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
  private ImageButton top, jungle, mid, bot, support;
  private final String tag = "TOGGLED", unTag = "UNTOGGLED";

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    DashboardViewModel dashboardViewModel =
        new ViewModelProvider(this).get(DashboardViewModel.class);

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

    for (Map.Entry<String, String> entry : championsDB.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();

      int resourceId = getContext().getResources()
          .getIdentifier(value, "drawable", getContext().getPackageName());
      Drawable drawable = getContext().getResources().getDrawable(resourceId);

      Champion champion = new Champion(key, drawable);

      champions.add(champion);
    }

    filteredChampions = new ArrayList<>(champions);

    adapter = new ChampionAdapter(getContext(), R.layout.list_item, filteredChampions);
    championsList.setAdapter(adapter);

    championsList.setOnItemClickListener((parent, view, position, id) -> {
      onItemClick(view, position);
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
    Champion selectedChampion = filteredChampions.get(position);

    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    builder.setTitle("Select lanes for " + selectedChampion.getName());

    View dialogView = checkPreferredLanes(selectedChampion);
    builder.setView(dialogView);

    HashMap<ImageButton, Boolean> startingState = new HashMap<>();
    startingState.put(top, top.getTag() == tag);
    startingState.put(jungle, jungle.getTag() == tag);
    startingState.put(mid, mid.getTag() == tag);
    startingState.put(bot, bot.getTag() == tag);
    startingState.put(support, support.getTag() == tag);

    ImageButton[] buttons = {top, jungle, mid, bot, support};

    for (ImageButton button : buttons) {
      button.setOnClickListener(v -> {
        if (button.getTag().equals(tag)) {
          button.setTag(unTag);
          button.animate().alpha(0.5f);
        } else {
          button.setTag(tag);
          button.animate().alpha(1f);
        }
      });
    }

    HashMap<ImageButton, String> buttonsMap = new HashMap<>();
    buttonsMap.put(top, "top");
    buttonsMap.put(jungle, "jungle");
    buttonsMap.put(mid, "mid");
    buttonsMap.put(bot, "bot");
    buttonsMap.put(support, "support");

    builder.setPositiveButton("OK", (dialog, which) -> {
      DatabaseHelper dbHelper = new DatabaseHelper(getContext());
      SQLiteDatabase db = dbHelper.getWritableDatabase();
      db.beginTransaction();

      try {
        for (Map.Entry<ImageButton, String> button : buttonsMap.entrySet()) {
          String exec = null;

          SQLiteStatement statement = null;
          if (button.getKey().getTag().equals(tag)) {
            exec = "INSERT OR IGNORE INTO preferredLanes (champion, lane) VALUES (?, ?)";
            statement = db.compileStatement(exec);
            statement.bindString(1, selectedChampion.getName());
            statement.bindString(2, button.getValue());
            statement.executeInsert();
          } else if (button.getKey().getTag().equals(unTag)) {
            exec = "DELETE FROM preferredLanes WHERE champion = ? AND lane = ?";
            statement = db.compileStatement(exec);
            statement.bindString(1, selectedChampion.getName());
            statement.bindString(2, button.getValue());
            statement.executeUpdateDelete();
          }

          if (statement != null) {
            statement.close();  // Close the statement after execution.
          }
        }

        db.setTransactionSuccessful();
      } finally {
        db.endTransaction();
        dbHelper.close();
      }
    });
    builder.setNegativeButton("Cancel", (dialog, which) -> {

    });

    AlertDialog dialog = builder.create();
    dialog.show();
  }

  private View checkPreferredLanes(Champion champion) {
    preferenceDB = new HashMap<>();
    dashboardViewModel = new DashboardViewModel();

    preferenceDB = dashboardViewModel.getPreferredLanes(getContext(), champion.getName());

    Log.d("DEBUG", "HELLO WORLD!");
    Log.d("DEBUG", "checkPreferredLanes: " + preferenceDB.toString());

    LayoutInflater dialogInflater = LayoutInflater.from(getContext());
    View dialogView = dialogInflater.inflate(R.layout.dialog_selection, null);

    top = dialogView.findViewById(R.id.dialog_top);
    jungle = dialogView.findViewById(R.id.dialog_jungle);
    mid = dialogView.findViewById(R.id.dialog_mid);
    bot = dialogView.findViewById(R.id.dialog_bot);
    support = dialogView.findViewById(R.id.dialog_support);

    HashMap<ImageButton, String> buttons = new HashMap<>();
    buttons.put(top, "top");
    buttons.put(jungle, "jungle");
    buttons.put(mid, "mid");
    buttons.put(bot, "bot");
    buttons.put(support, "support");

    for (Map.Entry<ImageButton, String> current : buttons.entrySet()) {

      if (preferenceDB.containsKey(current.getValue())) {
        current.getKey().animate().alpha(1f);
        current.getKey().setTag(tag);
      } else {
        current.getKey().animate().alpha(0.5f);
        current.getKey().setTag(unTag);
      }
    }

    return dialogView;
  }
}