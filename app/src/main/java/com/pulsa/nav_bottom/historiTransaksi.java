package com.pulsa.nav_bottom;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.pulsa.R;
import com.pulsa.koneksi.kirim;

import java.util.Calendar;

public class historiTransaksi extends Fragment {
    private TextView tampil;
    private int year;
    private int month;
    String bulan;
    private int day;
    Button button1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cek_transaksi, container, false);
        tampil = v.findViewById(R.id.TAMPIL);
        button1= v.findViewById(R.id.kirim);
        final Calendar c = Calendar.getInstance();
        day   = c.get(Calendar.DAY_OF_MONTH);
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        if(month==0){
            bulan= "Januari";
        }else if(month==1){
            bulan= "Februari";
        }else if(month==2){
            bulan= "Maret";
        }else if(month==3){
            bulan= "April";
        }else if(month==4){
            bulan= "Mei";
        }else if(month==5){
            bulan= "Juni";
        }else if(month==6){
            bulan= "Juli";
        }else if(month==7){
            bulan= "Agustus";
        }else if(month==8){
            bulan= "September";
        }else if(month==9){
            bulan= "Oktober";
        }else if(month==10) {
            bulan= "November";
        }else if(month==11){
            bulan= "Desember";
        }else {
            bulan= String.valueOf(month);
        }
        tampil.setText(new StringBuilder()
                .append(day).append(" ")
                .append(bulan).append(" ")
                .append(year).append(" "));

        tampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int selectedYear,
                                                  int selectedMonth, int selectedDay) {

                                day   = selectedDay;
                                year  = selectedYear;
                                month = selectedMonth;

                                if(month==0){
                                    bulan= "Januari";
                                }else if(month==1){
                                    bulan= "Februari";
                                }else if(month==2){
                                    bulan= "Maret";
                                }else if(month==3){
                                    bulan= "April";
                                }else if(month==4){
                                    bulan= "Mei";
                                }else if(month==5){
                                    bulan= "Juni";
                                }else if(month==6){
                                    bulan= "Juli";
                                }else if(month==7){
                                    bulan= "Agustus";
                                }else if(month==8){
                                    bulan= "September";
                                }else if(month==9){
                                    bulan= "Oktober";
                                }else if(month==10) {
                                    bulan= "November";
                                }else if(month==11){
                                    bulan= "Desember";
                                }else {
                                    bulan= String.valueOf(month);
                                }

                                tampil.setText(new StringBuilder()
                                        .append(day).append(" ")
                                        .append(bulan)
                                        .append(" ").append(year)
                                        .append(" "));

                            }
                        }, year, month, day);
                datePickerDialog.show();

            }
        });
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String kode = null;
                int bulan = month+1;
                String bln = String.valueOf(bulan);
                if(month+1 >= 10) {
                    kode = "CEKTRX." + String.valueOf(day) + bln + String.valueOf(year);
                }else{
                    kode = "CEKTRX." + String.valueOf(day) + "0" + bln + String.valueOf(year);
                }
                String format =(kode);
                try{
                    if (kode.length()>0 ) {
                        send(format.trim());
                    }else {
                        proses();
                    }
                }catch (Exception E){
                    Toast.makeText(getActivity(),
                            "Gagal Terjadi Kesalahan",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    public void proses() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage(getString(R.string.konfirmasi));
        alertDialogBuilder.setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void send(String format) {
        Intent i = new Intent(getActivity(), kirim.class);
        i.putExtra("formats", format.trim().toUpperCase());
        startActivity(i);
    }
}
