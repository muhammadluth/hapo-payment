package com.pulsa.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by IT-Rochim on 18/05/2016.
 */
public class dbinbox extends SQLiteOpenHelper {
    private static final String DB_NAME = "dbPesanMasuk";
    private static final int DB_VERSION = 1;
    public static Cursor cursor;
    public static final String TABLE_INBOX = "tblinbox";
    public static final String COL_NAME = "isi";
    public static final String COL_DATE = "tgl";
    private static final String STRING_CREATE = "CREATE TABLE "+TABLE_INBOX+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COL_NAME+" TEXT, "+COL_DATE+" DATE);";

    public dbinbox(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(STRING_CREATE);
    }

    public void Tambah(Data data) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put(COL_NAME, data.getIsi());
            values.put(COL_DATE, data.getDateTitle());
            db.insert(TABLE_INBOX, null, values);
            Log.e("INSERT INBOX", data.getIsi());
        }catch (Exception e){
            Log.e("problem", e + "");
        }
        db.close();
    }

    public Cursor getAllDataInbox() {
        SQLiteDatabase mDb = this.getWritableDatabase();
        String[] columns = new String[]{"_id", COL_NAME, COL_DATE};
        Cursor mCursor = mDb.query(TABLE_INBOX, columns, null, null, null, null, "_id DESC", "100");
        if (mCursor != null) {
            Log.e("ISI","ADA");
            mCursor.moveToFirst();
        }else {
            Log.e("ISI","KOSONG");
        }
        return mCursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INBOX);
        onCreate(db);
    }

    public void HapusALL() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INBOX,null,null);
    }

    public void Hapus(String rowId) {
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "Delete from "+TABLE_INBOX+" where _id ="+"'"+ rowId +"'";
        Log.d("query",updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }
}
