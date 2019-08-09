package com.pulsa;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.pulsa.koneksi.service_xmpp;
import com.pulsa.nav_bottom.accountFragment;
import com.pulsa.nav_bottom.historiTransaksi;
import com.pulsa.nav_bottom.homeAtribut.ViewPagerAdapter;
import com.pulsa.nav_bottom.homeFragment;
import com.pulsa.nav_bottom.inboxFragment;
import com.pulsa.utils.CreateFileJson;
import com.pulsa.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static com.pulsa.setGet.DataPut.RESTART_CONNECTION_APP;
import static com.pulsa.setGet.DataPut.RESTART_SALDO_APP;

public class MainActivity extends AppCompatActivity {
    SessionManager session;
    TextView saldo;
    AHBottomNavigation navigation;
    ImageButton ButtonAccount;
    boolean home = true;
    RelativeLayout head_produk, iklanApp;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    int currentPage = 0;
    final Handler handler = new Handler();
    public Timer swipeTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = findViewById(R.id.navigation);
        setTitle(R.string.app_name);
        saldo = findViewById(R.id.saldo);
        head_produk = findViewById(R.id.head_produk);
//        iklanApp = findViewById(R.id.iklanApp);


        LocalBroadcastManager.getInstance(this).registerReceiver(iYourBroadcastReceiver, new IntentFilter(RESTART_CONNECTION_APP));
        LocalBroadcastManager.getInstance(this).registerReceiver(iYourBroadcastReceiver, new IntentFilter(RESTART_SALDO_APP));

        session = new SessionManager(getApplicationContext());
        //session.checkLogin();

        ButtonAccount = (ImageButton) findViewById(R.id.ButtonAccount);
        ButtonAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View item) {
                Intent int1 = new Intent(MainActivity.this, accountFragment.class);
                startActivity(int1);
            }
        });
//
//        ButtonAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentLoadakunFragment = onNewIntent
//                startActivity(intentLoadakunFragment);
//            }
//        });


        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.nav_home, R.drawable.go_home, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.nav_inbox, R.drawable.go_inbox, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.nav_histori, R.drawable.go_histori, R.color.colorPrimary);
//        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.nav_akun, R.drawable.go_account, R.color.colorPrimary);

        navigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        navigation.setAccentColor(getResources().getColor(R.color.colorPrimary));
        navigation.setInactiveColor(getResources().getColor(R.color.text_abu_abu));

        // Add items
        navigation.addItem(item1);
        navigation.addItem(item2);
        navigation.addItem(item3);
//        navigation.addItem(item4);

        navigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (position == 0) {
                    start(R.id.home);
                } else if (position == 1) {
                    start(R.id.inbox);
                } else if (position == 2) {
                    start(R.id.cek_transaksi);
                } else if (position == 3) {
                    start(R.id.profile);
                }
                return true;
            }
        });

        navigation.setCurrentItem(0);
        final ViewPager imgSlider = findViewById(R.id.viewPager);
        dotsLayout = findViewById(R.id.layoutDots);

//        addBanners(imgSlider);
//        addBottomDots(0);
    }


//    private void addBanners(final ViewPager imgSlider) {
//        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
//        imgSlider.setAdapter(viewPagerAdapter);
//        imgSlider.addOnPageChangeListener(viewPagerPageChangeListener);
//        setTimer(imgSlider, 5);
//
//        imgSlider.setOnTouchListener(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        stopTimer();
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        setTimer(imgSlider, 1);
//                        break;
//                }
//                return false;
//            }
//        });
//    }

//    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
//        @Override
//        public void onPageSelected(int position) {
//            //Log.e("Selected", position + "*");
//            addBottomDots(position);
//            if (position > currentPage) {
//                currentPage += 1;
//            } else {
//                currentPage -= 1;
//            }
//        }
//
//        @Override
//        public void onPageScrolled(int i, float v, int i1) {
//            //Log.e("Scrolled", "*");
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int i) {
//            //Log.e("ScrollStateChanged", "*");
//        }
//    };

    public void stopTimer() {
        // handler.removeCallbacks(null);
        swipeTimer.cancel();
    }

    /**
     * this function swipes pages left to right for every 7 seconds
     *
     * @param myPager its viewpager.
     * @param time    as second, for 7 second enter 7
     */
    public void setTimer(final ViewPager myPager, int time) {
        final int size = 3;

        final Runnable Update = new Runnable() {
            int NUM_PAGES = size;

            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                myPager.setCurrentItem(currentPage++, true);
            }
        };

        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1000, time * 1000);
    }

//    private void addBottomDots(int currentPage) {
//        dots = new TextView[3];
//        dotsLayout.removeAllViews();
//        for (int i = 0; i < dots.length; i++) {
//            dots[i] = new TextView(this);
//            dots[i].setText(Html.fromHtml("&#8226;"));
//            dots[i].setTextSize(17);
//            dots[i].setTextColor(getResources().getColor(R.color.background2));
//            dotsLayout.addView(dots[i]);
//        }
//
//        if (dots.length > 0)
//            dots[currentPage].setTextColor(getResources().getColor(R.color.black));
//    }


    private String ambilInfoFile() {
        try {
            JSONObject jsonObject = new JSONObject(CreateFileJson.getData(this, "infoTerbaru"));
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            return String.valueOf(jsonArray.length());
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }

        return "";

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.komplain) {
            /*Intent i = new Intent(this, mainChat.class);
            startActivity(i);*/
            String contact = "+62895404750070"; // use country code with your phone number
            String url = "https://api.whatsapp.com/send?phone=" + contact;
            try {
                PackageManager pm = getPackageManager();
                pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            } catch (PackageManager.NameNotFoundException e) {
                Toast.makeText(this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();

            }
            return true;
        } else if (id == R.id.ButtonAccount) {
            accountFragment fragment = new accountFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame, fragment);
            transaction.commit();
        }


        return super.onOptionsItemSelected(item);
    }

    public void clearApplicationData() {
        try {
            File deletePrefFile = new File("/data/data/com.pulsa/shared_prefs");
            deletePrefFile.delete();
        } catch (Exception e) {

        }

    }

    public void clearSharedPreferences(Context ctx) {
        File dir = new File(ctx.getFilesDir().getParent() + "/shared_prefs/");
        String[] children = dir.list();
        for (int i = 0; i < children.length; i++) {
            // clear each of the prefrances
            ctx.getSharedPreferences(children[i].replace(".xml", ""), Context.MODE_PRIVATE).edit().clear().commit();
        }
        // Make sure it has enough time to save all the commited changes
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        for (int i = 0; i < children.length; i++) {
            // delete the files
            new File(dir, children[i]).delete();
        }
        session.logoutUser();
    }


    private void start(int id) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();
//        iklanApp.setVisibility(View.VISIBLE);
        head_produk.setVisibility(View.VISIBLE);
        if (id == R.id.home) {
            fragment = new homeFragment();
        } else if (id == R.id.inbox) {
            fragment = new inboxFragment();
        } else if (id == R.id.profile) {
            fragment = new accountFragment();
        } else if (id == R.id.cek_transaksi) {
            fragment = new historiTransaksi();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.commit();
            fragment.setArguments(bundle);
        }

        cekService();

    }

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    /**
     * Handle Back Navigation Button
     *
     * @param keyCode
     * @param event
     * @return
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //back to previous url
            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                finishApp();
            } else {
                Toast.makeText(getBaseContext(), "Tekan Sekali Lagi Untuk Keluar", Toast.LENGTH_SHORT).show();
            }

            mBackPressed = System.currentTimeMillis();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Method to close App
     */
    private void finishApp() {
        finish();
    }

    private final BroadcastReceiver iYourBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // now you can call all your fragments method here
            //Log.e("MainActivity", "ada pesan broadcast");
            try {
                if (intent.getAction() != null) {
                    if (intent.getAction().equalsIgnoreCase(RESTART_CONNECTION_APP)) {
                        try {
                            startService(new Intent(getBaseContext(), service_xmpp.class));
                        } catch (Exception e) {
                            Log.e("message_receiver", "gagal service_xmpp");
                        }
                    }

                    if (intent.getAction().equalsIgnoreCase(RESTART_SALDO_APP)) {
                        updateNama();
                    }
                }
            } catch (Exception e) {

            }
        }
    };

    private void updateNama() {
        HashMap<String, String> data = session.getUserDetails();
        if (data.get(SessionManager.key_activitys) != null) {
            String user = data.get(SessionManager.KEY_SALDO).toUpperCase();
            saldo.setText(user.toUpperCase());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        cekService();
    }

    private void cekService() {
        //Log.e("service", "cek");
        /*GetSession gs = new GetSession(this);
        final serviceTools serviceTools = new serviceTools();
        long waktuSekarang = Long.parseLong(CommonMethods.getCurrentDateTimes());
        long waktuServices = Long.parseLong(gs.getAktifitasServices());

        if (waktuSekarang > waktuServices) {
            if (serviceTools.isServiceRunning(service_xmpp.class.getName(), MainActivity.this)) {
                try {
                    //Log.e("service", "udah running");
                    if (service_xmpp.xmpp.cekKoneksi()) {
                        //Log.e("service", "is connect");
                    } else {
                        //Log.e("service", "not connect");
                        gs.CreateAktifitasServices();
                        restartService();
                    }

                } catch (Exception e) {
                    //Log.e("error", e.getMessage());
                    gs.CreateAktifitasServices();
                    restartService();
                }
            } else {
                //Log.e("service", "menjalankan service");
                gs.CreateAktifitasServices();
                restartService();
            }
        } else {
            try {
                if (service_xmpp.xmpp.cekKoneksi()) {
                    //restartService();
                } else {
                    //Log.e("service", "not connect");
                    gs.CreateAktifitasServices();
                    restartService();
                }
            } catch (Exception e) {
                restartService();
            }
        }*/
    }

    private void restartService() {
        Log.e("service", "restart");
        Intent intent2 = new Intent(RESTART_CONNECTION_APP);
        LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(iYourBroadcastReceiver);
    }

}
