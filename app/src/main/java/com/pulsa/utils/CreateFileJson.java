package com.pulsa.utils;

import android.content.Context;
import android.net.ParseException;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateFileJson {
    public static void saveData(Context context, String mJsonResponse, String fileName) {
        try {

            FileWriter file = new FileWriter(context.getFilesDir().getPath() + "/" + fileName + ".txt", false);
            file.write(mJsonResponse);
            file.flush();
            file.close();

            Log.e("cretate", context.getFilesDir().getPath() + "/" + fileName + ".txt");
        } catch (IOException e) {
            Log.e("TAG", "Writing file Error :" + e.getMessage());
        }
    }

    public static String getData(Context context, String fileName) {
        try {
            File f = new File(context.getFilesDir().getPath() + "/" + fileName + ".txt");
            //check whether file exists
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);
        } catch (IOException e) {
            //Log.e("TAG", "Error in Reading: " + e.getLocalizedMessage());
            return null;
        }
    }

    public static String getDataCreate(Context context, String fileName) {
        try {
            File file = new File(context.getFilesDir().getPath() + "/" + fileName + ".txt");
            //check whether file exists
            Date lastModDate = new Date(file.lastModified());
            try {
                Date d = lastModDate;
                DateFormat dates = new SimpleDateFormat("yyyyMMdd");
                return new String(dates.format(d));
            } catch (ParseException e) {
                return null;
            } catch (Exception e) {
                return null;
            }

        } catch (Exception e) {
            // Log.e("TAG", "Error in Reading: " + e.getLocalizedMessage());
            return null;
        }
    }

    public static boolean deleteFile(Context context, String fileName) {
        try {
            File file = new File(context.getFilesDir().getPath() + "/" + fileName + ".txt");
            //check whether file exists
            boolean d0 = file.delete();
            Log.e("Delete Check", fileName + " | " + d0);
            return d0;

        } catch (Exception e) {
            Log.e("TAG", "Error Delete: " + e.getMessage());
            return false;
        }
    }

    public static String getJsonAsset(Context context, String nama) {
        Log.e("nama", nama);
        String json = null;
        try {
            InputStream is = context.getAssets().open(nama);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception e) {
            return null;
        }

        return json;
    }
}
