package com.pulsa.nav_bottom.akun;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pulsa.R;
import com.pulsa.nav_bottom.akun.atribut.customListAdapterProduk;
import com.pulsa.setGet.DataPut;
import com.pulsa.setGet.DataProduk;
import com.pulsa.utils.RegisterUserClass;
import com.pulsa.webView.akunWebview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class panduanApp extends AppCompatActivity {
    private ProgressDialog pDialog;
    ListView listView;
    ArrayList<DataProduk> array = new ArrayList<DataProduk>();
    customListAdapterProduk adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Panduan Aplikasi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.panduan_app);

        listView = (ListView) findViewById(R.id.list);
        array = new ArrayList<>();
        getUserInformation();

        adapter = new customListAdapterProduk(this, array, getResources());
        listView.setAdapter(adapter);
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(panduanApp.this, akunWebview.class);
                i.putExtra("url", array.get(position).getUrl());
                i.putExtra("title", array.get(position).getJudul());
                startActivity(i);
            }
        });

    }

    private void getUserInformation() {
        class panduanAplikasi extends AsyncTask<String, Void, String> {
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
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObjectData = jsonArray.getJSONObject(i);
                        String nama = jsonObjectData.getString("nama");
                        String url = jsonObjectData.getString("url");
                        String image = jsonObjectData.getString("image");
                        DataProduk game = new DataProduk();
                        game.setJudul(nama);
                        game.setImage(image);
                        game.setUrl(url);
                        array.add(game);
                    }
                } catch (JSONException e) {
                    Log.e("data", e.getMessage());
                } catch (Exception e) {
                    Log.e("data", e.getMessage());
                }
                adapter.notifyDataSetChanged();
                hidePDialog();
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put(DataPut.id, DataPut.caraTransaksi);
                RegisterUserClass ruc = new RegisterUserClass();
                String result = ruc.sendPostRequest(data);
                return result;
            }
        }

        panduanAplikasi ulc = new panduanAplikasi();
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
