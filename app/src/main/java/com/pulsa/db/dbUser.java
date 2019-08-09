package com.pulsa.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class dbUser extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "membersdb";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_NAME = "dbuser";
    private static final String COL_NAME = "username";
    private static final String COL_DATE = "password";
    private static final String COL_STATUS = "status";
    private static final String COL_WAKTU = "waktu";

    private static final String STRING_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NAME + " TEXT, " + COL_STATUS + " TEXT, " + COL_DATE + " TEXT , " + COL_WAKTU + " TEXT DEFAULT 0);";

    public dbUser(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(STRING_CREATE);
        ContentValues cv = new ContentValues(3);
        cv.put("username", "united");
        cv.put("password", "1234");
        cv.put("status", "1");
        db.insert(TABLE_NAME, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            try {
                db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COL_WAKTU + " TEXT DEFAULT 0");
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
            }
        } else {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    public HashMap<String, String> appKey() {
        SQLiteDatabase mDb = this.getWritableDatabase();
        HashMap<String, String> hashMapKunci = new HashMap<String, String>();
        Cursor cursor = mDb.rawQuery("SELECT status,password,waktu FROM dbuser ORDER BY _id DESC LIMIT 1", null);
        if (cursor.moveToFirst()) {
            do {
                hashMapKunci.put("status", cursor.getString(0));
                hashMapKunci.put("password", cursor.getString(1));
                hashMapKunci.put("waktu", cursor.getString(2));
            } while (cursor.moveToNext());
        }
       return hashMapKunci;
    }

    public String kunci() {
        SQLiteDatabase mDb = this.getWritableDatabase();
        Cursor mCursor = null;
        String format = "Kosong";
        mCursor = mDb.query(true, TABLE_NAME, new String[]{"_id", COL_DATE}, null, null, null, null, "_id DESC","1");

        if (mCursor != null) {
            mCursor.moveToFirst();
            format = mCursor.getString(1);
        }
        return format;
    }

    public String status() {
        SQLiteDatabase mDb = this.getWritableDatabase();
        Cursor mCursor = null;
        String format = "Kosong";
        mCursor = mDb.query(true, TABLE_NAME, new String[]{"_id", COL_STATUS}, null, null, null, null, "_id DESC","1");

        if (mCursor != null) {
            mCursor.moveToFirst();
            format = mCursor.getString(1);

        }
        return format;
    }

    public String waktu() {
        SQLiteDatabase mDb = this.getWritableDatabase();
        Cursor mCursor = null;
        String format = "Kosong";
        mCursor = mDb.query(true, TABLE_NAME, new String[]{"_id", COL_WAKTU}, null, null, null, null, "_id DESC","1");

        if (mCursor != null) {
            mCursor.moveToFirst();
            format = mCursor.getString(1);
        }
       return format;
    }

}