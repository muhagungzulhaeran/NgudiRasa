package com.example.ngudirasa.AsynTask;

import static com.example.ngudirasa.Model.ApiHandler.BASE_URL;
import static com.example.ngudirasa.Model.ApiHandler.ENDPOINT_HISTORY_INPUT;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PostToRiwayatTask {

    private Context context;
    private String idProduk;
    private String harga;
    private String quantity;

    public PostToRiwayatTask(Context context, String idProduk, String harga, String quantity) {
        this.context = context;
        this.idProduk = idProduk;
        this.harga = harga;
        this.quantity = quantity;
    }

    public void execute() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            // Background work here

            // Perform the POST request
            performPostRequest();

            handler.post(() -> {
                // UI Thread work here (if needed)
            });
        });
    }

    private void performPostRequest() {
        // Construct the URL for posting data to Riwayat
        String url = BASE_URL + ENDPOINT_HISTORY_INPUT;
        RequestQueue queue = Volley.newRequestQueue(context);
        // Create parameters for the POST request
        final Map<String, String> postParams = new HashMap<>();

        postParams.put("id_produk", idProduk);
        postParams.put("harga", harga);
        postParams.put("quantity", quantity);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return postParams;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Set content type to application/x-www-form-urlencoded
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Add the request to the RequestQueue
        queue.add(stringRequest);

        // For simplicity, we'll just print the parameters here
        System.out.println("POST request parameters: " + postParams);
    }
}
