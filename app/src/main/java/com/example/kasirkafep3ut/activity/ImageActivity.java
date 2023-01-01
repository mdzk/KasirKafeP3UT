package com.example.kasirkafep3ut.activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kasirkafep3ut.R;
import com.example.kasirkafep3ut.adapter.PdfDocumentAdapter;
import com.example.kasirkafep3ut.helper.DbHelper;
import com.example.kasirkafep3ut.helper.DbMenuHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class ImageActivity extends AppCompatActivity {

    String kasir, id, tanggal, jam, kodemenu, harga, menu;
    TextView tvId, tvKasir, tvTanggal, tvJam, tvMenu, tvHarga, tvTotalHarga;
    DbMenuHelper SQLite = new DbMenuHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);


        tvId = findViewById(R.id.invoice_id);
        tvKasir = findViewById(R.id.invoice_kasir);
        tvTanggal = findViewById(R.id.invoice_date);
        tvJam = findViewById(R.id.invoice_time);
        tvMenu = findViewById(R.id.smenu);
        tvHarga = findViewById(R.id.sharga);
        tvTotalHarga = findViewById(R.id.stotalharga);

        id = getIntent().getStringExtra("id");
        tanggal = getIntent().getStringExtra("tanggal");
        jam = getIntent().getStringExtra("jam");
        kodemenu = getIntent().getStringExtra("menu");
        harga = getIntent().getStringExtra("harga");

        tvId.setText("ID"+id);
        tvKasir.setText("Kasir: Admin");
        tvTanggal.setText(tanggal);
        tvJam.setText(jam);
        tvMenu.setText(SQLite.getMenuData(kodemenu));
        tvHarga.setText(harga);
        tvTotalHarga.setText(harga);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.invoice_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.printInvoice:
                createPdfFromView(findViewById(R.id.invoice_layout), "invoice", 720, 1080, 1);
                return(true);
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void createPdfFromView(View view, String fileName, int pageWidth, int pageHeight, int pageNumber) {

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(path, fileName.concat(".pdf"));

        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (file.exists()) {
            PdfDocument document = new PdfDocument();
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create();
            PdfDocument.Page page = document.startPage(pageInfo);

            view.draw(page.getCanvas());

            document.finishPage(page);

            try {
                Toast.makeText(this, "Saving...", Toast.LENGTH_SHORT).show();
                document.writeTo(fOut);
            } catch (IOException e) {
                Toast.makeText(this, "Failed...", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            document.close();
            printPDF(fileName);
        } else {
            //..
        }
    }

    private void printPDF(String fileName){
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(path, fileName.concat(".pdf"));
        try {
            PrintDocumentAdapter printDocumentAdapter = new PdfDocumentAdapter(ImageActivity.this, file.getPath());
            printManager.print("Document",printDocumentAdapter,new PrintAttributes.Builder().build());
        }catch (Exception ex){
            Log.e("Harshita",""+ex.getMessage());
            Toast.makeText(ImageActivity.this, "Can't read pdf file", Toast.LENGTH_SHORT).show();

        }
    }
}