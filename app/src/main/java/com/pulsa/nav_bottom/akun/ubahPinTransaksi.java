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

public class ubahPinTransaksi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.tools_pin);
        setTitle("Ubah Pin");

        final EditText pinBaru = findViewById(R.id.pinBaru);
        final EditText pinBaru2 = findViewById(R.id.pinBaru2);
        Button ubah_pin = findViewById(R.id.ubah_pin);

        ubah_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String PINBARU = pinBaru.getText().toString();
                String PINBARU2 = pinBaru2.getText().toString();
                if (PINBARU.length() > 3 && PINBARU2.length() > 3) {
                    if (PINBARU.equals(PINBARU2)) {
                        String formatTransaksi = "GP." + PINBARU;

                        dialogProsesTransaksi dialog = new dialogProsesTransaksi(ubahPinTransaksi.this, formatTransaksi, "Ubah Pin", "Ubah Pin transaksi", "Mengubah pin transaksi", "!simpan & !proses & !perulangan");
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.show();
                    } else {
                        dialog("Pin baru tidak sama");
                    }
                } else {
                    dialog("Isi pin dengan benar, minimal 4 karakter");
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
