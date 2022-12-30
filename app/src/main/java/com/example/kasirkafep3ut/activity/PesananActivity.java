package com.example.kasirkafep3ut.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kasirkafep3ut.R;
import com.example.kasirkafep3ut.adapter.Adapter;
import com.example.kasirkafep3ut.helper.DbHelper;
import com.example.kasirkafep3ut.model.DataPesanan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PesananActivity extends AppCompatActivity {

    ListView lvData;
    FloatingActionButton fabAdd;
    AlertDialog.Builder dialog;
    List<DataPesanan> itemList = new ArrayList<>();
    Adapter adapter;
    DbHelper SQLite = new DbHelper(this);
//    private PdfDocument document;
//    private static final int CREATE_FILE = 1;

    public static final String TAG_ID = "id";
    public static final String TAG_TANGGAL = "tanggal";
    public static final String TAG_JAM = "jam";
    public static final String TAG_KODE_MENU = "menu";
    public static final String TAG_HARGA = "harga";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("TRANSAKSI");
        }

        lvData = findViewById(R.id.lv_data);
        fabAdd = findViewById(R.id.fab_add);
        SQLite = new DbHelper(getApplicationContext());

        adapter = new Adapter(this, itemList);
        lvData.setAdapter(adapter);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PesananActivity.this, AddEditActivity.class);
                startActivity(intent);
            }
        });

        lvData.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String idx = itemList.get(position).getId();
                final String tanggal = itemList.get(position).getTanggal();
                final String jam = itemList.get(position).getJam();
                final String kode_menu = itemList.get(position).getKode();
                final String harga = itemList.get(position).getHarga();

                final CharSequence[] dialogItem = {"Edit", "Hapus"};
                dialog = new AlertDialog.Builder(PesananActivity.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent(PesananActivity.this, AddEditActivity.class);
                                intent.putExtra(TAG_ID, idx);
                                intent.putExtra(TAG_TANGGAL, tanggal);
                                intent.putExtra(TAG_JAM, jam);
                                intent.putExtra(TAG_KODE_MENU, kode_menu);
                                intent.putExtra(TAG_HARGA, harga);
                                startActivity(intent);
                                break;
                            case 1:
                                SQLite.delete(Integer.parseInt(idx));
                                itemList.clear();
                                getAllData();
                                break;
                            case 2:
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
        ArrayList<HashMap<String, String>> row = SQLite.getAllData();

        for (int i = 0; i < row.size(); i++) {
            String id = row.get(i).get(TAG_ID);
            String tanggal = row.get(i).get(TAG_TANGGAL);
            String jam = row.get(i).get(TAG_JAM);
            String kode = row.get(i).get(TAG_KODE_MENU);
            String harga = row.get(i).get(TAG_HARGA);

            DataPesanan data = new DataPesanan();
            data.setId(id);
            data.setTanggal(tanggal);
            data.setJam(jam);
            data.setKode(kode);
            data.setHarga(harga);

            itemList.add(data);

        }
        adapter.notifyDataSetChanged();

    }

//    private void createFile() {
//        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("application/pdf");
//        intent.putExtra(Intent.EXTRA_TITLE, "invoice.pdf");
//        startActivityForResult(intent, CREATE_FILE);
//    }
//
//    public void createPdfFromView(View view) {
//        final Dialog invoicedialog = new Dialog(this);
//        invoicedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        invoicedialog.setContentView(R.layout.invoice_layout);
//        invoicedialog.setCancelable(true);
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(invoicedialog.getWindow().getAttributes());
//    }

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