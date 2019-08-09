package com.pulsa.transaksi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.pulsa.R;
import com.pulsa.nav_bottom.homeAtribut.AndroidDataAdapter;
import com.pulsa.setGet.DataMenu;
import com.pulsa.setGet.DataPut;
import com.pulsa.transaksi.aksi.aksiGame;
import com.pulsa.utils.CreateFileJson;
import com.pulsa.utils.RecyclerItemClickListener;
import com.pulsa.utils.RegisterUserClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class voucherGame extends AppCompatActivity {
    private ProgressDialog pDialog;
    ArrayList<DataMenu> menu = new ArrayList<DataMenu>();
    RecyclerView mRecyclerView;
    AndroidDataAdapter adapter;
    String jsonName;
    String judul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_game);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        jsonName = getIntent().getStringExtra("aksi");
        judul = getIntent().getStringExtra("judul");
        String keterangan = getIntent().getStringExtra("keterangan");
        setTitle(judul);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(voucherGame.this, 4);
        mRecyclerView.setLayoutManager(mLayoutManager);

        adapter = new AndroidDataAdapter(voucherGame.this, menu);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(voucherGame.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Kategori = groupMenu;
                String detail = menu.get(position).getDetail();
                String hintTitle = menu.get(position).getHintTitle();
                String hint = menu.get(position).getHint();
                Intent i = new Intent(voucherGame.this, aksiGame.class);
                i.putExtra("detail", detail);
                i.putExtra("judul", judul);
                i.putExtra("hint", hint);
                i.putExtra("hintTitle", hintTitle);
                startActivity(i);
            }
        }));

        if (CreateFileJson.getData(voucherGame.this, jsonName) == null) {
            //StartSync();
            new GrabURL().execute("oke");
        } else {
            new GrabURL().execute("oke");
        }

    }

    private class GrabURL extends AsyncTask<String, Integer, ArrayList<DataMenu>> {
        @Override
        protected ArrayList<DataMenu> doInBackground(String... strings) {
            return getArrayDefault();
        }

        @Override
        protected void onPreExecute() {
            // start loading animation maybe?
            menu.clear(); // clear "old" entries (optional)
        }

        @Override
        protected void onPostExecute(ArrayList<DataMenu> items) {
            // stop the loading animation or something
            menu.addAll(items);
            adapter.notifyDataSetChanged();
        }
    }

    public ArrayList<DataMenu> getArrayDefault() {
        ArrayList<DataMenu> menu = new ArrayList<>();
        //String jsonFile = CreateFileJson.getData(this, jsonName);
        String jsonFile = CreateFileJson.getJsonAsset(this, jsonName + ".json");
        try {
            JSONObject jsonObject = new JSONObject(jsonFile);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectData = jsonArray.getJSONObject(i);
                String operator = jsonObjectData.getString("nama");
                String image = jsonObjectData.getString("image");
                String detail = jsonObjectData.getString("detail");
                String hint = jsonObjectData.getString("hint");
                String hintTitle = jsonObjectData.getString("hintTitle");

                DataMenu tp = new DataMenu();
                tp.setJudul(operator);
                tp.setImage(image);
                tp.setDetail(detail);
                tp.setHint(hint);
                tp.setHintTitle(hintTitle);
                menu.add(tp);
            }

        } catch (Exception e) {
            Log.e("JSONException", e.getMessage());
        }
        hidePDialog();
        return menu;
    }

    private void StartSync() {
        class AMBIL_PRODUK extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    CreateFileJson.saveData(voucherGame.this, s, jsonName);
                    new GrabURL().execute("oke");
                } catch (JSONException e) {
                    //Log.e("JSONException", e.getMessage());
                } catch (Exception e) {
                    //Log.e("Exception", e.getMessage());
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put(DataPut.id, judul);
                RegisterUserClass ruc = new RegisterUserClass();
                String result = ruc.sendPostRequest(data);
                return result;
            }


        }
        AMBIL_PRODUK ulc = new AMBIL_PRODUK();
        ulc.execute();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
