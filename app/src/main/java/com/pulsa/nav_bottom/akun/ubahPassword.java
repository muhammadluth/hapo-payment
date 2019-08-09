package com.pulsa.nav_bottom.akun;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pulsa.R;
import com.pulsa.utils.SessionManager;

import java.util.HashMap;

public class ubahPassword extends AppCompatActivity {
    SessionManager ss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.ubah_password);
        setTitle("Ubah Kunci Aplikasi");
        ss = new SessionManager(this);
        HashMap<String, String> data = ss.getUserDetails();
        final String passlama = data.get(SessionManager.key_fragment);

        final EditText passBaru = findViewById(R.id.passBaru);
        final EditText passBaru2 = findViewById(R.id.passBaru2);
        final EditText passLama = findViewById(R.id.passLama);
        Button ubah_pass = findViewById(R.id.proses);
        CardView cardPassLama = findViewById(R.id.cardPassLama);

        if (data.get(SessionManager.key_fragment) == null) {
            cardPassLama.setVisibility(View.GONE);
            passLama.setText("121");
        } else {
            passLama.setText(data.get(SessionManager.key_fragment));
        }

        ubah_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String PASSBARU = passBaru.getText().toString();
                String PASSBARU2 = passBaru2.getText().toString();
                String PASSLAMA = passLama.getText().toString();
                if (PASSLAMA.equalsIgnoreCase("121")) {
                    //SIMPAN PASSWORD
                } else {
                    if (PASSBARU.length() > 3 && PASSBARU2.length() > 3) {
                        if (PASSBARU.equals(PASSBARU2)) {
                            if (PASSLAMA.equalsIgnoreCase(passlama)) {
                                //SIMPAN PASSWORD
                            } else {
                                dialog("Password lama salah");
                            }
                        } else {
                            dialog("Password baru tidak sama");
                        }
                    } else {
                        dialog("Isi password dengan benar, minimal 4 karakter");
                    }
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
