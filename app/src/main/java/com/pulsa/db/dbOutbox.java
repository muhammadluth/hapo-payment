package com.pulsa.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.pulsa.utils.CommonMethods;
import com.pulsa.utils.ConvertTanggal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IT-Staff on 23/12/2016.
 */

public class dbOutbox extends SQLiteOpenHelper {

    private static final String DB_NAME = "dbOutbox";
    private static int DB_VERSION = 1;
    public static final String TABLE_OUTBOX = "tbloutbox";
    public static final String COL_NAMA = "nama";
    public static final String COL_KODE = "kode";
    public static final String COL_TANGGAL = "tanggal";
    public static final String COL_DATE_TIME = "datetime";
    public static final String COL_TUJUAN = "tujuan";
    public static final String COL_HARGA = "harga";
    public static final String COL_POIN = "poin";
    public static final String COL_KETERANGAN = "keterangan";
    Context context;

    private static final String STRING_CREATE_DATA = "CREATE TABLE " + TABLE_OUTBOX + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NAMA + " TEXT, " + COL_TANGGAL + " TEXT, " + COL_TUJUAN + " TEXT, " + COL_KODE + " TEXT, " + COL_DATE_TIME + " TEXT, " + COL_HARGA + " TEXT, " + COL_POIN + " TEXT, " + COL_KETERANGAN + " TEXT );";

    public dbOutbox(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(STRING_CREATE_DATA);
    }

    public void Tambah(String kode, String tujuan, String nama, String tanggal, String harga, String keterangan, String aksi) {
        SQLiteDatabase db = this.getWritableDatabase();
        String dateime = CommonMethods.getCurrentDateTime();
        try {
            ContentValues values = new ContentValues();
            values.put(COL_KODE, kode);
            values.put(COL_TUJUAN, tujuan);
            values.put(COL_NAMA, nama);
            values.put(COL_TANGGAL, tanggal);
            values.put(COL_DATE_TIME, dateime);
            values.put(COL_HARGA, harga);
            values.put(COL_KETERANGAN, keterangan);
            values.put(COL_POIN, aksi);
            db.insert(TABLE_OUTBOX, null, values);
        } catch (Exception e) {
            //Log.e("problem", e + "");
        }
        db.close();
        //Log.e("INSERT TO OUTBOX", kode);
    }

    public List<Data> getAllData(int offset) {
        String dateTitle = "";
        int limit = 100;
        int set = 0;
        if (offset == 0) {
            set = offset;
        } else {
            set = (offset - 1) * limit;
        }
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Data> dataList = null;

        dataList = new ArrayList<Data>();
        String QUERY = "select _id, kode, tujuan, tanggal, datetime, nama, harga, keterangan, poin from " + TABLE_OUTBOX + " ORDER BY _id DESC LIMIT " + limit + " OFFSET " + set;
        Cursor cursor = db.rawQuery(QUERY, null);
        if (cursor != null) {
            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    Data data = new Data();
                    try {
                        if (!dateTitle.equalsIgnoreCase(cursor.getString(4).substring(0, cursor.getString(4).indexOf(' ')))) {
                            dateTitle = cursor.getString(4).substring(0, cursor.getString(4).indexOf(' '));
                            data.setDateTitle(ConvertTanggal.tanggal(dateTitle));
                        } else {
                            data.setDateTitle("0");
                        }
                    } catch (Exception e) {
                        data.setDateTitle("-");
                    }

                    int id = Integer.parseInt(cursor.getString(0));
                    data.setId(id);
                    data.setKode(cursor.getString(1));
                    data.setOperator(cursor.getString(2));
                    data.setTglAwal(cursor.getString(3));
                    data.setTgl(cursor.getString(4));
                    data.setName(cursor.getString(5));
                    data.setHarga(cursor.getString(6));
                    data.setAksi(cursor.getString(8));

                    if (cursor.getString(7).length() > 3) {
                        data.setKeterangan(cursor.getString(7));
                        data.setJudul(cursor.getString(5));
                    } else {
                        data.setKeterangan("-");
                        data.setJudul("-");
                    }
                    dataList.add(data);
                }
            }
            cursor.close();
        }
        db.close();
        return dataList;
    }

    public void Hapus(String rowId) {
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "Delete from " + TABLE_OUTBOX + " where _id =" + "'" + rowId + "'";
        //Log.d("query", updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }

    public int perulanganCek(String inputText){
        SQLiteDatabase mDb = this.getWritableDatabase();
        String tgl = CommonMethods.getCurrentDate();
        Cursor mCursor = null;
        int format = 0;
        //Log.e("INPUT", inputText);
        if (inputText == null || inputText.length() == 0) {

        } else {
            Log.e("INPUT TEXT", inputText);
            mCursor = mDb.query(true, TABLE_OUTBOX, new String[]{"_id", COL_KODE}, COL_KODE + " like '%" + inputText.trim().replace(" ", "") + "%' and " + COL_TANGGAL + "= '" + tgl + "'", null, null, null, "_id DESC", null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
            format = mCursor.getCount();
            mCursor.close();
        }

        mDb.close();
        return format;
    }

    public void HapusBeberapa() {
        String tgl = CommonMethods.getCurrentDate();
        SQLiteDatabase database = this.getWritableDatabase();
        String deleteQuery = "Delete from " + TABLE_OUTBOX + " where " + COL_TANGGAL + " <> " + "'" + tgl + "'";
        //Log.e("DELETE",deleteQuery);
        database.execSQL(deleteQuery);
        database.close();
    }

    public String cariformat(String inputText) throws SQLException {
        SQLiteDatabase mDb = this.getWritableDatabase();
        String tgl = CommonMethods.getCurrentDate();
        Cursor mCursor = null;
        String format = "Kosong";
        //Log.e("INPUT", inputText);
        if (inputText == null || inputText.length() == 0) {

        } else {
            //Log.e("INPUT TEXT", inputText);
            mCursor = mDb.query(true, TABLE_OUTBOX, new String[]{"_id", COL_KODE}, COL_KODE + " like '%" + inputText.trim().replace(" ", "") + "%' and " + COL_TANGGAL + "= '" + tgl + "'", null, null, null, "_id DESC", null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
            format = mCursor.getString(1);
            mCursor.close();
        }
        //Log.e("INPUT TEXT", format);
        mDb.close();
        return format;
    }

    public int getDataCount() {
        int num = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String QUERY = "SELECT * FROM " + TABLE_OUTBOX;
            Cursor cursor = db.rawQuery(QUERY, null);
            num = cursor.getCount();
            cursor.close();
            db.close();
            return num;
        } catch (Exception e) {
            //Log.e("error",e+"");
        }
        return 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            try {
                db.execSQL("ALTER TABLE " + TABLE_OUTBOX + " ADD COLUMN " + COL_KETERANGAN + " TEXT DEFAULT 0");
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
            }
        } else {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_OUTBOX);
            onCreate(db);
        }
    }
}
