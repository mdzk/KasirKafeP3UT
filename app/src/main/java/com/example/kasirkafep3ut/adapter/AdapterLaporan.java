package com.example.kasirkafep3ut.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kasirkafep3ut.R;
import com.example.kasirkafep3ut.model.DataLaporan;
import com.example.kasirkafep3ut.model.DataPesanan;

import java.util.List;

public class AdapterLaporan extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<DataLaporan> items;

    public AdapterLaporan(Activity activity, List<DataLaporan> items) {
        this.activity = activity;
        this.items = items;
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
            view = inflater.inflate(R.layout.list_pesanan, null);
        }

        TextView id = view.findViewById(R.id.tv_id);
        TextView tanggal = view.findViewById(R.id.tv_tanggal);
        TextView jam = view.findViewById(R.id.tv_jam);
        TextView kodeMenu = view.findViewById(R.id.tv_kode_menu);
        TextView harga = view.findViewById(R.id.tv_harga);

        DataLaporan data = items.get(i);

        id.setText(data.getId());
        tanggal.setText(data.getTanggal());
        jam.setText(data.getJam());
        kodeMenu.setText(data.getKode());
        harga.setText(data.getHarga());

        return view;
    }
}
