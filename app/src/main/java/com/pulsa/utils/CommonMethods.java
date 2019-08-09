package com.pulsa.utils;

/**
 * Created by IT-Staff on 29/06/2016.
 */

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CommonMethods {

    public static String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        String jam = df.format(c.getTime());
        return jam;
    }

    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd MM yyyy");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        //c.add(Calendar.HOUR, 23);
        String tgl = df.format(c.getTime());
        return tgl;
    }

    public static String getCurrentDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        String tgl = df.format(c.getTime());
        return tgl;
    }

    public static String getCurrentDateTimePlusHour(int hour) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        c.add(Calendar.HOUR, hour);
        String tgl = df.format(c.getTime());
        return tgl;
    }

    public static String getCurrentDateBayi() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        c.add(Calendar.YEAR, 1);
        String tgl = df.format(c.getTime());
        return tgl;
    }

    public static String getCurrentDateTimesSecon() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        String tgl = df.format(c.getTime());
        return tgl;
    }

    public static String getCurrentDateTimes() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        String tgl = df.format(c.getTime());
        return tgl;
    }

    public static String getCurrentDateTrim() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        String tgl = df.format(c.getTime());
        return tgl;
    }

    public static String getCurrentDateTimesINT() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        String tgl = df.format(c.getTime());
        return tgl;
    }

    public static String getCurrentDateTrimNotTimezon() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String tgl = df.format(c.getTime());
        return tgl;
    }

    public static String getSetTanggal(String tglInput) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        String tgl = df.format(c.getTime());
        tgl = tgl + "-" + tglInput;
        return tgl;
    }

    public static String getCurrentDateTimePlusMinutServices(int minute) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        c.add(Calendar.MINUTE, minute);
        String tgl = df.format(c.getTime());
        return tgl;
    }

    public static String getFormatTanggalIdo(String tgl) {
        String MyDates = tgl;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tgl);
            return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(date);
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }
        return MyDates;
    }
}
