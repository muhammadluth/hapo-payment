package com.pulsa.setGet;

public class DataProduk {
    private String judul;
    private String image;
    private String harga;
    private String quota;
    private String aturan;
    private String kodeProduk;
    private String kategori;
    private String kodeCek;
    private int imageResource;
    private int imageDrawable;
    private boolean useID;
    private String hint;
    private String tvNama;
    private String url;
    private String keterangan;
    private String nominal;
    private String fee;

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public void setQuota(String quota) {
        this.quota = quota;
    }

    public void setAturan(String aturan) {
        this.aturan = aturan;
    }

    public String getImage() {
        return image;
    }

    public String getJudul() {
        return judul;
    }

    public String getAturan() {
        return aturan;
    }

    public String getHarga() {
        return harga;
    }

    public String getQuota() {
        return quota;
    }

    public String getKodeProduk() {
        return kodeProduk;
    }

    public void setKodeProduk(String kodeProduk) {
        this.kodeProduk = kodeProduk;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKodeCek(String kodeCek) {
        this.kodeCek = kodeCek;
    }

    public String getKodeCek() {
        return kodeCek;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageDrawable(int imageDrawable) {
        this.imageDrawable = imageDrawable;
    }

    public int getImageDrawable() {
        return imageDrawable;
    }

    public void setUseID(boolean useID) {
        this.useID = useID;
    }

    public boolean getUseID() {
        return useID;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getHint() {
        return hint;
    }

    public String getTvNama() {
        return tvNama;
    }

    public void setTvNama(String tvNama) {
        this.tvNama = tvNama;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getFee() {
        return fee;
    }
}