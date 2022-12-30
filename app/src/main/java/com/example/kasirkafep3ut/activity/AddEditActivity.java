package com.example.kasirkafep3ut.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.kasirkafep3ut.R;
import com.example.kasirkafep3ut.helper.DbHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEditActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TimePickerDialog timePickerDialog;
    Button btnSubmit, btnCancel;
    EditText etId, etTanggal, etJam, etKodeMenu, etHarga;
    DbHelper SQLite = new DbHelper(this);
    String id, tanggal, jam, kode, harga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        etId = findViewById(R.id.et_id);
        etTanggal = findViewById(R.id.et_tanggal);
        etJam = findViewById(R.id.et_jam);
        etKodeMenu = findViewById(R.id.et_kode);
        etHarga = findViewById(R.id.et_harga);
        btnSubmit = findViewById(R.id.btn_submit);
        btnCancel = findViewById(R.id.btn_cancel);

        id = getIntent().getStringExtra(PesananActivity.TAG_ID);
        tanggal = getIntent().getStringExtra(PesananActivity.TAG_TANGGAL);
        jam = getIntent().getStringExtra(PesananActivity.TAG_JAM);
        kode = getIntent().getStringExtra(PesananActivity.TAG_KODE_MENU);
        harga = getIntent().getStringExtra(PesananActivity.TAG_HARGA);

        if (id == null || id.equals("")){
            setTitle("Tambah Data");
        } else {
            setTitle("Edit Data");
            etId.setText(id);
            etTanggal.setText(tanggal);
            etJam.setText(jam);
            etKodeMenu.setText(kode);
            etHarga.setText(harga);
        }

        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        etJam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });

        etKodeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEditActivity.this, ListMenuActivity.class);
                startActivityForResult(intent, 1);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                etKodeMenu.setText(data.getStringExtra("selectedKodeMenu"));
                etHarga.setText(data.getStringExtra("selectedHargaMenu"));
            }
        }
    }

    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etTanggal.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showTimePicker(){
        dateFormatter = new SimpleDateFormat("HH:mm", Locale.US);
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
               etJam.setText(String.format("%02d:%02d", hourOfDay, minute));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void blank() {
        etId.setText(null);
        etTanggal.setText(null);
        etJam.setText(null);
        etKodeMenu.setText(null);
        etHarga.setText(null);
    }

    private void save() {
        if (etTanggal.getText().toString().equals("") ||
                etJam.getText().toString().equals("") ||
                etKodeMenu.getText().toString().equals("") ||
                etHarga.getText().toString().equals("")) {
            Toast.makeText(this, "Wajib Diisi Semua", Toast.LENGTH_SHORT).show();
        } else {
            SQLite.insert(etTanggal.getText().toString(), etJam.getText().toString(),
                    etKodeMenu.getText().toString(), etHarga.getText().toString());
            blank();
            finish();
        }
    }

    private void edit() {
        if (etTanggal.getText().toString().equals("") ||
                etJam.getText().toString().equals("") ||
                etKodeMenu.getText().toString().equals("") ||
                etHarga.getText().toString().equals("")) {
            Toast.makeText(this, "Wajib Diisi Semua", Toast.LENGTH_SHORT).show();
        } else {
            SQLite.update(Integer.parseInt(etId.getText().toString()), etTanggal.getText().toString(), etJam.getText().toString(),
                    etKodeMenu.getText().toString(), etHarga.getText().toString());
            blank();
            finish();
        }
    }

}