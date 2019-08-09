package com.pulsa.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by IT-STAFF on 06/11/2017.
 */

public class dbFavorit extends SQLiteOpenHelper {
    private static final String DB_NAME = "dbFavorit";
    private static int DB_VERSION = 6;
    private String TABLE_FAVORITE = "tblfavorits";

    private String COL_JUDUL = "judul";
    private String COL_TUJUAN = "tujuan";
    private String COL_KODE_PRODUK = "produk";
    private String COL_MENU = "menu";
    private String COL_AGEN = "kode_agen";
    private String COL_STATUS = "status";
    private String COL_IDSQL = "idSQL";
    private String COL_STATUS_SYNC = "sync_status";

    public dbFavorit(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_FAVORITE + " (_id INTEGER PRIMARY KEY, "
                + COL_JUDUL + " TEXT, "
                + COL_TUJUAN + " TEXT, "
                + COL_KODE_PRODUK + " TEXT, "
                + COL_STATUS + " TEXT, "
                + COL_AGEN + " TEXT, "
                + COL_IDSQL + " TEXT, "
                + COL_STATUS_SYNC + " TEXT, "
                + COL_MENU + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        onCreate(db);
    }


    public void insert(HashMap<String, String> queryValues) {
        //Log.e("insert", queryValues.toString());
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_JUDUL, queryValues.get("judul"));
        values.put(COL_TUJUAN, queryValues.get("tujuan"));
        values.put(COL_KODE_PRODUK, queryValues.get("produk"));
        values.put(COL_MENU, queryValues.get("menu"));
        values.put(COL_IDSQL, queryValues.get("idsql"));
        values.put(COL_STATUS, "no");
        values.put(COL_AGEN, "agen");

        if (queryValues.get("idsql") != null) {
            values.put(COL_STATUS_SYNC, queryValues.get("idsql"));
        } else {
            values.put(COL_STATUS_SYNC, "");
        }

        database.insert(TABLE_FAVORITE, null, values);
        database.close();
    }

    public void updateIDSQL(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "UPDATE " + TABLE_FAVORITE
                + " SET " + COL_IDSQL + " = " + queryValues.get("idsql")
                + " WHERE "
                + COL_STATUS + " != " + "'" + "del" + "' AND "
                + COL_KODE_PRODUK + " = " + "'" + queryValues.get("produk") + "' AND "
                + COL_TUJUAN + " = " + "'" + queryValues.get("tujuan") + "'";

        //Log.e("update", updateQuery);

        database.execSQL(updateQuery);
        database.close();
    }

    public String cekDataAmbilJudul(String tujuan, String kodeProduk) {
        String hasil = null;
        SQLiteDatabase mDb = this.getWritableDatabase();
        String[] columns = new String[]{"_id", COL_JUDUL, COL_TUJUAN, COL_KODE_PRODUK};
        Cursor mCursor = mDb.query(TABLE_FAVORITE, columns,
                COL_TUJUAN + " =? AND "
                        + COL_KODE_PRODUK + " =? AND "
                        + COL_STATUS + " !=?"
                , new String[]{tujuan, kodeProduk, "del"}, null, null, null);

        if (mCursor.moveToFirst()) {
            hasil = mCursor.getString(1);
            mCursor.close();
        }
        return hasil;
    }

    public String cekDataSync(String tujuan, String kodeProduk) {
        String hasil = null;
        SQLiteDatabase mDb = this.getWritableDatabase();
        String[] columns = new String[]{"_id", COL_JUDUL, COL_TUJUAN, COL_KODE_PRODUK};
        Cursor mCursor = mDb.query(TABLE_FAVORITE, columns,
                COL_TUJUAN + " =? AND "
                        + COL_KODE_PRODUK + " =? "
                , new String[]{tujuan, kodeProduk}, null, null, null);

        if (mCursor.moveToFirst()) {
            hasil = String.valueOf(mCursor.getCount());
            mCursor.close();
        }
        return hasil;
    }

    public String cekIDSQL(String tujuan, String kodeProduk) {
        String hasil = null;
        SQLiteDatabase mDb = this.getWritableDatabase();
        String[] columns = new String[]{"_id", COL_JUDUL, COL_TUJUAN, COL_KODE_PRODUK, COL_IDSQL};
        Cursor mCursor = mDb.query(TABLE_FAVORITE, columns,
                COL_TUJUAN + " =? AND "
                        + COL_KODE_PRODUK + " =? "
                , new String[]{tujuan, kodeProduk}, null, null, null);

        if (mCursor.moveToFirst()) {
            hasil = mCursor.getString(4);
            mCursor.close();
        }
        return hasil;
    }

    //update status sync jika upload sukses atau opsi hapus jika udah selesai hapus dari could
    public void updateSyncStatus(String id, String aksi) {
        SQLiteDatabase database = this.getWritableDatabase();
        if (aksi.equalsIgnoreCase("del")) {
            String updateQuery = "UPDATE " + TABLE_FAVORITE
                    + " SET " + COL_STATUS + " = '" + aksi + "'"
                    + " WHERE "
                    + "_id =" + "'" + id + "' ";

            database.execSQL(updateQuery);
            database.close();
        }
    }

    public void updateSyncStatusSync(String id, String aksi, String idsql) {
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "UPDATE " + TABLE_FAVORITE
                + " SET " + COL_STATUS + " = '" + aksi + "'"
                + "," + COL_STATUS_SYNC + " = 'uptodate'"
                + "," + COL_IDSQL + " = '" + idsql + "'"
                + " WHERE "
                + "_id =" + "'" + id + "' ";
        //Log.e("updateSyncStatusSync", updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }

    //ubah status menjadi del untuk hapus data could
    public void hapusDataLocal() {
        Log.e("hapus local", "oke");
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "DELETE FROM " + TABLE_FAVORITE + " WHERE " + COL_STATUS + " = " + "'del'";
        database.execSQL(updateQuery);
        database.close();

        Log.e("favorit", getAllFavoritTRX("TRX").size() + "**");

    }

    public void hapusDataLocalArray(ArrayList<String> a) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(String.format("DELETE FROM " + TABLE_FAVORITE + " WHERE idSQL IN (%s);", a));
        database.close();
    }

    public void updateSyncStatusDeleteAll(String menu) {
        if (menu.equalsIgnoreCase("Transfer Downline")) {
            SQLiteDatabase database = this.getWritableDatabase();
            String updateQuery = "UPDATE " + TABLE_FAVORITE
                    + " SET " + COL_STATUS + " = 'del'"
                    + " WHERE "
                    + COL_MENU + " =" + "'" + menu + "' ";
            database.execSQL(updateQuery);
            database.close();
        } else {
            SQLiteDatabase database = this.getWritableDatabase();
            String updateQuery = "UPDATE " + TABLE_FAVORITE
                    + " SET " + COL_STATUS + " = 'del'"
                    + " WHERE "
                    + COL_MENU + " !=" + "'" + menu + "' ";
            database.execSQL(updateQuery);
            database.close();
        }

    }

    public void updateJudul(String id, String judul) {
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "UPDATE " + TABLE_FAVORITE
                + " SET " + COL_JUDUL + " = '" + judul + "'"
                + " WHERE "
                + "_id =" + "'" + id + "' ";
        database.execSQL(updateQuery);
        database.close();
    }

    public void Hapus() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITE, null, null);
    }


    public ArrayList<Favorit> uploadDataToCould() {
        ArrayList<Favorit> dataList = new ArrayList<Favorit>();
        SQLiteDatabase mDb = this.getReadableDatabase();
        String[] columns = new String[]{"_id", COL_JUDUL, COL_KODE_PRODUK, COL_TUJUAN, COL_MENU, COL_STATUS, COL_AGEN, COL_IDSQL, COL_STATUS_SYNC};
        Cursor mCursor = mDb.query(TABLE_FAVORITE, columns, null, null, null, null, "judul ASC", null);

        if (mCursor != null) {
            if (!mCursor.isLast()) {
                while (mCursor.moveToNext()) {
                    Favorit data = new Favorit();
                    data.setId(mCursor.getString(0));
                    data.setJudul(mCursor.getString(1));
                    data.setKodeProduk(mCursor.getString(2));
                    data.setTujuan(mCursor.getString(3));
                    data.setMenu(mCursor.getString(4));
                    data.setStatus(mCursor.getString(5));
                    data.setUser(mCursor.getString(6));
                    data.setIDSQL(mCursor.getString(7));
                    data.setSyncStatus(mCursor.getString(8));
                    dataList.add(data);
                }
            }
            mCursor.close();
        }
        return dataList;
    }

    public ArrayList<Favorit> getAllFavoritTRX(String menu) {
        ArrayList<Favorit> dataList = new ArrayList<Favorit>();
        try {
            SQLiteDatabase mDb = this.getReadableDatabase();
            String[] columns = new String[]{"_id", COL_JUDUL, COL_KODE_PRODUK, COL_TUJUAN, COL_MENU, COL_IDSQL};
            Cursor mCursor = null;
            if (menu.equalsIgnoreCase("Transfer Downline")) {
                //Log.e("favorit", menu);
                mCursor = mDb.query(TABLE_FAVORITE, columns, COL_STATUS + " !=? AND " + COL_MENU + " =? ", new String[]{"del", menu}, null, null, "judul ASC", null);
            } else {
                //Log.e("favorit", "all");
                mCursor = mDb.query(TABLE_FAVORITE, columns, COL_STATUS + " !=? AND " + COL_MENU + " !=? ", new String[]{"del", "Transfer Downline"}, null, null, "judul ASC", null);
            }
            //Log.e("mCursor", mCursor.getCount() + "--");
            if (mCursor != null) {
                if (!mCursor.isLast()) {
                    while (mCursor.moveToNext()) {
                        Favorit data = new Favorit();
                        data.setId(mCursor.getString(0));
                        data.setJudul(mCursor.getString(1));
                        data.setKodeProduk(mCursor.getString(2));
                        data.setTujuan(mCursor.getString(3));
                        data.setMenu(mCursor.getString(4));
                        data.setIDSQL(mCursor.getString(5));
                        dataList.add(data);
                    }
                }
                mCursor.close();
            }
        } catch (Exception e) {

        }
        return dataList;
    }

    public int getCount(String menu) {
        int hasil = 0;
        SQLiteDatabase mDb = this.getWritableDatabase();
        String[] columns = new String[]{"_id", COL_JUDUL, COL_TUJUAN, COL_KODE_PRODUK};
        Cursor mCursor = null;
        if (menu.equalsIgnoreCase("Transfer Downline")) {
            mCursor = mDb.query(TABLE_FAVORITE, columns,
                    COL_MENU + " =? " + " AND " +
                            COL_STATUS + " !=?  "
                    , new String[]{"Transfer Downline", "del"}, null, null, null);
        } else {
            mCursor = mDb.query(TABLE_FAVORITE, columns,
                    COL_MENU + " !=? " + " AND " +
                            COL_STATUS + " !=?  "
                    , new String[]{"Transfer Downline", "del"}, null, null, null);
        }

        if (mCursor.moveToFirst()) {
            hasil = mCursor.getCount();
            mCursor.close();
        }
        return hasil;
    }

}
