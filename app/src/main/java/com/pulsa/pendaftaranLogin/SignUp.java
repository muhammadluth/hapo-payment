package com.pulsa.pendaftaranLogin;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.util.Linkify;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pulsa.R;
import com.pulsa.setGet.DataPut;
import com.pulsa.utils.RegisterUserClass;

import java.util.HashMap;

/**
 * Created by IT-Staff on 18/08/2016.
 */
public class SignUp extends Activity {
    Button btnDaftar;
    ProgressBar pg;
    TextView tunggu;
    LinearLayout form;
    Button login_member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pendaftaran_activity);
        changeStatusBarColor();

        btnDaftar = findViewById(R.id.daftar);
        final EditText edtNama = findViewById(R.id.nama);
        final EditText edtAlamat = findViewById(R.id.alamat);
        final EditText edtTlp = findViewById(R.id.tlp);

        login_member = findViewById(R.id.login_member);
        login_member.setPaintFlags(login_member.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        Linkify.addLinks(login_member, Linkify.ALL);

        pg = findViewById(R.id.loading);
        tunggu = findViewById(R.id.tunggu);
        form = findViewById(R.id.form_daftar);
        login_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        TextView term = findViewById(R.id.term);
        TextView petunjuk = findViewById(R.id.petunjuk);

        term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(SignUp.this, akunWebview.class);
                i.putExtra("url", "https://ajisaicko.cafe24.com/term.html");
                i.putExtra("title","Terms of Use");
                startActivity(i);*/
            }
        });

        petunjuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(SignUp.this, akunWebview.class);
                i.putExtra("url", "https://ajisaicko.cafe24.com/api_v4/");
                i.putExtra("title","Petunjuk");
                i.putExtra("aksi", "petunjuk");
                startActivity(i);*/
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = edtNama.getText().toString();
                String alamat = edtAlamat.getText().toString();
                String tlp = edtTlp.getText().toString();
                if (nama.length() > 0 && alamat.length() > 0 && tlp.length() > 0) {
                    try {
                        String no = tlp.substring(0, 2);
                        if (no.equals("08")) {
                            dialog("Pendaftaran Sukses.\nUsername dan Password akan dikirim ke No Hp Anda");
                            //StartSync(nama, alamat, tlp);
                        } else {
                            dialog("Mohon periksa nomor hp yang anda gunakan");
                        }
                    } catch (Exception e) {
                        dialog("Mohon Masukkan data dengan benar");
                    }
                } else {
                    dialog("Mohon Data Dilengkapi");
                }
            }
        });
    }

    private void login() {
        Intent i = new Intent(getApplicationContext(), Login.class);
        startActivity(i);
        finish();
    }

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    private void StartSync(String nama, String alamat, String tlp) {
        class DATA2 extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                form.setVisibility(View.GONE);
                login_member.setVisibility(View.GONE);
                pg.setVisibility(View.VISIBLE);
                tunggu.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pg.setVisibility(View.GONE);
                tunggu.setVisibility(View.GONE);
                form.setVisibility(View.VISIBLE);
                login_member.setVisibility(View.VISIBLE);
                try {
                    if (s.equalsIgnoreCase("1")) {
                        dialog("Pendaftaran Sukses.\nUsername dan Password akan dikirim ke No Hp Anda");
                    } else {
                        if (s.isEmpty()) {
                            String isi = "Tidak dapat terhubung ke server";
                            dialog(isi);
                        } else {
                            dialog(s);
                        }
                    }
                } catch (Exception e) {
                    Log.e("data", e.getMessage());
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put(DataPut.a1, params[0]);
                data.put(DataPut.a2, params[1]);
                data.put(DataPut.a3, params[2]);
                data.put(DataPut.id, DataPut.daftar);
                RegisterUserClass ruc = new RegisterUserClass();
                String result = ruc.sendPostRequest(data);
                return result;
            }
        }
        DATA2 ulc = new DATA2();
        ulc.execute(nama, alamat, tlp);
    }

    public void dialog(String pesan) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(pesan);
        if (pesan.toLowerCase().contains("pendaftaran sukses")) {
            alertDialogBuilder.setPositiveButton("Masuk", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    login();
                }
            });
        } else {
            alertDialogBuilder.setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {

                }
            });
        }
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
