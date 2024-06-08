package com.example.ngudirasa.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ngudirasa.Model.DataHistory;
import com.example.ngudirasa.R;

import java.util.List;
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<DataHistory> historyDataList;

    public HistoryAdapter(List<DataHistory> historyDataList) {
        this.historyDataList = historyDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate your history_item.xml layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data to views in history_item.xml
        DataHistory historyData = historyDataList.get(position);
        holder.txtProductName.setText(historyData.getNamaProduk());
        holder.textViewTanggal.setText(historyData.getTanggal());
        holder.txtharga.setText(historyData.getTotal());
        holder.qty.setText(historyData.getKuantitas());

        // Add more bindings as needed
    }

    @Override
    public int getItemCount() {
        return historyDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Declare your TextViews and other views in history_item.xml
        TextView txtProductName, txtharga, textViewTanggal, qty;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            // Initialize your TextViews and other views
//            textViewTanggal = itemView.findViewById(R.id.textViewTanggal);
//            txtProductName = itemView.findViewById(R.id.txtProductName);
//            txtharga = itemView.findViewById(R.id.txtharga);
//            imageView = itemView.findViewById(R.id.imageView);
//            qty = itemView.findViewById(R.id.qty);

            // Initialize more TextViews as needed
        }
    }
}

