package com.example.kasirkafep3ut.adapter;

import static androidx.core.graphics.drawable.DrawableKt.toBitmap;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.example.kasirkafep3ut.R;
import com.example.kasirkafep3ut.model.DataMenu;

import java.util.HashMap;
import java.util.List;

public class AdapterMenu extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<DataMenu> items;

    private Context context;
    public AdapterMenu(Activity activity, List<DataMenu> items, Context context) {
        this.activity = activity;
        this.items = items;
        this.context = context;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null) {
            view = inflater.inflate(R.layout.list_menu, null);
        }

        TextView id = view.findViewById(R.id.tv_id_menu);
        TextView kode = view.findViewById(R.id.tv_kode);
        TextView kategori = view.findViewById(R.id.tv_kategori);
        TextView nama = view.findViewById(R.id.tv_nama);
        TextView keterangan = view.findViewById(R.id.tv_ket);
        TextView harga = view.findViewById(R.id.tv_harga);
        ImageView gambar = view.findViewById(R.id.imageViewMenu);

        DataMenu data = items.get(i);

        id.setText((String) data.getId());
        kode.setText((String) data.getKode());
        kategori.setText((String) data.getKategori());
        nama.setText((String) data.getNama());
        keterangan.setText((String) data.getKeterangan());
        harga.setText((String) data.getHarga());

//        try {

            byte[] datagambar = (byte[]) data.getGambar();
            Bitmap bitmap = BitmapFactory.decodeByteArray(datagambar, 0 ,datagambar.length);
//            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.blackbuck);

            gambar.setImageBitmap(bitmap);
//            gambar.setImageResource("a");
//        } catch (Exception e) {
//            Log.e("e", String.valueOf(e));
//        }


        return view;
    }

}
