package com.example.kasirkafep3ut.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kasirkafep3ut.R;
import com.example.kasirkafep3ut.adapter.AdapterMenu;
import com.example.kasirkafep3ut.helper.DbMenuHelper;
import com.example.kasirkafep3ut.model.DataMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    ListView lvDataMenu;
    FloatingActionButton fabAddMenu;
    AlertDialog.Builder dialog;
    List<DataMenu> itemList = new ArrayList<>();
    AdapterMenu adapter;
    DbMenuHelper SQLite = new DbMenuHelper(this);

    public static final String TAG_ID = "id";
    public static final String TAG_KODE = "kode";
    public static final String TAG_KATEGORI= "kategori";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_KETERANGAN = "keterangan";
    public static final String TAG_HARGA = "harga";
    public static final String TAG_GAMBAR = "gambar";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("DAFTAR MENU");
        }

        lvDataMenu = findViewById(R.id.lv_data_menu);
        fabAddMenu = findViewById(R.id.fab_add_menu);
        SQLite = new DbMenuHelper(getApplicationContext());

        adapter = new AdapterMenu(this, itemList, this);
        lvDataMenu.setAdapter(adapter);

        fabAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, AddEditMenuActivity.class);
                startActivity(intent);
            }
        });

        lvDataMenu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Object idx = itemList.get(position).getId();
                final Object kode = itemList.get(position).getKode();
                final Object kategori = itemList.get(position).getKategori();
                final Object nama = itemList.get(position).getNama();
                final Object keterangan = itemList.get(position).getKeterangan();
                final Object harga = itemList.get(position).getHarga();

                final CharSequence[] dialogItem = {"Edit", "Hapus"};
                dialog = new AlertDialog.Builder(MenuActivity.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent(MenuActivity.this, AddEditMenuActivity.class);
                                intent.putExtra(TAG_ID, (String) idx);
                                intent.putExtra(TAG_KODE, (String) kode);
                                intent.putExtra(TAG_KATEGORI, (String) kategori);
                                intent.putExtra(TAG_NAMA, (String) nama);
                                intent.putExtra(TAG_KETERANGAN, (String) keterangan);
                                intent.putExtra(TAG_HARGA, (String) harga);
                                startActivity(intent);
                                break;
                            case 1:
                                SQLite.delete(Integer.parseInt(String.valueOf(idx)));
                                itemList.clear();
                                getAllData();
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });
        getAllData();

    }

    private void getAllData() {
        ArrayList<HashMap<Object, Object>> row = SQLite.getAllData();

        for (int i = 0; i < row.size(); i++) {
            Object id = row.get(i).get(TAG_ID);
            Object kode = row.get(i).get(TAG_KODE);
            Object kategori = row.get(i).get(TAG_KATEGORI);
            Object nama = row.get(i).get(TAG_NAMA);
            Object ket = row.get(i).get(TAG_KETERANGAN);
            Object harga = row.get(i).get(TAG_HARGA);
            Object gambar = row.get(i).get(TAG_GAMBAR);

            DataMenu data = new DataMenu();
            data.setId(id);
            data.setKode(kode);
            data.setKategori(kategori);
            data.setNama(nama);
            data.setKeterangan(ket);
            data.setHarga(harga);
            data.setGambar(gambar);

            itemList.add(data);

        }
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();
        itemList.clear();
        getAllData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}