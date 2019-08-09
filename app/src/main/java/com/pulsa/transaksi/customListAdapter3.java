package com.pulsa.transaksi;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.pulsa.R;
import com.pulsa.setGet.DataProduk;
import com.pulsa.utils.NumberFormat;

import java.util.ArrayList;
import java.util.List;

public class customListAdapter3 extends BaseAdapter implements Filterable {
    private Activity activity;
    public Resources res;
    LayoutInflater inflater;
    List<DataProduk> dataItems;
    public static ArrayList<DataProduk> listFiltered = new ArrayList<DataProduk>();

    public customListAdapter3(Activity tagihanListrik, ArrayList objects, Resources resLocal) {
        /********** Take passed values **********/
        this.activity = tagihanListrik;
        this.dataItems = objects;
        this.res = resLocal;
        listFiltered = objects;
    }

    public int getCount() {
        return listFiltered.size();//note the change
    }

    public Object getItem(int position) {
        return listFiltered.get(position);//note the change
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
            convertView = inflater.inflate(R.layout.list_produk_1a, null);

        DataProduk produk = listFiltered.get(position);

        TextView namaProduk = convertView.findViewById(R.id.namaProduk);
        TextView harga = convertView.findViewById(R.id.harga);
        TextView keterangan = convertView.findViewById(R.id.kateranganProduk);
        TextView kodeProduk = convertView.findViewById(R.id.kodeProduk);

        namaProduk.setText(produk.getJudul());
        harga.setText(NumberFormat.setNumberformat(produk.getFee()));
        kodeProduk.setText(produk.getKodeProduk());
        if (produk.getKeterangan() == null) {
            keterangan.setVisibility(View.GONE);
        } else {
            keterangan.setText(produk.getKeterangan());
        }

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint == null || constraint.length() == 0) {
                    //no constraint given, just return all the data. (no search)
                    results.count = dataItems.size();
                    results.values = dataItems;
                } else {//do the search
                    List<DataProduk> resultsData = new ArrayList<>();
                    String searchStr = constraint.toString().toUpperCase();

                    for (DataProduk s : dataItems)
                        if (s.getJudul().toUpperCase().contains(searchStr) || s.getQuota().toUpperCase().contains(searchStr))
                            resultsData.add(s);
                    results.count = resultsData.size();
                    results.values = resultsData;
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listFiltered = (ArrayList<DataProduk>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
