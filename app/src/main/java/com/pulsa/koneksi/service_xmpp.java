package com.pulsa.koneksi;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class service_xmpp extends Service {
    private static final String DOMAIN = "192.168.5.6";
    private static final String HOST = "192.168.5.6";
    public static String email = "tes";
    public static String password = "tes";
    //public static xmpp xmpp;
    private static final String TAG = "Service XMPP";
    boolean jalanLagi = false;
    boolean create = false;

    @Override
    public IBinder onBind(final Intent intent) {
        return new LocalBinder<service_xmpp>(this);
    }

    @Override
    public void onCreate() {
        jalan();
        create = false;
        jalanLagi = true;
        super.onCreate();
    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        super.onStartCommand(intent, startId, startId);
        if (!create) {
            create = true;
            jalanLagi = true;
            onCreate();
        }
        return Service.START_STICKY;
    }

    private void jalan() {
        /*try {
            xmpp = com.bebaspay.koneksi.xmpp.getInstance(service_xmpp.this, DOMAIN, HOST);
            xmpp.connect("onCreate 1a");

            Log.e("Service", "connect() 1a");
        } catch (Exception e) {
            Log.e("Gagal service xmpp", e.getMessage() + "*");
        }*/
    }

    @Override
    public boolean onUnbind(final Intent intent) {
        Log.e(TAG, "Service onBind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*try {
            xmpp.disconnect();
        } catch (Exception e) {
            Log.e("Service destroy", e.getMessage());
        }*/
    }
}
