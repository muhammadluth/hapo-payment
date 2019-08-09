package com.pulsa.transaksi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pulsa.R;
import com.pulsa.dialog.dialogProsesTransaksi;
import com.pulsa.setGet.DataProduk;
import com.pulsa.setGet.DataPut;
import com.pulsa.utils.CreateFileJson;
import com.pulsa.utils.NonScrollListView;
import com.pulsa.utils.PermissionAllow;
import com.pulsa.utils.RegisterUserClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.pulsa.utils.PermissionAllow.PERMISSION_REQUEST_CODE;

public class ppob extends AppCompatActivity {
    ArrayList<DataProduk> array = new ArrayList<DataProduk>();
    boolean cari = false;
    private ProgressDialog pDialog;
    String jsonName;
    String judul;
    customListAdapter3 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_ppob);
        jsonName = getIntent().getStringExtra("aksi");
        judul = getIntent().getStringExtra("judul");
        String keterangan = getIntent().getStringExtra("keterangan");
        setTitle(judul);

        LinearLayout layoutKeterangan = findViewById(R.id.layoutKeterangan);
        TextView tvKeterangan = findViewById(R.id.keterangan);

        if (keterangan != null) {
            layoutKeterangan.setVisibility(View.VISIBLE);
            tvKeterangan.setText(keterangan);
        }

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();

        final NonScrollListView listView = findViewById(R.id.list);
        final EditText idPelanggan = findViewById(R.id.idPelanggan);
        ImageView pick = findViewById(R.id.pick);
        ImageView clear_number = findViewById(R.id.clear_number);

        final LinearLayout doodle = findViewById(R.id.doodle);
        ImageView doodle_img = findViewById(R.id.doodle_img);
        TextView doodle_keterangan = findViewById(R.id.doodle_keterangan);
        doodle_img.setImageDrawable(getResources().getDrawable(R.drawable.ic__dwn_contacts));
        doodle_keterangan.setText(getResources().getString(R.string.doodle_iput_no_telepon));
        doodle.setVisibility(View.VISIBLE);

        adapter = new customListAdapter3(ppob.this, array, getResources());
        listView.setAdapter(adapter);
        doodle.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);

        if (CreateFileJson.getData(ppob.this, jsonName) == null) {
            //StartSync();
            new GrabURL().execute("oke");
        } else {
            new GrabURL().execute("oke");
        }

        clear_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idPelanggan.setText("");
            }
        });
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionAllow rp = new PermissionAllow();
                if (!rp.checkPermission(ppob.this)) {
                    rp.requestPermission(ppob.this);
                } else {
                    try {
                        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, uri);
                        startActivityForResult(contactPickerIntent, 1);
                    } catch (Exception e) {
                        Toast.makeText(ppob.this, "PIC 1", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idPel = idPelanggan.getText().toString();
                DataProduk clickvg = array.get(position);
                String detailProduk = clickvg.getKeterangan();
                String kodeProduk = clickvg.getKodeProduk();

                String format = kodeProduk + "." + idPelanggan;
                if (kodeProduk.length() > 0 && idPel.length() > 0) {
                    dialogProsesTransaksi dialog = new dialogProsesTransaksi(ppob.this, format, array.get(position).getJudul(), idPel, detailProduk, "simpan & proses & perulangan");
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.show();
                }
            }
        });
    }

    private class GrabURL extends AsyncTask<String, Integer, ArrayList<DataProduk>> {
        @Override
        protected ArrayList<DataProduk> doInBackground(String... strings) {
            return getArrayDefault();
        }

        @Override
        protected void onPreExecute() {
            // start loading animation maybe?
            array.clear(); // clear "old" entries (optional)
        }

        @Override
        protected void onPostExecute(ArrayList<DataProduk> items) {
            // stop the loading animation or something
            array.addAll(items);
            adapter.notifyDataSetChanged();
        }
    }

    public ArrayList<DataProduk> getArrayDefault() {
        ArrayList<DataProduk> array = new ArrayList<>();
        //String jsonFile = CreateFileJson.getData(this, jsonName);
        String jsonFile = CreateFileJson.getJsonAsset(this, jsonName + ".json");
        try {
            JSONObject jsonObject = new JSONObject(jsonFile);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject objectDetail = jsonArray.getJSONObject(i);
                String kodeTransaksi = objectDetail.getString("kodeTransaksi");
                String fee = objectDetail.getString("fee");
                String keterangan = objectDetail.getString("keterangan");
                String operator = objectDetail.getString("nama");
                String kodeCek = objectDetail.getString("kodeCek");

                DataProduk tp = new DataProduk();
                tp.setJudul(operator);
                tp.setKodeProduk(kodeTransaksi);
                tp.setKeterangan(keterangan);
                tp.setFee(fee);
                tp.setKodeCek(kodeCek);
                array.add(tp);
            }

        } catch (Exception e) {
            Log.e("JSONException", e.getMessage());
        }
        hidePDialog();
        return array;
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
                    CreateFileJson.saveData(ppob.this, s, jsonName);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(ppob.this, "Tidak dapat mengakses kontak", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Activity activity = ppob.this;
        String phone = "";
        Cursor contacts = null;
        try {
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case 1:

                        // gets the uri of selected contact
                        Uri result = data.getData();
                        // get the contact id from the Uri (last part is contact
                        // id)
                        String id = result.getLastPathSegment();
                        // queries the contacts DB for phone no
                        contacts = activity.getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone._ID + "=?",
                                new String[]{id}, null);
                        // gets index of phone no
                        int phoneIdx = contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);
                        if (contacts.moveToFirst()) {
                            // gets the phone no
                            phone = contacts.getString(phoneIdx);
                            phone = phone.replace("+62", "0").replace("-", "").replace(" ", "").trim();
                            EditText phoneTxt = activity.findViewById(R.id.nomorHP);
                            cari = false;
                            // assigns phone no to EditText field phoneno
                            phoneTxt.setText(phone);
                        } else {
                            Toast.makeText(activity, "error", Toast.LENGTH_LONG).show();
                        }
                        break;

                }

            } else {
                Toast.makeText(activity, R.string.belumdipilih, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (contacts != null) {
                contacts.close();
            }
        }
    }
}
