package com.pulsa.nav_bottom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pulsa.R;
import com.pulsa.nav_bottom.akun.ubahNama;
import com.pulsa.nav_bottom.akun.ubahPassword;
import com.pulsa.nav_bottom.akun.ubahPinTransaksi;
import com.pulsa.utils.SessionManager;

import static com.pulsa.setGet.DataPut.RESTART_CONNECTION_APP;

public class akunFragment extends Fragment {
    SessionManager ss;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_akun, container, false);
        RelativeLayout gantiNama = v.findViewById(R.id.gantiNama);
        RelativeLayout gantiPin = v.findViewById(R.id.gantiPin);
        RelativeLayout panduanAplikasi = v.findViewById(R.id.panduanAplikasi);
        RelativeLayout kunciAplikasi = v.findViewById(R.id.kunciAplikasi);
        RelativeLayout pusatBantuan = v.findViewById(R.id.pusatBantuan);
        RelativeLayout rating = v.findViewById(R.id.rating);
        RelativeLayout logout = v.findViewById(R.id.logout);
        ss = new SessionManager(getActivity());
        gantiNama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ubahNama.class);
                startActivity(i);
            }
        });

        gantiPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ubahPinTransaksi.class);
                startActivity(i);
            }
        });

        kunciAplikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ubahPassword.class);
                startActivity(i);
            }
        });

        pusatBantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(getActivity(), akunWebview.class);
                i.putExtra("title", "Pusat Bantuan");
                i.putExtra("url", "https://ajisaicko.cafe24.com/api/jsons/data.php?menu=pusatBantuan");
                startActivity(i);*/
            }
        });

        panduanAplikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(getActivity(), panduanApp.class);
                startActivity(i);*/
            }
        });

        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogout();
            }
        });

        return v;
    }

    private String getCapsSentences(String tagName) {
        String[] splits = tagName.toLowerCase().split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < splits.length; i++) {
            String eachWord = splits[i];
            if (i > 0 && eachWord.length() > 0) {
                sb.append(" ");
            }
            String cap = eachWord.substring(0, 1).toUpperCase()
                    + eachWord.substring(1);
            sb.append(cap);
        }
        return sb.toString();
    }

    private void rating() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Rating");
        alertDialog.setMessage("Anda senang menggunakan aplikasi DiPay ? Ayo dukung kami dengan cara memberi bintang 5 dan tulis ulasan di playstore, dukungan yang anda berikan akan sangat membantu kami dalam penyempurnaan produk maupun layanan yang lebih baik");
        alertDialog.setPositiveButton("Beri Nilai", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                /*Intent intent = new Intent(Intent.ACTION_VIEW);
                try {
                    intent.setData(Uri.parse("market://details?id=com.dipay.net"));
                    startActivity(intent);
                } catch (Exception e) {
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.pulsakita.net"));
                    startActivity(intent);
                }*/
                dialog.dismiss();
            }
        });
        alertDialog.setNegativeButton("Lain Kali", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    private void dialogLogout() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Peringatan");
        alertDialog.setMessage("Apa anda yakin ingin logout ?");
        alertDialog.setPositiveButton("LOGOUT", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                try {
                    SessionManager ss = new SessionManager(getActivity());
                    ss.logoutUser();
                    restartService();
                } catch (Exception e) {
                    toast("terjadi kesalahan");
                }

                dialog.dismiss();
            }
        });
        alertDialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    public void toast(String isi) {
        Toast.makeText(getActivity(), isi, Toast.LENGTH_SHORT).show();
    }

    private void restartService() {
        Log.e("service", "restart");
        Intent intent2 = new Intent(RESTART_CONNECTION_APP);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent2);
    }
}
