package com.pulsa.splash;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.pulsa.MainActivity;
import com.pulsa.koneksi.service_xmpp;
import com.pulsa.pendaftaranLogin.Login;
import com.pulsa.setGet.GetSession;
import com.pulsa.BuildConfig;
import com.pulsa.R;

public class splashScreen extends Activity {
    public static boolean splasScreen = true;
    GetSession gs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splasScreen = true;
        gs = new GetSession(this);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        changeStatusBarColor();
        setContentView(R.layout.splash);
        TextView versi = findViewById(R.id.versi);
        versi.setText(BuildConfig.VERSION_NAME);
        Thread timerThread = new Thread() {
            public void run() {
                String email = gs.getUser();
                if (email != null) {
                    cekService();
                }
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    splasScreen = false;
                    /*untuk spalsh screen mauk ke halaman login*/
                    Intent i = new Intent(getApplicationContext(), Login.class);
                    /*untuk spalsh screen mauk ke halaman home*/
//                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        timerThread.start();
    }

    private void cekService() {
        /*String getAktifitasServices = gs.getAktifitasServices();
        final serviceTools serviceTools = new serviceTools();
        long waktuSekarang = Long.parseLong(CommonMethods.getCurrentDateTimes());
        long waktuServices = Long.parseLong(getAktifitasServices);

        if (waktuSekarang > waktuServices) {
            if (serviceTools.isServiceRunning(service_xmpp.class.getName(), splashScreen.this)) {
                try {
                    Log.e("service", "udah running");
                    if (service_xmpp.xmpp.cekKoneksi()) {
                        //Log.e("service", "is connect");
                    } else {
                        //Log.e("service", "not connect");
                        restartService();
                    }

                } catch (Exception e) {
                    //Log.e("error", e.getMessage());
                    restartService();
                }
            } else {
                //Log.e("service", "menjalankan service");
                restartService();
            }
        } else {
            //session.CreateAktifitasServices();
            restartService();
        }*/
    }

    private void restartService() {
        Log.e("service", "restartService");
        try {
            gs.CreateAktifitasServices();
            startService(new Intent(getBaseContext(), service_xmpp.class));
        } catch (Exception e) {
            Log.e("message_receiver", "gagal service_xmpp");
        }
    }

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
