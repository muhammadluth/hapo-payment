package com.pulsa.transaksi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.pulsa.R;
import com.pulsa.dialog.dialogProsesTransaksi;

public class tagihanListrik extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tagihan_listrik, container, false);

        final TextView idPelanggan = v.findViewById(R.id.idPelanggan);
        Button proses = v.findViewById(R.id.proses);

        proses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idPel = idPelanggan.getText().toString();
                String kodeProduk = "BYRPLN";
                if (idPel.length() > 0) {
                    String format = kodeProduk + "." + idPel;
                    dialogProsesTransaksi dialog = new dialogProsesTransaksi(getActivity(), format, "Pembayaran Listrik PLN", idPel, "Pembayaran tagihan listrik PLN", "simpan & proses & !perulangan");
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.show();
                }
            }
        });
        return v;
    }
}
