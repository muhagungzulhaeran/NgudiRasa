package com.example.ngudirasa;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ngudirasa.Adapter.RiwayatAdapter;
import com.example.ngudirasa.Model.ApiHandler;
import com.example.ngudirasa.Model.DataHistory;
import com.example.ngudirasa.Model.DbHelper;
import com.example.ngudirasa.Model.SavetoDb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RpFragment extends Fragment {

    private RecyclerView recyclerViewRiwayat;

    private List<DataHistory> dataList;

    private SwipeRefreshLayout swipeRefreshLayout;

    public RpFragment() {
        // Required empty public constructor
    }

    public static RpFragment newInstance() {
        return new RpFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rp, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // Handle the swipe-to-refresh action
            fetchDataAndPopulate();
        });

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Fetch data from SQLite
        dataList = fetchDataFromDb();

        // If data is not available in SQLite, fetch from the internet and save to SQLite
        if (dataList.isEmpty()) {
            ApiHandler.fetchHistoryData(requireContext(), new ApiHandler.VolleyCallbackHistory() {
                @Override
                public void onSuccess(List<DataHistory> retrievedDataList) {
                    // Save data to SQLite

                    SavetoDb.resetHistoryData(requireContext());


                    SavetoDb.saveHistoryToDb(requireContext(), retrievedDataList);

                    // Update the local dataList
                    dataList = retrievedDataList;

                    // Call populateRecyclerView in onViewCreated
                    populateRecyclerView();
                }

                @Override
                public void onError(String error) {
                    // Handle error
                }
            });
        } else {
            // Call populateRecyclerView in onViewCreated
            populateRecyclerView();
        }
    }

    private List<DataHistory> fetchDataFromDb() {
        Log.d("RpFragment", "Fetching data from database.");
        DbHelper dbHelper = new DbHelper(requireContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<DataHistory> dataList = new ArrayList<>();

        // Specify the columns you want to retrieve
        String[] columns = {
                DbHelper.COLUMN_NOMOR,
                DbHelper.COLUMN_ID_PRODUK,
                DbHelper.COLUMN_KUANTITAS,
                DbHelper.COLUMN_TIPE_HARGA,
                DbHelper.COLUMN_TANGGAL,
                DbHelper.COLUMN_NAMA_PRODUK,
                DbHelper.COLUMN_TOTAL
        };


        Cursor cursor = db.query(DbHelper.TABLE_NAME_HISTORY, columns, null, null, null, null, DbHelper.COLUMN_TANGGAL + " DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nomor = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_NOMOR));
                @SuppressLint("Range") String idProduk = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_ID_PRODUK));
                @SuppressLint("Range") String kuantitas = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_KUANTITAS));
                @SuppressLint("Range") String tipeharga = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_TIPE_HARGA));
                @SuppressLint("Range") String tanggal = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_TANGGAL));
                @SuppressLint("Range") String namaProduk = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_NAMA_PRODUK));
                @SuppressLint("Range") String total = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_TOTAL));
                DataHistory data = new DataHistory(nomor, idProduk, kuantitas, tipeharga, tanggal, namaProduk, total);
                dataList.add(data);
            } while (cursor.moveToNext());

            cursor.close();
        }

        // Close the database
        db.close();

        return dataList;
    }

    private int getResourceId(String idProduk) {
        // Assuming "drawable" is the resource type
        String resourceName = "p" + idProduk;
        return getResources().getIdentifier(resourceName, "drawable", requireContext().getPackageName());
    }

    private void populateRecyclerView() {
        Log.d("RpFragment", "Populating RecyclerView.");
        // Initialize RecyclerView
        recyclerViewRiwayat = requireView().findViewById(R.id.recyclerViewHistory);
        recyclerViewRiwayat.setHasFixedSize(true);
        recyclerViewRiwayat.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));


        Collections.sort(dataList, new Comparator<DataHistory>() {
            @Override
            public int compare(DataHistory data1, DataHistory data2) {
                // Assuming getTanggal() returns a String date in the format that allows lexicographical comparison
                return data2.getTanggal().compareTo(data1.getTanggal());
            }
        });

        // Extract data for RecyclerView
        List<String> tanggal = new ArrayList<>();
        List<String> nama_produk = new ArrayList<>();
        List<String> harga_produk = new ArrayList<>();
        List<String> qty = new ArrayList<>();
        List<Integer> mImageview = new ArrayList<>();

        for (DataHistory data : dataList) {
            tanggal.add(data.getTanggal());  // Assuming you have a method to get the date
            nama_produk.add(data.getNamaProduk());
            harga_produk.add(data.getTotal());
            qty.add(data.getKuantitas());
            int resourceId = getResourceId(data.getIdProduk());
            mImageview.add(resourceId);
        }
        // Log statements to check data
        Log.d("RiwayatAdapter", "Tanggal List: " + tanggal);
        Log.d("RiwayatAdapter", "Nama Produk List: " + nama_produk);
        Log.d("RiwayatAdapter", "Harga Produk List: " + harga_produk);
        Log.d("RiwayatAdapter", "Image List: " + mImageview);

        // Create and set the adapter
        RiwayatAdapter riwayatAdapter = new RiwayatAdapter(tanggal, mImageview, nama_produk, harga_produk, qty);
        recyclerViewRiwayat.setAdapter(riwayatAdapter);
    }

    private void fetchDataAndPopulate() {

        SavetoDb.resetHistoryData(requireContext());
        dataList = fetchDataFromDb();

        // If data is not available in SQLite, fetch from the internet and save to SQLite
        if (dataList.isEmpty()) {
            ApiHandler.fetchHistoryData(requireContext(), new ApiHandler.VolleyCallbackHistory() {
                @Override
                public void onSuccess(List<DataHistory> retrievedDataList) {
                    // Save data to SQLite


                    SavetoDb.saveHistoryToDb(requireContext(), retrievedDataList);

                    // Update the local dataList
                    dataList = retrievedDataList;

                    // Call populateRecyclerView in onViewCreated
                    populateRecyclerView();

                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onError(String error) {
                    // Handle error
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        } else {
            // Call populateRecyclerView in onViewCreated
            populateRecyclerView();
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
