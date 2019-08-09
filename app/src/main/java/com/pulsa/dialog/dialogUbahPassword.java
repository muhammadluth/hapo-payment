package com.pulsa.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pulsa.koneksi.kirim;
import com.pulsa.R;

public class dialogUbahPassword extends Dialog {
    private String ket;
    private String judul;
    private String format;
    private Activity context;

    public dialogUbahPassword(Activity context, String format, String laporans, String judul) {
        super(context);
        this.ket = laporans;
        this.judul = judul;
        this.format = format;
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_ubah);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        TextView title = findViewById(R.id.tv);
        TextView keterangan = findViewById(R.id.keterangan);
        title.setText(judul);
        keterangan.setText(ket);
        final Button btn = findViewById(R.id.btn);
        final Button tutup = findViewById(R.id.tutup);
        ImageView close = findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finish();
                dismiss();
            }
        });

        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(context, kirim.class);
                i.putExtra("formats", format);
                i.putExtra("ubahPassword", true);
                context.startActivity(i);
                dismiss();
            }
        });
    }
}
