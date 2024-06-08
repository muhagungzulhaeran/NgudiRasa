package com.example.ngudirasa.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ngudirasa.R;

import java.util.List;

public class PopulerAdapter extends RecyclerView.Adapter<PopulerAdapter.PopulerViewHolder> {

    private List<Integer> imageList;
    private List<String> namaProdukList, hargaProdukList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onPopulerItemClick(int position);
    }


    public PopulerAdapter(List<Integer> imageList, List<String> namaProdukList, List<String> hargaProdukList, OnItemClickListener listener){
        this.imageList = imageList ;
        this.namaProdukList = namaProdukList;
        this.hargaProdukList = hargaProdukList;
        this.mListener = listener;
    }
    @NonNull
    @Override
    public PopulerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.populer_single_item, parent, false);
        return new PopulerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopulerViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.mImageView.setImageResource(imageList.get(position));
        holder.nama_produk.setText(namaProdukList.get(position));
        holder.harga_produk.setText(hargaProdukList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onPopulerItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class PopulerViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageView;
        private TextView nama_produk, harga_produk;
        public PopulerViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.imageView_populer);
            nama_produk = itemView.findViewById(R.id.nama_produk_populer);
            harga_produk = itemView.findViewById(R.id.harga_populer);
        }
    }
}
