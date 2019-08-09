package com.pulsa.setGet;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pulsa.db.dbChat;
import com.pulsa.db.dbFavorit;
import com.pulsa.db.dbUser;
import com.pulsa.db.dbinbox;
import com.pulsa.koneksi.service_xmpp;
import com.pulsa.utils.CommonMethods;
import com.pulsa.utils.Encryption;
import com.pulsa.utils.SessionManager;
import com.pulsa.BuildConfig;

import java.util.HashMap;
import java.util.Random;


/**
 * Created by IT-Staff on 31/10/2016.
 */

public class GetSession {
    private Context context;
    private String email;
    private String alamat;
    private boolean versiApp;

    public GetSession(Context context) {
        this.context = context;
    }

    public String getUser() {
        String a = "";
        try {
            SessionManager session = new SessionManager(context);
            HashMap<String, String> user = session.getUserDetails();
            a = user.get(SessionManager.key_activitys).toUpperCase();
        } catch (Exception e) {

        }
        return a;
    }

    public String getFragment() {
        String a = null;
        try {
            SessionManager session = new SessionManager(context);
            HashMap<String, String> user = session.getUserDetails();
            a = user.get(SessionManager.key_fa);
        } catch (Exception e) {

        }
        return a;
    }

    public String getTkn() {
        String a = null;
        try {
            SessionManager session = new SessionManager(context);
            HashMap<String, String> user = session.getUserDetails();
            a = user.get(SessionManager.key_fragment);
        } catch (Exception e) {

        }
        return a;
    }

    public String getUserTkn() {
        String a = null;
        try {
            SessionManager session = new SessionManager(context);
            HashMap<String, String> user = session.getUserDetails();
            a = user.get(SessionManager.key_activitys);
        } catch (Exception e) {

        }
        return a;
    }

    public String getKY() {
        String a = "-";
        try {
            SessionManager session = new SessionManager(context);
            HashMap<String, String> user = session.getUserDetails();

            String x1 = user.get(SessionManager.key_activitys).toLowerCase();
            String x = Encryption.md5(x1);
            String a1 = x.substring(0, 5);
            String b1 = x.substring(6);
            a = b1 + a1;
        } catch (Exception e) {
            //Log.e("error", e.getMessage());
        }
        return Encryption.md5(a);
    }

    public String getKYBismillah() {
        String a = "-";
        try {
            SessionManager session = new SessionManager(context);
            HashMap<String, String> user = session.getUserDetails();
            String x1 = user.get(SessionManager.key_activitys);
            String x = Encryption.md5(x1.toLowerCase());
            String a1 = x.substring(0, 5);
            String a2 = x.substring(6);
            String a3 = x.substring(10);
            a = a2 + "bismillah" + a3 + a1;

        } catch (Exception e) {
            //Log.e("error", e.getMessage());
        }
        return Encryption.md5(a);
    }

    public String getKYApi(String kunci) {
        String a = "errorApiMas";
        try {
            String x = Encryption.md5(kunci.toLowerCase());
            String a1 = x.substring(0, 5);
            String a2 = x.substring(6);
            String a3 = x.substring(10);
            a = a2 + "bismillah" + a1 + "ingatAllah" + a3 + "ingatdosa";
        } catch (Exception e) {
            //Log.e("error", e.getMessage());
        }

        return Encryption.md5(a);
    }

    public String random(int length) {
        String alphabet = "0123456789GHIJKLMNOPQZ";
        int N = alphabet.length();
        Random r = new Random();
        StringBuilder x = new StringBuilder();
        for (int i = 0; i < length; i++) {
            x.append(alphabet.charAt(r.nextInt(N)));
        }
        return x.toString();
    }

    public String randomLower(int length) {
        String alphabet = "0123456789tyuiokgjdhn";
        int N = alphabet.length();
        Random r = new Random();
        StringBuilder x = new StringBuilder();
        for (int i = 0; i < length; i++) {
            x.append(alphabet.charAt(r.nextInt(N)));
        }
        return x.toString();
    }

    public String randomInteger(int length) {
        String alphabet = "0123456789";
        int N = alphabet.length();
        Random r = new Random();
        StringBuilder x = new StringBuilder();
        for (int i = 0; i < length; i++) {
            x.append(alphabet.charAt(r.nextInt(N)));
        }
        return x.toString();
    }

    public String getKY2(String x1) {
        String x = Encryption.md5(x1.toLowerCase());
        String a1 = x.substring(0, 5);
        String b1 = x.substring(6);
        return Encryption.md5(b1 + a1);
    }

    public String getKY3(String x1, String x2) {
        return Encryption.md5(x1 + x2);
    }

    public String getIdApp() {
        String a = BuildConfig.DOMAIN;
        try {
            SessionManager session = new SessionManager(context);
            HashMap<String, String> user = session.getUserDetails();
            a = user.get(SessionManager.KEY_VERIVICATIONID);
        } catch (Exception e) {

        }
        if (a == null) {
            a = BuildConfig.DOMAIN;
        }
        return a;
    }

    public void createIdApp(String idApp) {
        try {
            SessionManager session = new SessionManager(context);
            session.createVerifikasiIdApp(idApp);
        } catch (Exception e) {

        }
    }

    public String getFB() {
        SessionManager session = new SessionManager(context);
        HashMap<String, String> user = session.getUserDetails();
        String fb = user.get(SessionManager.key_fb);
        return fb;
    }

    public String getHP() {
        SessionManager session = new SessionManager(context);
        HashMap<String, String> user = session.getUserDetails();
        String fb = user.get(SessionManager.KEY_HP);
        return fb;
    }

    public String getLevel() {
        String level = "0";
        try {
            SessionManager session = new SessionManager(context);
            HashMap<String, String> user = session.getUserDetails();
            level = user.get(SessionManager.KEY_LEVEL);
        } catch (Exception e) {

        }
        return level;
    }

    public String getKodeLevel() {
        String level = "0";
        try {
            SessionManager session = new SessionManager(context);
            HashMap<String, String> user = session.getUserDetails();
            level = user.get(SessionManager.KEY_KODE_LEVEL);
        } catch (Exception e) {

        }
        return level;
    }

    public String getServerSMS() {
        String server = null;
        try {
            SessionManager session = new SessionManager(context);
            HashMap<String, String> user = session.getUserDetails();
            server = user.get(SessionManager.KEY_SERVER);
        } catch (Exception e) {

        }
        return server;
    }

    public String getServer() {
        String server = null;
        try {
            SessionManager session = new SessionManager(context);
            HashMap<String, String> user = session.getUserDetails();
            server = user.get(SessionManager.SERVER_URL);
        } catch (Exception e) {

        }
        return server;
    }

    public void setStatusRepliaksi(String status) {
        try {
            SessionManager session = new SessionManager(context);
            session.createRepliaksi(status);
        } catch (Exception e) {

        }
    }

    public String getRepliaksi() {
        String replikasi = "1";
        try {
            SessionManager session = new SessionManager(context);
            HashMap<String, String> user = session.getUserDetails();
            replikasi = user.get(SessionManager.KEY_REPLIKASI);
        } catch (Exception e) {

        }
        return replikasi;
    }

    public String getPrinter() {
        String print = null;
        try {
            SessionManager session = new SessionManager(context);
            HashMap<String, String> user = session.getUserDetails();
            print = user.get(SessionManager.KEY_PRINTER);
        } catch (Exception e) {
            print = null;
        }
        return print;
    }

    public void setServer() {
        try {
            SessionManager session = new SessionManager(context);
            session.createServerUrl("1");
        } catch (Exception e) {

        }
    }

    public String getMode() {
        String mode = "online";
        try {
            SessionManager session = new SessionManager(context);
            HashMap<String, String> user = session.getUserDetails();
            mode = user.get(SessionManager.SESSION_MODE);
        } catch (Exception e) {
            mode = "online";
        }
        return mode;
    }

    public void setServer1(String server1) {
        try {
            SessionManager session = new SessionManager(context);
            session.createServerUrl(server1);
        } catch (Exception e) {

        }
    }

    public void setMode(String mode) {
        try {
            SessionManager session = new SessionManager(context);
            session.createSessionMode(mode);
        } catch (Exception e) {

        }
    }

    public void setNotifikasiError(Boolean status) {
        try {
            SessionManager session = new SessionManager(context);
            session.createNotifikasiError(status);
        } catch (Exception e) {
        }
    }

    public boolean getNotifikasiError() {
        SessionManager session = new SessionManager(context);
        return session.IsNotifikasiError();
    }


    public void CreateAktifitasTerahir() {
        try {
            SessionManager session = new SessionManager(this.context);
            session.createAktifitasTerahir(CommonMethods.getCurrentDateTimesINT());
        } catch (Exception e) {
            //Log.e("ERROR", e.getMessage() + "error");
        }
    }

    public void CreateAktifitasServices() {
        try {
            SessionManager session = new SessionManager(this.context);
            session.createAktifitasRestart(CommonMethods.getCurrentDateTimePlusMinutServices(5));
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage() + "error");
        }
    }

    public String getAktifitasServices() {
        String a = "0";
        try {
            SessionManager session = new SessionManager(context);
            HashMap<String, String> user = session.getUserDetails();
            a = user.get(SessionManager.KEY_AKTIVITAS_SERVICE);
            if (a == null) {
                a = "0";
                CreateAktifitasServices();
            }
        } catch (Exception e) {

        }
        return a;
    }

    public String getAktifitasTerahir() {
        String a = "";
        try {
            SessionManager session = new SessionManager(context);
            HashMap<String, String> user = session.getUserDetails();
            a = user.get(SessionManager.KEY_AKTIVITAS_TERAHIR);
            if (a == null) {
                a = "2017-05-02 10:48:00";
            }
        } catch (Exception e) {

        }
        return a;
    }

    public boolean transaksipinMode() {
        SessionManager session = new SessionManager(context);
        boolean status = session.isENIPAplikasi();
        return status;
    }

    public void setENIPStatus(boolean ENIP) {
        try {
            SessionManager session = new SessionManager(this.context);
            session.setENIPStatus(ENIP);
        } catch (Exception e) {
            //Log.e("ERROR", e.getMessage());
        }
    }

    public void setENIP(String ENIP) {
        if (ENIP != null) {
            if (ENIP.length() > 0) {
                Encryption encryption = Encryption.getDefault(getUser() + 1, "Salt", new byte[16]);
                String encrypted = encryption.encryptOrNull(ENIP);
                SessionManager session = new SessionManager(this.context);
                session.createENIP(encrypted);
            } else {
                SessionManager session = new SessionManager(this.context);
                session.createENIP("");
            }
        } else {
            SessionManager session = new SessionManager(this.context);
            session.createENIP(null);
        }
    }

    public String getENIP() {
        SessionManager session = new SessionManager(context);
        HashMap<String, String> user = session.getUserDetails();
        String a = user.get(SessionManager.KEY_ENIP);

        Encryption encryption = Encryption.getDefault(getUser() + 1, "Salt", new byte[16]);
        String decrypted = null;
        if (a != null) {
            if (encryption != null) {
                decrypted = encryption.decryptOrNull(a);
                if (decrypted.length() <= 0) {
                    return null;
                }
            }
        } else {
            return null;
        }

        return decrypted;
    }

    //sesi baru
    public void setNama(String nama) {
        SessionManager session = new SessionManager(this.context);
        session.createSesiNama(nama);
    }

    //sesi baru
    public String getNama() {
        SessionManager session = new SessionManager(context);
        HashMap<String, String> user = session.getUserDetails();
        String a = user.get(SessionManager.KEY_NAMA);
        if (a == null) {
            return "-";
        }
        return a;
    }

    public String getSaldo() {
        SessionManager session = new SessionManager(context);
        HashMap<String, String> user = session.getUserDetails();
        String a = user.get(SessionManager.KEY_SALDO);
        if (a == null) {
            return "-";
        }
        return a;
    }

    public String getKomisi() {
        SessionManager session = new SessionManager(context);
        HashMap<String, String> user = session.getUserDetails();
        String a = user.get(SessionManager.KEY_KOMISI);
        if (a == null) {
            return "-";
        }
        return a;
    }

    public void createNama(String nama) {
        try {
            SessionManager session = new SessionManager(this.context);
            session.createSesiNama(nama);
        } catch (Exception e) {
            // Log.e("ERROR", e.getMessage());
        }
    }

    public void createSaldo(String saldo) {
        try {
            SessionManager session = new SessionManager(this.context);
            session.createSesiSaldo(saldo);
        } catch (Exception e) {
            //  Log.e("ERROR", e.getMessage());
        }
    }

    public void createKomisi(String komisi) {
        try {
            SessionManager session = new SessionManager(this.context);
            session.createSesiKomisi(komisi);
        } catch (Exception e) {
            //  Log.e("ERROR", e.getMessage());
        }
    }

    //cs
    public String getUSerCsTransaksi() {
        SessionManager session = new SessionManager(context);
        HashMap<String, String> user = session.getUserDetails();
        String a = user.get(SessionManager.KEY_USERCS_TRANSAKSI);
        if (a == null) {
            return "-";
        }
        return a;
    }

    public String getUSerCsDeposit() {
        SessionManager session = new SessionManager(context);
        HashMap<String, String> user = session.getUserDetails();
        String a = user.get(SessionManager.KEY_USERCS_DEPOSIT);
        if (a == null) {
            return "-";
        }
        return a;
    }

    public void createUserCsTransaksi(String user) {
        //  Log.e("createUserCsTransaksi", user);
        try {
            SessionManager session = new SessionManager(this.context);
            session.createSesiCSTransaksi(user);
        } catch (Exception e) {
            //   Log.e("ERROR", e.getMessage());
        }
    }

    public void createUserCsDeposit(String user) {
        // Log.e("createUserCsDeposit", user);
        try {
            SessionManager session = new SessionManager(this.context);
            session.createSesiCSDeposit(user);
        } catch (Exception e) {
            //  Log.e("ERROR", e.getMessage());
        }
    }

    public String getFee() {
        try {
            SessionManager session = new SessionManager(context);
            HashMap<String, String> user = session.getUserDetails();
            return user.get(SessionManager.KEY_FEE);
        } catch (Exception e) {
            return "0";
        }

    }

    public boolean getMinDeposit() {
        try {
            SessionManager session = new SessionManager(context);
            return session.isMinDeposit();
        } catch (Exception e) {
            //Log.e("minDeposit", e.getMessage() + "");
            return false;
        }
    }

    public void createSMSServer(String center) {
        try {
            SessionManager session = new SessionManager(this.context);
            session.createServerSession(center);
        } catch (Exception e) {
            //  Log.e("ERROR", e.getMessage());
        }
    }

    public void cekLoginApp() {
        SessionManager session = new SessionManager(this.context);
        session.checkLogin();
    }

    public String getReward() {
        String a = "-";
        try {
            SessionManager session = new SessionManager(context);
            HashMap<String, String> user = session.getUserDetails();
            a = user.get(SessionManager.KEY_REWARD);

            if (a == null) {
                return "-";
            }
        } catch (Exception e) {

        }
        return a;
    }

    public void setReward(String center) {
        try {
            SessionManager session = new SessionManager(this.context);
            session.createReward(center);
        } catch (Exception e) {
            //  Log.e("ERROR", e.getMessage());
        }
    }

    public boolean getUserReferal() {
        try {
            SessionManager session = new SessionManager(context);
            return session.isUserReferal();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean getTampilReward() {
        if (getMode() == null || getMode().equalsIgnoreCase("online")) {
            try {
                SessionManager session = new SessionManager(context);
                return session.isTampilReward();
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public String getTvReferral() {
        String a = "-";
        try {
            SessionManager session = new SessionManager(context);
            HashMap<String, String> user = session.getUserDetails();
            a = user.get(SessionManager.KEY_REFERAL);

            if (a == null) {
                return "-";
            }
        } catch (Exception e) {

        }
        return a;
    }

    public boolean perbaharuiApp() {
        try {
            SessionManager session = new SessionManager(context);
            return session.isParameterUpdateApp();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean cekMenuPremiumAlive() {
        try {
            SessionManager session = new SessionManager(context);
            return session.isParameterMenuPremium();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean cekUpgradePremium() {
        try {
            SessionManager session = new SessionManager(context);
            return session.isUpgradePremium();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean cekUpgradeMD() {
        try {
            SessionManager session = new SessionManager(context);
            return session.isUpgradeMD();
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmail() {
        return email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void createCashback(String dataCashback) {
        try {
            SessionManager session = new SessionManager(this.context);
            session.cashBack(dataCashback);
        } catch (Exception e) {
            //  Log.e("ERROR", e.getMessage());
        }
    }

    public String getCashback() {
        SessionManager session = new SessionManager(context);
        HashMap<String, String> user = session.getUserDetails();
        String a = user.get(SessionManager.KEY_CASHBACK);
        if (a == null) {
            return "0";
        }
        return a;
    }

    public void setSessionReveralUpline(String sessionReveralUpline) {
        try {
            SessionManager session = new SessionManager(this.context);
            session.createReferalUpline(sessionReveralUpline);
        } catch (Exception e) {
            //  Log.e("ERROR", e.getMessage());
        }
    }

    public void setAutocompleteTujuan(Boolean status) {
        try {
            SessionManager session = new SessionManager(this.context);
            session.createAutocompleteTujuan(status);
        } catch (Exception e) {
            //  Log.e("ERROR", e.getMessage());
        }
    }

    public String getReferralUpline() {
        SessionManager session = new SessionManager(context);
        HashMap<String, String> user = session.getUserDetails();
        String a = user.get(SessionManager.KEY_REFERAL_UPLINE);
        if (a == null) {
            return "";
        }
        return a;
    }

    public boolean getAutocompleteTujuan() {
        SessionManager session = new SessionManager(context);
        boolean status = session.isAutocomplete();
        return status;
    }

    public void setPenjelasanCashback(String penjelasan) {
        try {
            SessionManager session = new SessionManager(this.context);
            session.setpenjelasanCashBack(penjelasan);
        } catch (Exception e) {
            //  Log.e("ERROR", e.getMessage());
        }
    }

    public String getPenjelasanCashback() {
        SessionManager session = new SessionManager(context);
        HashMap<String, String> user = session.getUserDetails();
        String a = user.get(SessionManager.KEY_PENJELASAN_CASHBACK);
        Log.e("a", "aaa" + a);
        if (a == null) {
            return "";
        }
        return a;
    }


    public void logoutAplikasi() {
        SessionManager session = new SessionManager(context);
        session.createLoginSession(null, null);

        try {
            dbChat dbcat = new dbChat(context);
            dbcat.Hapus();
        } catch (Exception e) {

        }

        try {
            dbFavorit dbfavorit = new dbFavorit(context);
            dbfavorit.Hapus();
        } catch (Exception e) {

        }

        try {
            dbinbox dbhelper = new dbinbox(context);
            dbhelper.HapusALL();
        } catch (Exception e) {

        }

        try {
            dbUser dbU = new dbUser(context);
            SQLiteDatabase mDb = dbU.getWritableDatabase();
            ContentValues cv = new ContentValues();
            String ids = "1";
            cv.put("password", "1234");
            mDb.update("dbuser", cv, ids + " = 1", null);
        } catch (Exception e) {

        }

        session.logoutUser();

        try {
            context.stopService(new Intent(context, service_xmpp.class));
        } catch (Exception e) {

        }
    }

    public void createVersiApp(String versi) {
        try {
            SessionManager session = new SessionManager(this.context);
            session.createVersi(versi);
        } catch (Exception e) {
            Log.e("ERROR createVersiApp", e.getMessage());
        }
    }

    public String getVersiApp() {
        SessionManager session = new SessionManager(context);
        HashMap<String, String> user = session.getUserDetails();
        String a = user.get(SessionManager.KEY_VERSI_APP);
        if (a == null) {
            return "0";
        }
        return a;
    }

    public void setKunciSidikJari(Boolean status) {
        try {
            SessionManager session = new SessionManager(context);
            session.createKunciSidikJari(status);
        } catch (Exception e) {
        }
    }

    public boolean getKunciSidikJari() {
        SessionManager session = new SessionManager(context);
        return session.IsKunciSidikJari();
    }

    public void setTransaksiSidikJari(Boolean status) {
        try {
            SessionManager session = new SessionManager(context);
            session.createTransaksiSidikJari(status);
        } catch (Exception e) {
        }
    }

    public boolean getTransaksiKunciSidikJari() {
        SessionManager session = new SessionManager(context);
        return session.IsTransaksiSidikJari();
    }
}
