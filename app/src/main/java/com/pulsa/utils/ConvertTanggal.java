package com.pulsa.utils;

import java.util.StringTokenizer;

public class ConvertTanggal {

    public static boolean hariIni(String tanggal) {
        if (CommonMethods.getCurrentDate().equalsIgnoreCase(tanggal.replaceAll("[^0-9]", " "))) {
            return true;
        }
        return false;
    }

    public static String tanggal(String date) {
        String pemisah = "-";
        if (!date.contains("-")) {
            pemisah = " ";
        }
        StringTokenizer st = new StringTokenizer(date, pemisah);
        String tgl = st.nextToken();
        int month = Integer.parseInt(st.nextToken()) - 1;
        String thn = st.nextToken();
        String bulan = "";
        if (month == 0) {
            bulan = "Januari";
        } else if (month == 1) {
            bulan = "Februari";
        } else if (month == 2) {
            bulan = "Maret";
        } else if (month == 3) {
            bulan = "April";
        } else if (month == 4) {
            bulan = "Mei";
        } else if (month == 5) {
            bulan = "Juni";
        } else if (month == 6) {
            bulan = "Juli";
        } else if (month == 7) {
            bulan = "Agustus";
        } else if (month == 8) {
            bulan = "September";
        } else if (month == 9) {
            bulan = "Oktober";
        } else if (month == 10) {
            bulan = "November";
        } else if (month == 11) {
            bulan = "Desember";
        } else {
            bulan = String.valueOf(month);
        }

        return tgl + " " + bulan + " " + thn;
    }

    public static String tanggal2(String date) {
        String pemisah = "-";
        if (!date.contains("-")) {
            pemisah = " ";
        }
        StringTokenizer st = new StringTokenizer(date, pemisah);
        String tgl = st.nextToken();
        String month = st.nextToken().trim();
        String thn = st.nextToken();
        String bulan = "";
        if (month.equalsIgnoreCase("Januari")) {
            bulan = "1";
        } else if (month.equalsIgnoreCase("Februari")) {
            bulan = "2";
        } else if (month.equalsIgnoreCase("Maret")) {
            bulan = "3";
        } else if (month.equalsIgnoreCase("April")) {
            bulan = "4";
        } else if (month.equalsIgnoreCase("Mei")) {
            bulan = "5";
        } else if (month.equalsIgnoreCase("Juni")) {
            bulan = "6";
        } else if (month.equalsIgnoreCase("Juli")) {
            bulan = "7";
        } else if (month.equalsIgnoreCase("Agustus")) {
            bulan = "8";
        } else if (month.equalsIgnoreCase("September")) {
            bulan = "9";
        } else if (month.equalsIgnoreCase("Oktober")) {
            bulan = "10";
        } else if (month.equalsIgnoreCase("November")) {
            bulan = "11";
        } else if (month.equalsIgnoreCase("Desember")) {
            bulan = "12";
        } else {
            bulan = "0";
        }

        return thn + "-" + bulan + "-" + tgl;
    }
}
