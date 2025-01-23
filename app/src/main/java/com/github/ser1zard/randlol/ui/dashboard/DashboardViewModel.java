package com.github.ser1zard.randlol.ui.dashboard;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.ViewModel;

import com.github.ser1zard.randlol.dao.DatabaseHelper;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class DashboardViewModel extends ViewModel {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;

    public HashMap<String, String> getLanes(Context context) {
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

        return lanes;
    }

    public HashMap<String, String> getChampions(Context context) {
        LinkedHashMap<String, String> champions = new LinkedHashMap<>();

        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getReadableDatabase();

        try {
            cursor = db.rawQuery("SELECT * FROM champions ORDER BY champion", null);

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

        return champions;
    }

    public HashMap<String, String> getPreferredLanes(Context context, String champion) {
        LinkedHashMap<String, String> preference = new LinkedHashMap<>();

        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getReadableDatabase();

        try {
            String[] selectionArgs = new String[]{champion};
            cursor = db.rawQuery("SELECT * FROM preferredLanes WHERE champion = ?", selectionArgs);

            if (cursor.moveToFirst()) {
                do {
                    preference.put(cursor.getString(0), cursor.getString(1));
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