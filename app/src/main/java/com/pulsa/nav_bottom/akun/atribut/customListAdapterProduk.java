package com.pulsa.nav_bottom.akun.atribut;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pulsa.R;
import com.pulsa.setGet.DataProduk;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class customListAdapterProduk extends BaseAdapter {
    private Activity activity;
    public Resources res;
    LayoutInflater inflater;
    List<DataProduk> dataItems;

    public customListAdapterProduk(Activity context, ArrayList objects, Resources resLocal) {
        /********** Take passed values **********/
        this.activity = context;
        this.dataItems = objects;
        this.res = resLocal;
    }

    public int getCount() {
        return dataItems.size();//note the change
    }

    public Object getItem(int position) {
        return dataItems.get(position);//note the change
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_produk_2, null);

        DataProduk produk = dataItems.get(position);

        TextView namaProduk = (TextView) convertView.findViewById(R.id.namaProduk);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

        namaProduk.setText(produk.getJudul());
        Glide.with(inflater.getContext())
                .load(produk.getImage())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.no_image))
                .into(imageView);
        return convertView;
    }
}
