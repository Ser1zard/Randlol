package com.github.ser1zard.randlol.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ser1zard.randlol.R;

import java.util.List;

public class ChampionAdapter extends ArrayAdapter<Champion> {
    private final Context context;
    private final LayoutInflater inflater;
    private List<Champion> champions;

    public ChampionAdapter(Context context, int resource, List<Champion> champions) {
        super(context, resource, champions);
        this.champions = champions;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {

        if (v == null) {
            v = inflater.inflate(R.layout.list_item, parent, false);
        }

        Champion champion = champions.get(position);

        ImageView imageView = v.findViewById(R.id.item_image);
        imageView.setImageDrawable(champion.getImage());

        TextView textView = v.findViewById(R.id.item_text);
        textView.setText(
                champion.getName().substring(0, 1).toUpperCase() + champion.getName().substring(1));

        return v;
    }

    public void setData(List<Champion> champions) {
        this.champions = champions;
        notifyDataSetChanged();
    }
}
