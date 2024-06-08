package com.example.ngudirasa.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ngudirasa.R;

import java.util.List;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder> {

    private List<Integer> imageList;
    private List<String> tanggalList, namaProdukList, hargaProdukList, qtyList;
    public RiwayatAdapter(List<String> tanggalList, List<Integer> imageList, List<String> namaProdukList, List<String> hargaProdukList, List<String> qtyList) {
        this.tanggalList = tanggalList;
        this.imageList = imageList;
        this.namaProdukList = namaProdukList;
        this.hargaProdukList = hargaProdukList;
        this.qtyList = qtyList;
    }

    @NonNull
    @Override
    public RiwayatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new RiwayatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatViewHolder holder, int position) {
        holder.tanggal.setText(tanggalList.get(position));
        holder.mImageview.setImageResource(imageList.get(position));
        holder.nama_produk.setText(namaProdukList.get(position));
        holder.harga_produk.setText(hargaProdukList.get(position));
        holder.qty.setText(qtyList.get(position));
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class RiwayatViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageview;
        private TextView tanggal, nama_produk, harga_produk, qty;

        public RiwayatViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageview = itemView.findViewById(R.id.imageView_riwayat);
            tanggal = itemView.findViewById(R.id.tanggal_riwayat);
            nama_produk = itemView.findViewById(R.id.nama_produk_riwayat);
            harga_produk = itemView.findViewById(R.id.harga_riwayat);
            qty = itemView.findViewById(R.id.qty);
        }
    }
}

