package com.pulsa.nav_bottom.akun;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.pulsa.R;
import com.pulsa.dialog.dialogProsesTransaksi;

public class ubahNama extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.ubah_nama);
        setTitle("Ubah Nama");

        final EditText namaBaru = findViewById(R.id.namaBaru);
        Button ubah_pin = findViewById(R.id.proses);

        ubah_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NAMABARU = namaBaru.getText().toString();
                if (NAMABARU.length() > 3) {
                    String formatTransaksi = "UN." + NAMABARU;
                    dialogProsesTransaksi dialog = new dialogProsesTransaksi(ubahNama.this, formatTransaksi, "Ubah Nama", "Ubah Nama", "hidden", "!simpan & !proses & !perulangan");
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.show();

                } else {
                    dialog("Data yang anda masukkan tidak lengkap");
                }
            }
        });
    }

    public void dialog(String isi) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(isi);
        alertDialogBuilder.setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
