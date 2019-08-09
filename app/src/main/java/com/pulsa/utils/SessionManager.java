package com.pulsa.utils;

/**
 * Created by Marketing on 8/15/2015.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.pulsa.pendaftaranLogin.Login;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressLint("CommitPrefEdit")
public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;
    Editor editors;

    // Context
    Context _context;

    // Shared pref mode
    public int PRIVATE_MODE = 0;

    // nama sharepreference
    public static final String PREF_NAME = "SesiBebasPay";

    // All Shared Preferences Keys
    public static final String IS_SUKSES = "IsLoggedIn";
    public static final String IS_SERVER = "IsServerIn";
    public static final String IS_NOTIFIKASI_ERROR = "sessionNotifikasiError";
    public static final String IS_KUNCI_SIDIKJARI = "sessionKunciSidikJari";
    public static final String IS_TRANSAKSI_SIDIKJARI = "sessionTransaksiSidikJari";
    public static final String IS_ENIP = "IsENIP";


    public static final String KEY_VERIVICATIONID = "VerifikasiIdApp";
    public static final String KEY_SERVER = "server";
    public static final String KEY_LEVEL = "level";
    public static final String KEY_HP = "hp";
    public static final String key_fa = "fa";
    public static final String key_fb = "fb";
    public static final String key_con = "con";
    public static final String key_t = "t";
    public static final String key_date = "date";
    public static final String key_activitys = "ac";
    public static final String key_fragment = "fr";
    public static final String KEY_UPGRADEMD = "UPGRADEMD";
    public static final String KEY_AUTOCOMPLETE_TUJUAN = "AUTOCOMPLETETUJUAN";
    public static final String KEY_UPGRADE_PREMIUM = "UPGRADE_PREMIUM";
    public static final String KEY_USER_REVERAL = "USERREFERAL";
    public static final String SESSION_MODE = "online";
    public static final String SERVER_URL = "url";
    public static final String KEY_PRINTER = "address";
    public static final String KEY_REPLIKASI = "replikasi";
    public static final String KEY_SESSION_DATE = "sessiondate";
    public static final String KEY_AKTIVITAS_TERAHIR = "sessionaktifitasterahir";
    public static final String KEY_ENIP = "sessionENIP";
    public static final String KEY_USERCS_TRANSAKSI = "userCSTransaksi";
    public static final String KEY_USERCS_DEPOSIT = "userCSDeposit";
    public static final String KEY_FEE = "fee";

    public static final String KEY_REWARD = "rewardApp";
    public static final String KEY_TAMPIL_REWARD = "tampilReward";

    public static final String KEY_NAMA = "sessionNama";
    public static final String KEY_SALDO= "sessionSaldo";
    public static final String KEY_KOMISI = "sessionKomisi";
    public static final String KEY_MINDEPOSIT = "sessionMinDepo";
    public static final String KEY_REFERAL = "sessionReferal";
    public static final String KEY_REFERAL_UPLINE = "sessionReferalUpline";
    public static final String KEY_PARAM_UPDATE = "paramUpdate";
    public static final String KEY_MENU_PREMIUM = "paramAppPremium";
    public static final String KEY_BONUS_SALDO_DOWNLINE = "bonussaldoDownline";
    public static final String KEY_CASHBACK = "sessionCashback";
    public static final String KEY_SYARAT = "sessionSyarat";
    public static final String KEY_KODE_LEVEL = "sessionKodeLevel";
    public static final String KEY_PENJELASAN_CASHBACK = "sessionPenjelasanCashback";

    public static final String KEY_AKTIVITAS_SERVICE = "sessionAktifitasSession";
    public static final String KEY_VERSI_APP = "sessionVersiApp";

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editors = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String username, String password) {
        // Storing login value as TRUE
        editor.putBoolean(IS_SUKSES, true);
        // editor.putString(KEY_NAME, name);
        editor.putString(key_activitys, username);
        editor.putString(key_fragment, password);
        editor.commit();
    }

    public void createTglSession(String con, String date) {
        // Storing login value as TRUE
        // editor.putString(KEY_NAME, name);
        editor.putString(key_con, con);
        editor.putString(key_date, date);
        editor.commit();
    }

    public void createFaSession(String fa) {
        // Storing fa value as TRUE
        // editor.putString(KEY_NAME, name);
        editor.putString(key_fa, fa);
        editor.commit();
    }

    public void createFbSession(String lnt) {
        // editor.putString(KEY_NAME, name);
        editor.putString(key_fb, lnt);
        editor.commit();
    }

    public void createVerifikasiIdApp(String idapp) {
        editor.putString(KEY_VERIVICATIONID, idapp);
        editor.commit();
    }

    public void createServerSession(String server) {
        // Storing login value as TRUE
        editor.putBoolean(IS_SERVER, true);
        editor.putString(KEY_SERVER, server);
        // commit changes
        editor.commit();
    }

    public void createAutocompleteTujuan(boolean autocomplete) {
        // Storing login value as TRUE
        editor.putBoolean(KEY_AUTOCOMPLETE_TUJUAN, autocomplete);
        // commit changes
        editor.commit();
    }

    public void createLVLSession(String level, Boolean upgradeMD, Boolean upgradePremium, Boolean userReferal, Boolean minimalDeposit, Boolean tampilReward, String allFee, String kodeReferal, String saldoUser, Boolean parameterUpdate, Boolean menuPremium, String saldoDownline, String kodeLevelUSer) {
        if (level.length() > 0) {
            editor.putString(KEY_LEVEL, level);
            editor.putBoolean(KEY_UPGRADEMD, upgradeMD);
            editor.putBoolean(KEY_UPGRADE_PREMIUM, upgradePremium);
            editor.putBoolean(KEY_USER_REVERAL, userReferal);
            editor.putBoolean(KEY_MINDEPOSIT, minimalDeposit);
            editor.putBoolean(KEY_TAMPIL_REWARD, tampilReward);
            editor.putString(KEY_FEE, allFee);
            editor.putString(KEY_REFERAL, kodeReferal);
            editor.putString(KEY_SALDO, saldoUser);
            editor.putBoolean(KEY_PARAM_UPDATE, parameterUpdate);
            editor.putString(KEY_BONUS_SALDO_DOWNLINE, saldoDownline);
            editor.putString(KEY_KODE_LEVEL, kodeLevelUSer);
        }
        editor.putBoolean(KEY_MENU_PREMIUM, menuPremium);

        // commit changes
        editor.commit();
    }

    public void setStringArrayPref(JSONArray arraySyarat) {
        JSONArray a = new JSONArray();
        for (int i = 0; i < arraySyarat.length(); i++) {
            try {
                a.put(arraySyarat.get(i));
            } catch (Exception e) {
                a.put(null);
            }
        }

        if (arraySyarat != null) {
            editor.putString(KEY_SYARAT, a.toString());
        } else {
            editor.putString(KEY_SYARAT, null);
        }
        // commit changes
        editor.commit();
    }

    public ArrayList<String> getStringArrayPref() {
        String json = pref.getString(KEY_SYARAT, null);
        ArrayList<String> array = new ArrayList<String>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    array.add(url);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return array;
    }

    public void createServerUrl(String server) {
        editor.putString(SERVER_URL, server);
        editor.commit();
    }

    public void createHPSession(String hape) {
        editor.putString(KEY_HP, hape);
        // commit changes
        editor.commit();
    }

    public void createSessionMode(String mode) {
        editor.putString(SESSION_MODE, mode);
        // commit changes
        editor.commit();
    }

    public void createAddressPrinter(String address) {
        editor.putString(KEY_PRINTER, address);
        // commit changes
        editor.commit();
    }

    public void createRepliaksi(String status) {
        editor.putString(KEY_REPLIKASI, status);
        // commit changes
        editor.commit();
    }

    public void createSkipInformasi(String tgl) {
        editor.putString(KEY_SESSION_DATE, tgl);
        // commit changes
        editor.commit();
    }

    public void createSesiNama(String nama) {
        editor.putString(KEY_NAMA, nama);
        // commit changes
        editor.commit();
    }

    public void createSesiSaldo(String saldo) {
        editor.putString(KEY_SALDO, saldo);
        // commit changes
        editor.commit();
    }

    public void createSesiKomisi(String komisi) {
        editor.putString(KEY_KOMISI, komisi);
        // commit changes
        editor.commit();
    }

    public void createNotifikasiError(Boolean status) {
        editor.putBoolean(IS_NOTIFIKASI_ERROR, status);
        editor.commit();
    }

    public void createKunciSidikJari(Boolean status) {
        editor.putBoolean(IS_KUNCI_SIDIKJARI, status);
        editor.commit();
    }

    public void createTransaksiSidikJari(Boolean status) {
        editor.putBoolean(IS_TRANSAKSI_SIDIKJARI, status);
        editor.commit();
    }

    public void createAktifitasTerahir(String datetime) {
        editor.putString(KEY_AKTIVITAS_TERAHIR, datetime);
        // commit changes
        editor.commit();
    }

    public void createAktifitasRestart(String datetime) {
        editor.putString(KEY_AKTIVITAS_SERVICE, datetime);
        // commit changes
        editor.commit();
    }

    public void createENIP(String enip) {
        editor.putString(KEY_ENIP, enip);
        editor.commit();
    }

    public void setENIPStatus(Boolean status) {
        editor.putBoolean(IS_ENIP, status);
        editor.commit();
    }

    public void createSesiCSTransaksi(String user) {
        editor.putString(KEY_USERCS_TRANSAKSI, user);
        // commit changes
        editor.commit();
    }


    public void createSesiCSDeposit(String user) {
        editor.putString(KEY_USERCS_DEPOSIT, user);
        // commit changes
        editor.commit();
    }

    public void createReward(String reward) {
        editor.putString(KEY_REWARD, reward);
        // commit changes
        editor.commit();
    }

    public void cashBack(String cashback) {
        editor.putString(KEY_CASHBACK, cashback);
        // commit changes
        editor.commit();
    }

    public void setpenjelasanCashBack(String penjelasan) {
        editor.putString(KEY_PENJELASAN_CASHBACK, penjelasan);
        // commit changes
        editor.commit();
    }

    public void createReferalUpline(String referal) {
        editor.putString(KEY_REFERAL_UPLINE, referal);
        // commit changes
        editor.commit();
    }

    public void createVersi(String versi) {
        editor.putString(KEY_VERSI_APP, versi);
        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (getUserDetails().get(key_fa) == null) {
            Intent i = new Intent(_context, Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            _context.startActivity(i);
        }
    }

    public String getSyaratUpgradePro() {
        return pref.getString(KEY_SYARAT, null);
    }

    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(key_fa, pref.getString(key_fa, null));
        user.put(KEY_LEVEL, pref.getString(KEY_LEVEL, null));
        user.put(KEY_HP, pref.getString(KEY_HP, null));
        user.put(key_fb, pref.getString(key_fb, null));
        user.put(key_t, pref.getString(key_t, null));
        user.put(key_con, pref.getString(key_con, null));
        user.put(key_date, pref.getString(key_date, null));
        user.put(key_activitys, pref.getString(key_activitys, null));
        user.put(key_fragment, pref.getString(key_fragment, null));
        user.put(KEY_SERVER, pref.getString(KEY_SERVER, null));
        user.put(SESSION_MODE, pref.getString(SESSION_MODE, null));
        user.put(SERVER_URL, pref.getString(SERVER_URL, null));
        user.put(KEY_PRINTER, pref.getString(KEY_PRINTER, null));
        user.put(KEY_REPLIKASI, pref.getString(KEY_REPLIKASI, null));
        user.put(KEY_SESSION_DATE, pref.getString(KEY_SESSION_DATE, null));
        user.put(KEY_AKTIVITAS_TERAHIR, pref.getString(KEY_AKTIVITAS_TERAHIR, null));
        user.put(KEY_VERIVICATIONID, pref.getString(KEY_VERIVICATIONID, null));
        user.put(KEY_KOMISI, pref.getString(KEY_KOMISI, null));
        user.put(KEY_SALDO, pref.getString(KEY_SALDO, "-"));
        user.put(KEY_NAMA, pref.getString(KEY_NAMA, null));
        user.put(KEY_ENIP, pref.getString(KEY_ENIP, null));
        user.put(KEY_USERCS_TRANSAKSI, pref.getString(KEY_USERCS_TRANSAKSI, null));
        user.put(KEY_USERCS_DEPOSIT, pref.getString(KEY_USERCS_DEPOSIT, null));
        user.put(KEY_FEE, pref.getString(KEY_FEE, null));
        user.put(KEY_REFERAL, pref.getString(KEY_REFERAL, null));
        user.put(KEY_REFERAL_UPLINE, pref.getString(KEY_REFERAL_UPLINE, null));
        user.put(KEY_BONUS_SALDO_DOWNLINE, pref.getString(KEY_BONUS_SALDO_DOWNLINE, null));
        user.put(KEY_CASHBACK, pref.getString(KEY_CASHBACK, null));
        user.put(KEY_KODE_LEVEL, pref.getString(KEY_KODE_LEVEL, null));
        user.put(KEY_PENJELASAN_CASHBACK, pref.getString(KEY_PENJELASAN_CASHBACK, null));
        user.put(KEY_AKTIVITAS_SERVICE, pref.getString(KEY_AKTIVITAS_SERVICE, null));
        user.put(KEY_VERSI_APP, pref.getString(KEY_VERSI_APP, null));

        user.put(KEY_REWARD, pref.getString(KEY_REWARD, null));
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.putBoolean(IS_SUKSES, false);
        editor.clear();
        editor.commit();
        Intent i = new Intent(_context, Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

    public void clear() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_SUKSES, false);
    }

    public boolean IsServer() { return pref.getBoolean(IS_SERVER, false);
    }

    public boolean IsNotifikasiError() {
        return pref.getBoolean(IS_NOTIFIKASI_ERROR, true);
    }

    public boolean IsKunciSidikJari() {
        return pref.getBoolean(IS_KUNCI_SIDIKJARI, false);
    }

    public boolean IsTransaksiSidikJari() {
        return pref.getBoolean(IS_TRANSAKSI_SIDIKJARI, false);
    }

    public boolean isENIPAplikasi() {
        return pref.getBoolean(IS_ENIP, true);
    }

    public boolean isMinDeposit() {
        return pref.getBoolean(KEY_MINDEPOSIT, false);
    }


    public boolean isUserReferal() {
        return pref.getBoolean(KEY_USER_REVERAL, false);
    }

    public boolean isTampilReward() {
        return pref.getBoolean(KEY_TAMPIL_REWARD, false);
    }

    public boolean isParameterUpdateApp() {
        return pref.getBoolean(KEY_PARAM_UPDATE, false);
    }

    public boolean isParameterMenuPremium() {
        return pref.getBoolean(KEY_MENU_PREMIUM, false);
    }

    public boolean isUpgradePremium() {
        return pref.getBoolean(KEY_UPGRADE_PREMIUM, false);
    }

    public boolean isUpgradeMD() {
        return pref.getBoolean(KEY_UPGRADEMD, false);
    }

    public boolean isAutocomplete() {
        return pref.getBoolean(KEY_AUTOCOMPLETE_TUJUAN, true);
    }

    public void DESTROY(){
        editor.clear();
        editor.commit();
    }
}