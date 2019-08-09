package com.pulsa.nav_bottom.homeAtribut;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pulsa.R;
import com.pulsa.setGet.DataMenu;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter<String> {
    private final Activity context;
    ArrayList<DataMenu> dataItems;
    LayoutInflater inflater;

    public GridViewAdapter(Activity context, int textViewResourceId, ArrayList objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.dataItems = objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @SuppressLint("ViewHolder")
    public View getView(int position, View view, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
            view = inflater.inflate(R.layout.grid_item, parent, false);

        DataMenu tp = dataItems.get(position);
        TextView tvContent = view.findViewById(R.id.tvContent);
        ImageView ivImage = view.findViewById(R.id.ivImage);

        tvContent.setText(tp.getJudul());


        return view;
    }
}
