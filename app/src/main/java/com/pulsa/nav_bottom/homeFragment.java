package com.pulsa.nav_bottom;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pulsa.nav_bottom.homeAtribut.AndroidDataAdapter;
import com.pulsa.setGet.DataMenu;
import com.pulsa.transaksi.listrikPLN;
import com.pulsa.transaksi.ppob;
import com.pulsa.transaksi.pulsaReguler;
import com.pulsa.transaksi.voucherGame;
import com.pulsa.utils.CreateFileJson;
import com.pulsa.utils.RecyclerItemClickListener;
import com.pulsa.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.pulsa.setGet.DataPut.RESTART_SALDO_APP;

public class homeFragment extends Fragment {
    ArrayList<DataMenu> menu = new ArrayList<>();
    RecyclerView mRecyclerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment, container, false);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(iYourBroadcastReceiver, new IntentFilter(RESTART_SALDO_APP));

        menu = toStringArray();
        mRecyclerView = v.findViewById(R.id.recycler_view);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 4);
        mRecyclerView.setLayoutManager(mLayoutManager);

        AndroidDataAdapter mAdapter = new AndroidDataAdapter(getActivity(), menu);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Kategori = groupMenu;
                String groupMenu = menu.get(position).getGroupMenu();
                if (groupMenu != null) {
                    Intent i = null;
                    if (groupMenu.equalsIgnoreCase("Pulsa")) {
                        i = new Intent(getActivity(), pulsaReguler.class);
                    } else if (groupMenu.equalsIgnoreCase("Voucher Game")) {
                        i = new Intent(getActivity(), voucherGame.class);
                    } else if (groupMenu.equalsIgnoreCase("Listrik PLN")) {
                        i = new Intent(getActivity(), listrikPLN.class);
                    } else if (groupMenu.equalsIgnoreCase("PPOB")) {
                        i = new Intent(getActivity(), ppob.class);
                    } else if (groupMenu.equalsIgnoreCase("TeleponPascabayar")) {
                        i = new Intent(getActivity(), pulsaReguler.class);
                    } else {

                    }
                    if (i != null) {
                        String aksi = menu.get(position).getAksi();
                        String judul = menu.get(position).getJudul();
                        String keterangan = menu.get(position).getKeterangan();
                        i.putExtra("aksi", aksi);
                        i.putExtra("judul", judul);
                        i.putExtra("keterangan", keterangan);
                        startActivity(i);
                    } else {
                        toast("Menu belum dibuat");
                    }
                }
            }
        }));

        return v;
    }


    private void toast(String pesan) {
        Toast.makeText(getActivity(), pesan, Toast.LENGTH_SHORT).show();
    }

    private ArrayList<DataMenu> toStringArray() {
        ArrayList<DataMenu> array = new ArrayList<>();
        String produkMenu = CreateFileJson.getJsonAsset(getContext(), "menu.json");
        try {
            JSONArray jsonArray = new JSONArray(produkMenu);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectData = jsonArray.getJSONObject(i);
                String nama = jsonObjectData.getString("nama");
                String groupMenu = jsonObjectData.getString("groupMenu");
                String image = jsonObjectData.getString("gambar");
                String aksi = jsonObjectData.getString("aksi");
                String keterangan = jsonObjectData.getString("keterangan");

                DataMenu tp = new DataMenu();
                tp.setGroupMenu(groupMenu);
                tp.setJudul(nama);
                tp.setImage(image);
                tp.setAksi(aksi);
                tp.setKeterangan(keterangan);
                array.add(tp);

            }
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }

        return array;
    }

    private final BroadcastReceiver iYourBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // now you can call all your fragments method here
            try {
                if (intent.getAction() != null) {
                    if (intent.getAction().equalsIgnoreCase(RESTART_SALDO_APP)) {

                    }
                }
            } catch (Exception e) {

            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(iYourBroadcastReceiver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(iYourBroadcastReceiver);
    }
}
