package com.pulsa.db;

/**
 * Created by IT-STAFF on 06/11/2017.
 */

public class Favorit {
    private String id;
    private String judul;
    private String kodeProduk;
    private String tujuan;
    private String operator;
    private String kategori;
    private String menu;
    private String status;
    private String user;
    private String IDSQL;
    private String syncStatus;

    public void setId(String id) {
        this.id = id;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setKodeProduk(String kodeProduk) {
        this.kodeProduk = kodeProduk;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public String getKodeProduk() {
        return kodeProduk;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMenu() {
        return menu;
    }

    public String getStatus() {
        return status;
    }

    public void setIDSQL(String IDSQL) {
        this.IDSQL = IDSQL;
    }

    public String getIDSQL() {
        return IDSQL;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getSyncStatus() {
        return syncStatus;
    }
}
