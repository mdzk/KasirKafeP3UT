package com.example.kasirkafep3ut.activity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kasirkafep3ut.R;
import com.example.kasirkafep3ut.helper.DbMenuHelper;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class AddEditMenuActivity extends AppCompatActivity {

    Button btnSubmit, btnCancel, btnimage;
    Bitmap databitmap;
    byte[] gambar;
    ImageView imageView;
    int SELECT_IMAGE_CODE=1;
    EditText etId, etKode, etKategori, etNama, etKeterangan, etHarga;
    DbMenuHelper SQLite = new DbMenuHelper(this);
    String id, kode, kategori, nama, keterangan, harga;

    private String selectedImagePath;
    private static DownloadManager.Query ContentResolver;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_menu);

        etId = findViewById(R.id.et_id);
        etKode = findViewById(R.id.et_kode);
        etKategori = findViewById(R.id.et_kategori);
        etNama = findViewById(R.id.et_nama);
        etKeterangan = findViewById(R.id.et_keterangan);
        etHarga = findViewById(R.id.et_harga);
        btnSubmit = findViewById(R.id.btn_submit);
        btnCancel = findViewById(R.id.btn_cancel);
        btnimage = findViewById(R.id.btnimage);
        imageView = findViewById(R.id.Imagepick);

        id = getIntent().getStringExtra(MenuActivity.TAG_ID);
        kode = getIntent().getStringExtra(MenuActivity.TAG_KODE);
        kategori = getIntent().getStringExtra(MenuActivity.TAG_KATEGORI);
        nama = getIntent().getStringExtra(MenuActivity.TAG_NAMA);
        keterangan = getIntent().getStringExtra(MenuActivity.TAG_KETERANGAN);
        harga = getIntent().getStringExtra(MenuActivity.TAG_HARGA);
        gambar = getIntent().getByteArrayExtra(MenuActivity.TAG_GAMBAR);

        if (id == null || id.equals("")){
            setTitle("Tambah Data");
        } else  {
            setTitle("Edit Data");
            etId.setText(id);
            etKode.setText(kode);
            etKategori.setText(kategori);
            etNama.setText(nama);
            etKeterangan.setText(keterangan);
            etHarga.setText(harga);
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(gambar, 0, gambar.length));
        }

        btnimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Title"), SELECT_IMAGE_CODE);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (etId.getText().toString().equals("")) {
                        save();
                    } else {
                        edit();
                    }
                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blank();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void blank() {
        etId.setText(null);
        etKode.setText(null);
        etKategori.setText(null);
        etNama.setText(null);
        etKeterangan.setText(null);
        etHarga.setText(null);
    }

    private void save() throws IOException {
        if (etKode.getText().toString().equals("") ||
                etKategori.getText().toString().equals("") ||
                etNama.getText().toString().equals("") ||
                etKeterangan.getText().toString().equals("") ||
                etHarga.getText().toString().equals("")) {
            Toast.makeText(this, "Wajib Diisi Semua", Toast.LENGTH_SHORT).show();
        } else {
            byte[] byteImage1 = null;
            FileInputStream instream = new FileInputStream(selectedImagePath);
            BufferedInputStream bif = new BufferedInputStream(instream);
            byteImage1 = new byte[bif.available()];
            bif.read(byteImage1);

            SQLite.insert(etKode.getText().toString(), etKategori.getText().toString(),etNama.getText().toString(), etKeterangan.getText().toString(), etHarga.getText().toString(), byteImage1);
            blank();
            finish();
        }
    }

    private void edit() throws IOException {
        if (etKode.getText().toString().equals("") ||
                etKategori.getText().toString().equals("") ||
                etNama.getText().toString().equals("") ||
                etKeterangan.getText().toString().equals("") ||
                etHarga.getText().toString().equals("")) {
            Toast.makeText(this, "Wajib Diisi Semua", Toast.LENGTH_SHORT).show();
        } else {
            byte[] byteImage1 = null;
            FileInputStream instream = new FileInputStream(selectedImagePath);
            BufferedInputStream bif = new BufferedInputStream(instream);
            byteImage1 = new byte[bif.available()];
            bif.read(byteImage1);

            SQLite.update(Integer.parseInt(etId.getText().toString()), etKode.getText().toString(), etKategori.getText().toString(),
                    etNama.getText().toString(), etKeterangan.getText().toString(), etHarga.getText().toString(), byteImage1);
            blank();
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1) {
            Uri selectedImageUri = data.getData();
            imageView.setImageURI(selectedImageUri);

            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImageUri,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            selectedImagePath = cursor.getString(columnIndex);
        }
    }
}