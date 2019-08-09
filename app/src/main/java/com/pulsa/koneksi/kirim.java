package com.pulsa.koneksi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class kirim extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "mengirim transaksi", Toast.LENGTH_SHORT).show();
        finish();
    }
}
