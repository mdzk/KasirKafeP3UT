package com.example.kasirkafep3ut.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import com.example.kasirkafep3ut.R;

public class ShowImageActivity extends AppCompatActivity {

    ImageView ShowImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Object byteArray = bundle.get("GET_BYTE");

        byte[] datagambar = (byte[]) byteArray;
        ShowImage = findViewById(R.id.iv_showimage);

        if(byteArray == null) {
            ShowImage.setImageResource(R.drawable.bg_semua);
        } else {
//            byte[] decodedImageBytes = Base64.decode(byteArray, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(datagambar, 0 ,datagambar.length);

            ShowImage.setImageBitmap(bitmap);
        }
    }
}