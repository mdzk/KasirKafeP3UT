package com.example.kasirkafep3ut.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "pesanan.db";
    public static final String TABLE_SQLite = "sqlite";

    public static final String COL_ID = "id";
    public static final String COL_TANGGAL = "tanggal";
    public static final String COL_JAM = "jam";
    public static final String COL_KODE_MENU = "menu";
    public static final String COL_HARGA = "harga";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_DATA_TABLE = "CREATE TABLE " + TABLE_SQLite + " (" +
                COL_ID + " INTEGER PRIMARY KEY autoincrement, " +
                COL_TANGGAL + " TEXT NOT NULL, " +
                COL_JAM + " TEXT NOT NULL, " +
                COL_KODE_MENU + " TEXT NOT NULL, " +
                COL_HARGA + " TEXT NOT NULL" +
                " )";
        db.execSQL(SQL_CREATE_DATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SQLite);
        onCreate(db);
    }

    public ArrayList<HashMap<String, String>> getAllDataLaporan(final String gettanggal) {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SQLite + " WHERE tanggal = '"+ gettanggal +"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put(COL_ID, cursor.getString(0));
                map.put(COL_TANGGAL, cursor.getString(1));
                map.put(COL_JAM, cursor.getString(2));
                map.put(COL_KODE_MENU, cursor.getString(3));
                map.put(COL_HARGA, cursor.getString(4));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        Log.e("Select SQLite", "" + wordList);
        database.close();
        return wordList;
    }

    public ArrayList<HashMap<String, String>> getAllData() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SQLite;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put(COL_ID, cursor.getString(0));
                map.put(COL_TANGGAL, cursor.getString(1));
                map.put(COL_JAM, cursor.getString(2));
                map.put(COL_KODE_MENU, cursor.getString(3));
                map.put(COL_HARGA, cursor.getString(4));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        Log.e("Select SQLite", "" + wordList);
        database.close();
        return wordList;
    }

    public void insert(String tanggal, String jam, String menu, String harga) {
        SQLiteDatabase database = this.getWritableDatabase();
        String queryValue = "INSERT INTO " + TABLE_SQLite + " (tanggal, jam, menu, harga) " +
                "VALUES ('" + tanggal + "', '" + jam + "', '" + menu + "', '" + harga + "')";
        Log.e("Insert SQLite ", "" + queryValue);
        database.execSQL(queryValue);
        database.close();
    }

    public void update(int id, String tanggal, String jam, String menu, String harga) {
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "UPDATE " + TABLE_SQLite + " SET "
                + COL_TANGGAL + "='" + tanggal + "', "
                + COL_JAM + "='" + jam + "',"
                + COL_KODE_MENU + "='" + menu + "',"
                + COL_HARGA + "='" + harga + "'"
                + " WHERE " + COL_ID + "=" + "'" + id + "'";
        Log.e("Update SQLite ", updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }

    public void delete(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_SQLite + " WHERE " + COL_ID + "=" + "'" + id + "'";
        Log.e("Delete SQLite ", deleteQuery);
        database.execSQL(deleteQuery);
        database.close();
    }

//    Laporan Section

}
