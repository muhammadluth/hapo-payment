package com.pulsa.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pulsa.db.dbOutbox;
import com.pulsa.koneksi.kirim;
import com.pulsa.utils.CommonMethods;
import com.pulsa.R;

public class dialogProsesTransaksi extends Dialog {
    private Activity context;
    String format;
    String aksi;
    String judul;
    String tujuan;
    String formatAsli;
    String detailProduk;

    public dialogProsesTransaksi(Activity context, String format, String judul, String tujuan, String detailProduk, String aksi) {
        super(context);
        this.context = context;
        this.format = format;
        this.aksi = aksi;
        this.judul = judul;
        this.tujuan = tujuan;
        this.detailProduk = detailProduk;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_proses_transaksi);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        final TextView title = (TextView) findViewById(R.id.tv);
        final TextView keterangan = (TextView) findViewById(R.id.keterangan);
        final EditText pin = (EditText) findViewById(R.id.pin);
        ImageView close = (ImageView) findViewById(R.id.close);
        final CheckBox checkPerulangan = (CheckBox) findViewById(R.id.checkPerulangan);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        if (detailProduk.equalsIgnoreCase("hidden")) {
            keterangan.setVisibility(View.GONE);
        }
        keterangan.setText(detailProduk);

        final Button btn = (Button) findViewById(R.id.btn);
        title.setText("Proses Transaksi");

        if (aksi.contains("!perulangan")) {
            checkPerulangan.setVisibility(View.GONE);
        }

        formatAsli = format;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String PIN = pin.getText().toString().trim();

                if (checkPerulangan.isChecked()) {
                    if (format.contains(".[PERULANGAN].")) {
                        format = format.replace("[PERULANGAN]", String.valueOf(getPerulangan(tujuan)));
                    } else {
                        format = getPerulangan(tujuan) + "." + format;
                    }
                } else {
                    format = formatAsli;
                }

                if (PIN.length() > 0) {
                    simpanOutbox();
                    Intent i = new Intent(context, kirim.class);
                    if (format.contains(".[PIN].")) {
                        format.replace("[PIN]", PIN);
                    }
                    i.putExtra("formats", format + "." + PIN);
                    context.startActivity(i);
                    context.finish();
                    dismiss();
                } else {
                    toast("Masukkan pin transaksi");
                }

            }
        });
    }

    private int getPerulangan(String tujuan) {
        dbOutbox dbO = new dbOutbox(context);
        return dbO.perulanganCek(tujuan) + 1;
    }

    private void simpanOutbox() {
        if (aksi.contains("!simpan")) {

        } else {
            outbox(format, judul, tujuan, aksi);
        }
    }

    private void outbox(String format, String judul, String tujuan, String aksi) {
        dbOutbox dbO = new dbOutbox(context);
        dbO.getWritableDatabase();
        String tgl = CommonMethods.getCurrentDate();

        dbO.Tambah(format, tujuan, judul, tgl, "", "", aksi);
    }

    private void toast(String s) {
        Toast toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
}
