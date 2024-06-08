package com.example.ngudirasa.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.util.List;

public class SavetoDb {
    public static void saveProdukToDb(Context context, List<DataProduk> produkList) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Save Produk data
        for (DataProduk produk : produkList) {
            ContentValues values = new ContentValues();
            values.put(DbHelper.COLUMN_ID_PRODUK, produk.getIdProduk());
            values.put(DbHelper.COLUMN_NAMA_PRODUK, produk.getNamaProduk());
            values.put(DbHelper.COLUMN_HARGA_PREMIUM, produk.getHargaPremium());
            values.put(DbHelper.COLUMN_HARGA_EKONOMIS, produk.getHargaEkonomis());
            values.put(DbHelper.COLUMN_KATEGORI, produk.getKategori());
            values.put(DbHelper.COLUMN_DESKRIPSI, produk.getDeskripsi());
            db.insert(DbHelper.TABLE_NAME_PRODUK, null, values);
        }

        // Close the database
        db.close();
    }

    public static void saveUsertoDb(Context context, List<DataUser> userlist) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Save Produk data
        for (DataUser users : userlist) {
            ContentValues values = new ContentValues();
            values.put(DbHelper.COLUMN_ID_USER, users.getIdUser());
            values.put(DbHelper.COLUMN_EMAIL, users.getEmail());
            values.put(DbHelper.COLUMN_NAMA_USER, users.getNamaUser());
            values.put(DbHelper.COLUMN_PASSWORD, users.getPassword());
            values.put(DbHelper.COLUMN_VALID, users.isValid());
            db.insert(DbHelper.TABLE_NAME_USER, null, values);
        }

        // Close the database
        db.close();
    }

    public static void saveHistoryToDb(Context context, List<DataHistory> historyList) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Save History data
        for (DataHistory history : historyList) {
            ContentValues values = new ContentValues();
            values.put(DbHelper.COLUMN_NOMOR, history.getNomor());
            values.put(DbHelper.COLUMN_TIPE_HARGA, history.getTipeHarga());
            values.put(DbHelper.COLUMN_ID_PRODUK, history.getIdProduk());
            values.put(DbHelper.COLUMN_KUANTITAS, history.getKuantitas());
            values.put(DbHelper.COLUMN_TANGGAL, history.getTanggal());
            values.put(DbHelper.COLUMN_NAMA_PRODUK, history.getNamaProduk());
            values.put(DbHelper.COLUMN_TOTAL, history.getTotal());
            db.insert(DbHelper.TABLE_NAME_HISTORY, null, values);
        }

        // Close the database
        db.close();
    }

    public static void resetUserData(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        try {
            database.beginTransaction();
            // Clear all rows from the user table
            int rowsDeleted = database.delete(dbHelper.TABLE_NAME_USER, null, null);
            database.setTransactionSuccessful();

            Log.d("ResetUserData", "Deleted " + rowsDeleted + " rows from the user table");


        } finally {
            database.endTransaction();
        }
    }

    public static void resetHistoryData(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        try {
            database.beginTransaction();
            // Clear all rows from the user table
            int rowsDeleted = database.delete(dbHelper.TABLE_NAME_HISTORY, null, null);
            database.setTransactionSuccessful();

            Log.d("ResetHistory", "Deleted " + rowsDeleted + " rows from the history table");


        } finally {
            database.endTransaction();
        }
    }

    public static void resetProdukData(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        try {
            database.beginTransaction();
            // Clear all rows from the user table
            int rowsDeleted = database.delete(dbHelper.TABLE_NAME_PRODUK, null, null);
            database.setTransactionSuccessful();

            Log.d("ResetHistory", "Deleted " + rowsDeleted + " rows from the history table");


        } finally {
            database.endTransaction();
        }
    }


}
