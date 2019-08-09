package com.pulsa.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by IT-Staff on 01/07/2016.
 */
public class dbChat extends SQLiteOpenHelper {
    public static final String DB_NAME = "dbChat";
    private static final int DB_VERSION = 5;
    public static final String TABLE_NAME = "tblchat";
    public static final String COL_ISI = "isi";
    public static final String COL_PENGIRIM = "pengirim";
    public static final String COL_TUJUAN = "tujuan";
    public static final String COL_TANGGAL = "tanggal";
    public static final String COL_STATUS = "status";

    private static final String STRING_CREATE = "CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_ISI + " TEXT, " + COL_PENGIRIM + " TEXT, " + COL_TANGGAL + " TEXT, " + COL_TUJUAN + " TEXT, " + COL_STATUS + " TEXT );";

    public dbChat(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(STRING_CREATE);
    }

    public int getDataCount() {
        int num = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String QUERY = "SELECT * FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(QUERY + " WHERE status = ? ", new String[]{"0"});
            num = cursor.getCount();
            db.close();
            return num;
        } catch (Exception e) {
           // Log.e("error", e + "");
        }
        return 0;
    }

    public void UpdateStatus(String pengirim) {
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "UPDATE " + TABLE_NAME + " SET status = '1' where " + COL_PENGIRIM + " = " + "'" + pengirim + "'";
        //Log.d("query", updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }

    public void Tambah(String isi, String pengirim, String status, String tanggal, String tujuan) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put(COL_ISI, isi);
            values.put(COL_PENGIRIM, pengirim);
            values.put(COL_PENGIRIM, status);
            values.put(COL_TANGGAL, tanggal);
            values.put(COL_TUJUAN, tujuan);
            db.insert(TABLE_NAME, null, values);

            Log.e("INSERT CHAT", isi);
        }catch (Exception e){
            Log.e("problem", e + "");
        }
        db.close();
    }

    public void Hapus() {
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "DELETE from " + TABLE_NAME;
       // Log.d("query", updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }

    public void HapusTanggal(String tanggal) {
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "DELETE from " + TABLE_NAME + " where " + COL_TANGGAL + " = " + "'" + tanggal + "'";
        // Log.d("query", updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }
    public void HapusAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
