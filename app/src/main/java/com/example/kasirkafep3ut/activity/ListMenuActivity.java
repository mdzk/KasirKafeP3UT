package com.example.kasirkafep3ut.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kasirkafep3ut.R;
import com.example.kasirkafep3ut.adapter.AdapterMenu;
import com.example.kasirkafep3ut.helper.DbMenuHelper;
import com.example.kasirkafep3ut.model.DataMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListMenuActivity extends AppCompatActivity {

    ListView lvPilihMenu;
    List<DataMenu> itemList = new ArrayList<>();
    AdapterMenu adapter;
    DbMenuHelper SQLite = new DbMenuHelper(this);

    public static final String TAG_ID = "id";
    public static final String TAG_KODE = "kode";
    public static final String TAG_KATEGORI = "kategori";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_KETERANGAN = "keterangan";
    public static final String TAG_HARGA = "harga";
    public static final String TAG_GAMBAR = "gambar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_menu);

        lvPilihMenu = findViewById(R.id.lv_pilih_menu);
        SQLite = new DbMenuHelper(getApplicationContext());

        adapter = new AdapterMenu(this, itemList, this);
        lvPilihMenu.setAdapter(adapter);

        lvPilihMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Object kode = itemList.get(i).getKode();
                final Object harga = itemList.get(i).getHarga();
                Intent intent = new Intent();
                intent.putExtra("selectedKodeMenu", (String) kode);
                intent.putExtra("selectedHargaMenu", (String) harga);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        getAllData();
    }

    private void getAllData() {
        ArrayList<HashMap<Object, Object>> row = SQLite.getAllData();
        for (int i = 0; i < row.size(); i++) {
            String id = (String) row.get(i).get(TAG_ID);
            String kode = (String) row.get(i).get(TAG_KODE);
            String kategori = (String) row.get(i).get(TAG_KATEGORI);
            String nama = (String) row.get(i).get(TAG_NAMA);
            String keterangan = (String) row.get(i).get(TAG_KETERANGAN);
            String harga = (String) row.get(i).get(TAG_HARGA);
            byte[] gambar = (byte[]) row.get(i).get(TAG_GAMBAR);

            DataMenu data = new DataMenu();
            data.setId(id);
            data.setKode(kode);
            data.setKategori(kategori);
            data.setNama(nama);
            data.setKeterangan(keterangan);
            data.setHarga(harga);
            data.setGambar(gambar);

            itemList.add(data);

        }
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume () {
        super.onResume();
        itemList.clear();
        getAllData();
    }
}