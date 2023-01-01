package com.example.kasirkafep3ut.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kasirkafep3ut.R;
import com.example.kasirkafep3ut.adapter.Adapter;
import com.example.kasirkafep3ut.adapter.AdapterLaporan;
import com.example.kasirkafep3ut.adapter.AdapterMenu;
import com.example.kasirkafep3ut.helper.DbHelper;
import com.example.kasirkafep3ut.model.DataLaporan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class LaporanActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    DbHelper SQLite = new DbHelper(this);
    EditText teTanggal;
    AlertDialog.Builder dialog;
    TextView teprodukjual, tetotaltrans, tetotaljual, tetotaluntung;

    String gettanggal;
    ListView lvData;
    List<DataLaporan> itemList = new ArrayList<>();
    AdapterLaporan adapter;

    public static final String TAG_ID = "id";
    public static final String TAG_TANGGAL = "tanggal";
    public static final String TAG_JAM = "jam";
    public static final String TAG_KODE_MENU = "menu";
    public static final String TAG_HARGA = "harga";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("LAPORAN");
        }

        gettanggal = getIntent().getStringExtra("gettanggal");


        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        teprodukjual = findViewById(R.id.lap_produk_jual);
        tetotaltrans = findViewById(R.id.lap_total_tran);
        tetotaljual = findViewById(R.id.lap_total_jual);
        tetotaluntung = findViewById(R.id.lap_total_untung);

        lvData = findViewById(R.id.lv_data_laporan);
        SQLite = new DbHelper(getApplicationContext());

        adapter = new AdapterLaporan(this, itemList);
        lvData.setAdapter(adapter);

        lvData.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String idx = itemList.get(position).getId();
                final String tanggal = itemList.get(position).getTanggal();
                final String jam = itemList.get(position).getJam();
                final String kode_menu = itemList.get(position).getKode();
                final String harga = itemList.get(position).getHarga();

                final CharSequence[] dialogItem = {"Edit", "Hapus", "Lihat Struk"};
                dialog = new AlertDialog.Builder(LaporanActivity.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent(LaporanActivity.this, AddEditActivity.class);
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
                                Intent struk = new Intent(LaporanActivity.this, ImageActivity.class);
                                struk.putExtra(TAG_ID, idx);
                                struk.putExtra(TAG_TANGGAL, tanggal);
                                struk.putExtra(TAG_JAM, jam);
                                struk.putExtra(TAG_KODE_MENU, kode_menu);
                                struk.putExtra(TAG_HARGA, harga);
                                startActivity(struk);


                                break;
                            case 3:
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });

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

    private void getAllData() {
        ArrayList<HashMap<String, String>> row = SQLite.getAllDataLaporan(gettanggal);

        Integer totalpenjualan = 0;
        for (int i = 0; i < row.size(); i++) {
            String id = row.get(i).get(TAG_ID);
            String tanggal = row.get(i).get(TAG_TANGGAL);
            String jam = row.get(i).get(TAG_JAM);
            String kode = row.get(i).get(TAG_KODE_MENU);
            Integer harga = Integer.valueOf(row.get(i).get(TAG_HARGA));

            DataLaporan data = new DataLaporan();
            data.setId(id);
            data.setTanggal(tanggal);
            data.setJam(jam);
            data.setKode(kode);
            data.setHarga(String.valueOf(harga));

            itemList.add(data);
            totalpenjualan += harga;
        }
        adapter.notifyDataSetChanged();
        tetotaljual.setText(String.valueOf(totalpenjualan));
        tetotaluntung.setText(String.valueOf(totalpenjualan));
        tetotaltrans.setText(String.valueOf(row.size()));
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
//        invoicedialog.setContentView(R.layout.activity_image);
//        invoicedialog.setCancelable(true);
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(invoicedialog.getWindow().getAttributes());
//    }

//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        itemList.clear();
//        getAllData();
//    }

}