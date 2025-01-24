package com.github.ser1zard.randlol.ui.home;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.lifecycle.ViewModel;
import com.github.ser1zard.randlol.dao.DatabaseHelper;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HomeViewModel extends ViewModel {
  private DatabaseHelper dbHelper;
  private SQLiteDatabase db;
  private Cursor cursor;

  public List<String> getRandom(Context context) {
    LinkedHashMap<String, String> lanes = new LinkedHashMap<>();

    dbHelper = new DatabaseHelper(context);
    db = dbHelper.getReadableDatabase();

    try {
      cursor = db.rawQuery("SELECT * FROM lanes", null);

      if (cursor.moveToFirst()) {
        do {
          lanes.put(cursor.getString(0), cursor.getString(1));
        } while (cursor.moveToNext());
      }
    } finally {
      if (cursor != null && !cursor.isClosed()) {
        cursor.close();
      }
      dbHelper.close();
    }

    LinkedHashMap<String, String> champions = new LinkedHashMap<>();

    dbHelper = new DatabaseHelper(context);
    db = dbHelper.getReadableDatabase();

    try {
      cursor = db.rawQuery("SELECT * FROM champions", null);

      if (cursor.moveToFirst()) {
        do {
          champions.put(cursor.getString(0), cursor.getString(1));
        } while (cursor.moveToNext());
      }
    } finally {
      if (cursor != null && !cursor.isClosed()) {
        cursor.close();
      }
      dbHelper.close();
    }

    List<String> randomPick = new ArrayList<>();
    Random random = new Random();

    List<Map.Entry<String, String>> lanesList = new ArrayList<>(lanes.entrySet());
    List<Map.Entry<String, String>> championsList = new ArrayList<>(champions.entrySet());

    Map.Entry<String, String> lane = lanesList.get(random.nextInt(lanesList.size()));
    Map.Entry<String, String> champion = championsList.get(random.nextInt(championsList.size()));

    randomPick.add(lane.getValue());
    randomPick.add(champion.getValue());

    return randomPick;
  }

  public List<PreferredLane> getPreferredLanesByLane(Context context, String lane) {
    List<PreferredLane> preference = new ArrayList<>();

    dbHelper = new DatabaseHelper(context);
    db = dbHelper.getReadableDatabase();

    try {
      String[] selectionArgs = new String[] {lane};
      cursor = db.rawQuery("SELECT * FROM preferredLanes WHERE lane = ?", selectionArgs);

      if (cursor.moveToFirst()) {
        do {

          preference.add(new PreferredLane(cursor.getString(0), cursor.getString(1)));
        } while (cursor.moveToNext());
      }
    } finally {
      if (cursor != null && !cursor.isClosed()) {
        cursor.close();
      }
      dbHelper.close();
    }

    return preference;
  }
}