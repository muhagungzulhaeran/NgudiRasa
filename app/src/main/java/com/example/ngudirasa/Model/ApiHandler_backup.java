package com.example.ngudirasa.Model;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// ApiHandler.java
public class ApiHandler_backup {

    public static void fetchData(Context context, final VolleyCallback callback) {
        String url = "https://script.google.com/macros/s/AKfycbwenxTViKyvQSUUGMnGZorJWD_ngKnOylXDE9nF6e6y6rjWIqFcRHjV58Rr87ZtmBUesQ/exec"; // Replace with your Google Script URL

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            // Parse JSON response
            try {
                JSONArray jsonArray = new JSONArray(response);
                List<DataProduk> dataList = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    // Extract data from JSON
                    String idProduk = jsonObject.getString("id_produk");
                    String namaProduk = jsonObject.getString("nama_produk");
                    String hargaPremium = jsonObject.getString("harga_premium");
                    String hargaEkonomis = jsonObject.getString("harga_ekonomis");
                    String kategori = jsonObject.getString("kategori");
                    String deskripsi = jsonObject.getString("deskripsi");

                    // Create a data model object
                    DataProduk data = new DataProduk(idProduk, namaProduk, hargaPremium, hargaEkonomis, kategori, deskripsi);
                    dataList.add(data);
                }

                // Pass the data to the callback
                callback.onSuccess(dataList);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            // Handle error
            callback.onError(error.toString());
        });

        requestQueue.add(stringRequest);
    }

    public interface VolleyCallback {
        void onSuccess(List<DataProduk> dataList);
        void onError(String error);
    }
}
