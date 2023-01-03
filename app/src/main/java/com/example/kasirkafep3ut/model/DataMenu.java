package com.example.kasirkafep3ut.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Base64;
import android.util.Log;

public class DataMenu {

    Object id;
    Object kode;
    Object kategori;
    Object nama;
    Object keterangan;
    Object harga;
    Object gambar;

    public DataMenu() {
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getKode() {
        return kode;
    }

    public void setKode(Object kode) {
        this.kode = kode;
    }

    public Object getKategori() {
        return kategori;
    }

    public void setKategori(Object kategori) {
        this.kategori = kategori;
    }

    public Object getNama() {
        return nama;
    }

    public void setNama(Object nama) {
        this.nama = nama;
    }

    public Object getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(Object keterangan) {
        this.keterangan = keterangan;
    }

    public Object getHarga() {
        return harga;
    }

    public void setHarga(Object harga) {
        this.harga = harga;
    }

    public Object getGambar() {
        return gambar;
    }

    public void setGambar(Object gambar) {
        this.gambar = gambar;
    }
}
