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

public class AllAdapter extends RecyclerView.Adapter<AllAdapter.AllViewHolder> {

    private List<Integer> imageList;
    private List<String> namaProdukList, hargaProdukList;

    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onAllItemClick(int position);
    }
    public AllAdapter(List<Integer> imageList, List<String> namaProdukList, List<String> hargaProdukList, AllAdapter.OnItemClickListener listener){
        this.imageList = imageList;
        this.namaProdukList = namaProdukList;
        this.hargaProdukList = hargaProdukList;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public AllViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_single_item, parent, false);
        return new AllViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllViewHolder holder, int position) {
        holder.mImageview.setImageResource(imageList.get(position));
        holder.nama_produk.setText(namaProdukList.get(position));
        holder.harga_produk.setText(hargaProdukList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onAllItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class AllViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageview;
        private TextView nama_produk, harga_produk;

        public AllViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageview = itemView.findViewById(R.id.imageView_all);
            nama_produk = itemView.findViewById(R.id.nama_produk_all);
            harga_produk = itemView.findViewById(R.id.harga_all);
        }
    }
}
