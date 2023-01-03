package com.example.kasirkafep3ut.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DbMenuHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "menu.db";
    public static final String TABLE_MENU = "menu";

    public static final String COL_ID = "id";
    public static final String COL_KODE = "kode";
    public static final String COL_KATEGORI = "kategori";
    public static final String COL_NAMA = "nama";
    public static final String COL_KETERANGAN = "keterangan";
    public static final String COL_HARGA = "harga";
    public static final String COL_GAMBAR = "gambar";

    public DbMenuHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_DATA_TABLE = "CREATE TABLE " + TABLE_MENU + " (" +
                COL_ID + " INTEGER PRIMARY KEY autoincrement, " +
                COL_KODE + " TEXT NOT NULL, " +
                COL_KATEGORI + " TEXT NOT NULL, " +
                COL_NAMA + " TEXT NOT NULL, " +
                COL_KETERANGAN + " TEXT NOT NULL, " +
                COL_HARGA + " TEXT NOT NULL," +
                COL_GAMBAR + " BLOB NOT NULL" +
                " )";
        db.execSQL(SQL_CREATE_DATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU);
        onCreate(db);
    }

    public ArrayList<HashMap<Object, Object>> getAllData() {
        ArrayList<HashMap<Object, Object>> wordList;
        wordList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MENU;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<Object, Object> map = new HashMap<>();
                map.put(COL_ID, cursor.getString(0));
                map.put(COL_KODE, cursor.getString(1));
                map.put(COL_KATEGORI, cursor.getString(2));
                map.put(COL_NAMA, cursor.getString(3));
                map.put(COL_KETERANGAN, cursor.getString(4));
                map.put(COL_HARGA, cursor.getString(5));
                map.put(COL_GAMBAR, cursor.getBlob(6));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        Log.e("Select SQLite", "" + wordList);
        database.close();
        return wordList;
    }

    @SuppressLint("Range")
    public String getMenuData(String kode) {
        String selectQuery = "SELECT nama FROM " + TABLE_MENU + " WHERE kode = '"+ kode +"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            String str;
            str = cursor.getString(cursor.getColumnIndex("nama"));
            return str;
        }
        return null;
    }

    @SuppressLint("Range")
    public Object getSampleBlob() {
        String selectQuery = "SELECT gambar FROM " + TABLE_MENU + " WHERE nama = 'a'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            Object str;
            str = cursor.getBlob(cursor.getColumnIndex("gambar"));
            return str;
        }
        return null;
    }

    public void insert(String kode, String kategori, String nama, String keterangan, String harga, byte[] gambar) {
        SQLiteDatabase database = this.getWritableDatabase();
        String queryValue = "INSERT INTO " + TABLE_MENU + " (kode, kategori, nama, keterangan, harga, gambar) " +
                "VALUES ('" + kode + "', '" + kategori + "', '" + nama + "', '" + keterangan + "', '" + harga + "', '" + gambar + "')";
        Log.e("Insert SQLite ", "" + queryValue);
        database.execSQL(queryValue);
        database.close();
    }

    public void update(int id, String kode, String kategori, String nama, String keterangan, String harga, byte[] gambar) {
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "UPDATE " + TABLE_MENU + " SET "
                + COL_KODE + "='" + kode + "', "
                + COL_KATEGORI + "='" + kategori + "',"
                + COL_NAMA + "='" + nama + "',"
                + COL_KETERANGAN + "='" + keterangan + "',"
                + COL_GAMBAR + "='" + gambar + "',"
                + COL_HARGA + "='" + harga + "'"
                + " WHERE " + COL_ID + "=" + "'" + id + "'";
        Log.e("Update SQLite ", updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }

    public void delete(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_MENU + " WHERE " + COL_ID + "=" + "'" + id + "'";
        Log.e("Delete SQLite ", deleteQuery);
        database.execSQL(deleteQuery);
        database.close();
    }

}
