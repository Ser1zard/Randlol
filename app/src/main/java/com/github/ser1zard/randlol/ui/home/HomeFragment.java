package com.github.ser1zard.randlol.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.github.ser1zard.randlol.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class HomeFragment extends Fragment {

  private View homeView;
  private View pickLaneView, randomPickView;
  private Stack<View> viewStack;
  private ViewGroup parentLayout;
  private Button randomPick, generate;
  private ImageView randomChampion, championRandom, laneRandom;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_home, container, false);

    viewStack = new Stack<>();
    homeView = root.findViewById(R.id.home_layout);
    pickLaneView = inflater.inflate(R.layout.fragment_pick_lane, container, false);
    randomPickView = inflater.inflate(R.layout.fragment_random_pick, container, false);

    parentLayout = (ViewGroup) homeView.getParent();

    Button randomPickButton = root.findViewById(R.id.random_pick);
    randomPickButton.setOnClickListener(v -> {
      viewStack.push(homeView);

      replaceView(pickLaneView);

      checkPreferences();
    });

    generate = root.findViewById(R.id.random);
    championRandom = root.findViewById(R.id.champion_random);
    laneRandom = root.findViewById(R.id.lane_random);

    generate.setOnClickListener(v -> {
      List<String> randomPick = new HomeViewModel().getRandom(getContext());

      championRandom.setImageResource(
          getResources().getIdentifier(randomPick.get(1).toLowerCase(), "drawable",
              getContext().getPackageName()));

      laneRandom.setImageResource(
          getResources().getIdentifier(randomPick.get(0).toLowerCase(), "drawable",
              getContext().getPackageName()));
    });

    return root;
  }

  private void replaceView(View newView) {
    if (parentLayout.getChildCount() > 0) {
      parentLayout.removeAllViews();
    }

    parentLayout.addView(newView);
  }

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);

    requireActivity().getOnBackPressedDispatcher()
        .addCallback(this, new OnBackPressedCallback(true) {
          @Override
          public void handleOnBackPressed() {
            if (!viewStack.isEmpty()) {
              View previousView = viewStack.pop();
              replaceView(previousView);
            } else {
              setEnabled(false);
              requireActivity().onBackPressed();
            }
          }
        });
  }

  private void checkPreferences() {
    HomeViewModel viewModel = new HomeViewModel();
    List<PreferredLane> preferredLanes = new ArrayList<>();
    ImageButton[] buttons = new ImageButton[5];

    buttons[0] = pickLaneView.findViewById(R.id.dialog_top);
    buttons[1] = pickLaneView.findViewById(R.id.dialog_jungle);
    buttons[2] = pickLaneView.findViewById(R.id.dialog_mid);
    buttons[3] = pickLaneView.findViewById(R.id.dialog_bot);
    buttons[4] = pickLaneView.findViewById(R.id.dialog_support);

    buttons[0].setTag("top");
    buttons[1].setTag("jungle");
    buttons[2].setTag("mid");
    buttons[3].setTag("bot");
    buttons[4].setTag("support");

    for (ImageButton button : buttons) {
      preferredLanes = viewModel.getPreferredLanesByLane(getContext(), button.getTag().toString());

      if (preferredLanes.isEmpty()) {
        button.setColorFilter(Color.argb(200, 0, 0, 0));
        button.setOnClickListener(v -> {
          Toast.makeText(getContext(), "No champion selected", Toast.LENGTH_SHORT)
              .show();
        });
      } else {
        button.clearColorFilter();
        List<PreferredLane> finalPreferredLanes = preferredLanes;
        button.setOnClickListener(v -> {
          if (randomChampion != null) {
            randomChampion.setImageResource(0);
          }

          viewStack.push(pickLaneView);
          replaceView(randomPickView);

          randomPick = randomPickView.findViewById(R.id.randomPick);
          randomPick.setOnClickListener(v1 -> {
            Random random = new Random();
            PreferredLane pick =
                finalPreferredLanes.get(random.nextInt(finalPreferredLanes.size()));

            randomChampion = randomPickView.findViewById(R.id.champion_random_pick);
            randomChampion.setImageResource(
                getResources().getIdentifier(pick.getChampion().toLowerCase(), "drawable",
                    getContext().getPackageName()));
          });
        });
      }
    }
  }
}