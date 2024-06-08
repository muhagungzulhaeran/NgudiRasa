package com.example.ngudirasa;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ngudirasa.Adapter.AllAdapter;
import com.example.ngudirasa.Adapter.PopulerAdapter;
import com.example.ngudirasa.Model.ApiHandler;
import com.example.ngudirasa.Model.DataProduk;
import com.example.ngudirasa.Model.DbHelper;
import com.example.ngudirasa.Model.SavetoDb;
import com.example.ngudirasa.widget.Wrap_element;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements PopulerAdapter.OnItemClickListener, AllAdapter.OnItemClickListener{

    private RecyclerView recyclerViewPopuler, recyclerViewAll;
    private Wrap_element wrapElementAll, wrapElementGlutenFree, wrapElementPastry;
    private List<DataProduk> dataList;

    private SwipeRefreshLayout swipeRefreshLayout;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // Handle the swipe-to-refresh action
            fetchDataAndPopulate();
        });

        wrapElementAll = view.findViewById(R.id.wrapElementAll);
        wrapElementGlutenFree = view.findViewById(R.id.wrapElementGlutenFree);
        wrapElementPastry = view.findViewById(R.id.wrapElementPastry);

        // Set OnClickListener for Wrap_element views
        wrapElementAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for Wrap_elementAll

                dataList = fetchDataFromDb("All");

                // Populate RecyclerView
                populateRecyclerView();
            }
        });

        wrapElementGlutenFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for Wrap_elementGlutenFree

                dataList = fetchDataFromDb("Glutenfree");

                // Populate RecyclerView
                populateRecyclerView();
            }
        });

        wrapElementPastry.setOnClickListener(v -> {
            // Handle click for Wrap_elementPastry

            dataList = fetchDataFromDb("Pastry");

            // Populate RecyclerView
            populateRecyclerView();
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Fetch data from SQLite
        dataList = fetchDataFromDb("All");

        // If data is not available in SQLite, fetch from the internet and save to SQLite
        if (dataList.isEmpty()) {
            ApiHandler.fetchData(requireContext(), new ApiHandler.VolleyCallback() {
                @Override
                public void onSuccess(List<DataProduk> retrievedDataList) {
                    // Save data to SQLite
                    SavetoDb.saveProdukToDb(requireContext(), retrievedDataList);

                    // Update the local dataList
                    dataList = retrievedDataList;

                    // Check if the view is not null before calling populateRecyclerView
                    if (getView() != null) {
                        populateRecyclerView();
                    }
                }

                @Override
                public void onError(String error) {
                    // Handle error
                }
            });
        } else {
            // Check if the view is not null before calling populateRecyclerView
            if (getView() != null) {
                populateRecyclerView();
            }
        }
    }

    private List<DataProduk> fetchDataFromDb(String category) {
        DbHelper dbHelper = new DbHelper(requireContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<DataProduk> dataList = new ArrayList<>();

        // Specify the columns you want to retrieve
        String[] columns = {
                DbHelper.COLUMN_ID_PRODUK,
                DbHelper.COLUMN_NAMA_PRODUK,
                DbHelper.COLUMN_HARGA_PREMIUM,
                DbHelper.COLUMN_HARGA_EKONOMIS,
                DbHelper.COLUMN_KATEGORI,
                DbHelper.COLUMN_DESKRIPSI
        };

        // Specify the selection criteria based on the category
        String selection = null;
        String[] selectionArgs = null;

        // Check if a specific category is selected
        if (!"All".equalsIgnoreCase(category)) {
            selection = DbHelper.COLUMN_KATEGORI + "=?";
            selectionArgs = new String[]{category};
        }

        Cursor cursor = db.query(DbHelper.TABLE_NAME_PRODUK, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String idProduk = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_ID_PRODUK));
                @SuppressLint("Range") String namaProduk = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_NAMA_PRODUK));
                @SuppressLint("Range") String hargaPremium = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_HARGA_PREMIUM));
                @SuppressLint("Range") String hargaEkonomis = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_HARGA_EKONOMIS));
                @SuppressLint("Range") String kategori = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_KATEGORI));
                @SuppressLint("Range") String deskripsi = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_DESKRIPSI));
                DataProduk data = new DataProduk(idProduk, namaProduk, hargaPremium, hargaEkonomis, kategori, deskripsi);
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
        // Initialize RecyclerViews
        recyclerViewPopuler = requireView().findViewById(R.id.populer_recyclerview);
        recyclerViewPopuler.setHasFixedSize(true);
        recyclerViewPopuler.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        recyclerViewAll = requireView().findViewById(R.id.all_recyclerview);
        recyclerViewAll.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        recyclerViewAll.setHasFixedSize(true);

        // Extract data for RecyclerView
        List<String> namaProdukList = new ArrayList<>();
        List<String> hargaProdukList = new ArrayList<>();
        List<Integer> imageList = new ArrayList<>();

        for (DataProduk data : dataList) {
            namaProdukList.add(data.getNamaProduk());
            hargaProdukList.add(data.getHargaPremium());
            int resourceId = getResourceId(data.getIdProduk());
            imageList.add(resourceId);
        }

        // Set adapters for RecyclerViews
        PopulerAdapter populerAdapter = new PopulerAdapter(imageList, namaProdukList, hargaProdukList, (PopulerAdapter.OnItemClickListener) this);
        recyclerViewPopuler.setAdapter(populerAdapter);

        AllAdapter allAdapter = new AllAdapter(imageList, namaProdukList, hargaProdukList,(AllAdapter.OnItemClickListener) this );
        recyclerViewAll.setAdapter(allAdapter);

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onPopulerItemClick(int position) {

        DataProduk clickedItem = dataList.get(position);
        Intent intent = new Intent(requireContext(), ProdukDetails.class);
        intent.putExtra("EXTRA_ID_PRODUK", clickedItem.getIdProduk());
        intent.putExtra("EXTRA_NAMA_PRODUK", clickedItem.getNamaProduk());
        intent.putExtra("EXTRA_HARGA_PREMIUM", clickedItem.getHargaPremium());
        intent.putExtra("EXTRA_HARGA_EKONOMIS", clickedItem.getHargaEkonomis());
        intent.putExtra("EXTRA_DESKRIPSI", clickedItem.getDeskripsi());
        startActivity(intent);

    }

    @Override
    public void onAllItemClick(int position) {

        DataProduk clickedItem = dataList.get(position);
        Intent intent = new Intent(requireContext(), ProdukDetails.class);
        intent.putExtra("EXTRA_ID_PRODUK", clickedItem.getIdProduk());
        intent.putExtra("EXTRA_NAMA_PRODUK", clickedItem.getNamaProduk());
        intent.putExtra("EXTRA_HARGA_PREMIUM", clickedItem.getHargaPremium());
        intent.putExtra("EXTRA_HARGA_EKONOMIS", clickedItem.getHargaEkonomis());
        intent.putExtra("EXTRA_DESKRIPSI", clickedItem.getDeskripsi());
        startActivity(intent);


    }

    private void fetchDataAndPopulate() {

        SavetoDb.resetProdukData(requireContext());

        // Fetch data from SQLite
        dataList = fetchDataFromDb("All");

        // If data is not available in SQLite, fetch from the internet and save to SQLite
        if (dataList.isEmpty()) {
            ApiHandler.fetchData(requireContext(), new ApiHandler.VolleyCallback() {
                @Override
                public void onSuccess(List<DataProduk> retrievedDataList) {
                    // Save data to SQLite
                    SavetoDb.saveProdukToDb(requireContext(), retrievedDataList);

                    // Update the local dataList
                    dataList = retrievedDataList;

                    // Populate RecyclerView
                    populateRecyclerView();

                    // Hide the refreshing indicator
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onError(String error) {
                    // Handle error

                    // Hide the refreshing indicator
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        } else {
            // Populate RecyclerView
            populateRecyclerView();

            // Hide the refreshing indicator
            swipeRefreshLayout.setRefreshing(false);
        }
    }



}
