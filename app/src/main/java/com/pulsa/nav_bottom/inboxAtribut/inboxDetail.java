package com.pulsa.nav_bottom.inboxAtribut;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.pulsa.R;
import com.pulsa.db.dbinbox;

import java.util.HashMap;

public class inboxDetail extends AppCompatActivity {
    dbinbox mHelper;
    SQLiteDatabase mDb;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inbox_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Inbox Detail");
        mHelper = new dbinbox(this);
        String id = getIntent().getStringExtra("id");
        int ids = Integer.parseInt(id);
        if (id != null) {
            tampil_isi_berdasarkan_id(ids);
        } else {

        }
    }

    String message;
    public void tampil_isi_berdasarkan_id(int ids) {
        mDb = mHelper.getWritableDatabase();
        HashMap<String, String> hashMapBiodata = new HashMap<String, String>();
        Cursor cursor = mDb.rawQuery("SELECT * FROM tblinbox WHERE _id=" + ids + "", null);
        if (cursor.moveToFirst()) {
            do {
                hashMapBiodata.put("isi", cursor.getString(1));
            } while (cursor.moveToNext());
        }
        message = hashMapBiodata.get("isi");
        TextView view = (TextView) findViewById(R.id.isi);
        view.setText(message);
        view.setMovementMethod(LinkMovementMethod.getInstance());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
