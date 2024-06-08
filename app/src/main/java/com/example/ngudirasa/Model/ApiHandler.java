package com.example.ngudirasa.Model;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiHandler {

    // Base URL for your API
    public static final String BASE_URL = "https://script.google.com/macros/s/AKfycbxCimu1G8RnXa2zTWGkfDXetRFGnaJ3D5xrgdRyQ0e1YEeb_A7kc4DyWNeZEEGiX1PXpA/exec?action=";

    // Endpoint for fetching produk data
    private static final String ENDPOINT_PRODUK = "produk";

    // Endpoint for fetching history data
    private static final String ENDPOINT_HISTORY = "history";

    public static final String ENDPOINT_HISTORY_INPUT = "inputHistory";

    private static final String ENDPOINT_USER = "user";

    private static final String ENDPOINT_USER_REGIST = "register";

    public interface VolleyCallback {
        void onSuccess(List<DataProduk> produkList);

        void onError(String error);
    }

    public interface VolleyCallbackHistory {
        void onSuccess(List<DataHistory> historyDataList);

        void onError(String error);
    }

    public interface VolleyCallbackUser {
        void onSuccess(List<DataUser> dataUsers);

        void onError(String error);
    }

    public static void fetchData(Context context, final VolleyCallback callback) {
        // Create request queue
        RequestQueue queue = Volley.newRequestQueue(context);

        // Construct the URL for fetching produk data
        String url = BASE_URL + ENDPOINT_PRODUK;

        // Create JSON array request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Parse JSON response and convert it to a list of DataProduk objects
                        List<DataProduk> produkList = parseJsonResponse(response);

                        // Invoke the callback with the fetched produk data
                        callback.onSuccess(produkList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors
                        callback.onError(error.getMessage());
                    }
                });

        // Add the request to the RequestQueue
        queue.add(jsonArrayRequest);
    }

    public static void fetchHistoryData(Context context, final VolleyCallbackHistory callback) {
        // Create request queue
        RequestQueue queue = Volley.newRequestQueue(context);

        // Construct the URL for fetching history data
        String url = BASE_URL + ENDPOINT_HISTORY;
        Log.d("ApiHandler", "URL: " + url);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Parse JSON response and convert it to a list of DataProduk objects
                        List<DataHistory> historyList = ParseHistory(response);

                        // Invoke the callback with the fetched produk data
                        callback.onSuccess(historyList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors
                        callback.onError(error.getMessage());
                    }
                });

        // Add the request to the RequestQueue
        queue.add(jsonArrayRequest);
    }

    public static void fetchDataUser(Context context, final VolleyCallbackUser callback) {
        // Create request queue
        RequestQueue queue = Volley.newRequestQueue(context);

        // Construct the URL for fetching produk data
        String url = BASE_URL + ENDPOINT_USER;

        // Create JSON array request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        List<DataUser> userList = parseJsonUser(response);
                        callback.onSuccess(userList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors
                        callback.onError(error.getMessage());
                    }
                });

        // Add the request to the RequestQueue
        queue.add(jsonArrayRequest);
    }
    private static List<DataProduk> parseJsonResponse(JSONArray response) {
        List<DataProduk> dataList = new ArrayList<>();

        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject jsonObject = response.getJSONObject(i);

                String idProduk = jsonObject.getString("id_produk");
                String namaProduk = jsonObject.getString("nama_produk");
                String hargaPremium = jsonObject.getString("harga_premium");
                String hargaEkonomis = jsonObject.getString("harga_ekonomis");
                String kategori = jsonObject.getString("kategori");
                String deskripsi = jsonObject.getString("deskripsi");

                DataProduk dataProduk = new DataProduk(idProduk, namaProduk, hargaPremium, hargaEkonomis, kategori, deskripsi);
                dataList.add(dataProduk);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    private static List<DataUser> parseJsonUser(JSONArray response) {
        List<DataUser> dataList = new ArrayList<>();

        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject jsonObject = response.getJSONObject(i);

                String idUser = jsonObject.getString("id_user");
                String email = jsonObject.getString("email");
                String namaUser = jsonObject.getString("nama_user");
                String password = jsonObject.getString("password");
                String valid = jsonObject.getString("valid");


                DataUser DataUser = new DataUser(idUser, email, namaUser, password, valid);
                dataList.add(DataUser);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    private static List<DataHistory> ParseHistory(JSONArray response) {
        List<DataHistory> dataList = new ArrayList<>();

        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject jsonObject = response.getJSONObject(i);

                String nomor = jsonObject.getString("nomor");
                String id_produk = jsonObject.getString("id_produk");
                String kuantitas = jsonObject.getString("kuantitas");
                String tipe_harga = jsonObject.getString("tipe_harga");
                String tanggal = jsonObject.getString("tanggal");
                String nama_produk = jsonObject.getString("nama_produk");
                String total = jsonObject.getString("total");


                DataHistory DataHistory = new DataHistory(nomor, id_produk, kuantitas, tipe_harga, tanggal,nama_produk, total);
                dataList.add(DataHistory);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dataList;
    }



    public static void registerUser(Context context, String email, String username, String password, final VolleyCallbackUser callback) {
        // Create request queue
        RequestQueue queue = Volley.newRequestQueue(context);

        // Construct the URL for registration endpoint
        String url = BASE_URL + ENDPOINT_USER_REGIST;

        // Create parameters for the POST request
        final HashMap<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("nama_user", username);
        params.put("password", password);

        // Create String request for the registration API
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parse the response and convert it to a DataUser object
                            JSONObject jsonResponse = new JSONObject(response);

                            // Check if "id_user" is not null or not missing
                            if (jsonResponse.has("id_user") && !jsonResponse.isNull("id_user")) {
                                DataUser user = parseJsonUser(jsonResponse);

                                // Invoke the callback with the fetched user data
                                callback.onSuccess(Collections.singletonList(user));
                            } else {
                                // Handle the absence of "id_user" or its value being null
                                callback.onError("Username Sudah Ada");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Handle JSON parsing error
                            callback.onError("Username Sudah Ada");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle registration error
                        callback.onError(error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Set content type to application/x-www-form-urlencoded
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };

        // Add the request to the RequestQueue
        queue.add(stringRequest);
    }


    public static void postDataToRiwayat(Context context, String idProduk, String harga, String quantity, final VolleyCallbackHistory callback) {

        RequestQueue queue = Volley.newRequestQueue(context);
        // Construct the URL for posting data to Riwayat
        String url = BASE_URL + ENDPOINT_HISTORY_INPUT;


        // Create parameters for the POST request
        final HashMap<String, String> params = new HashMap<>();
        params.put("id_produk", idProduk);
        params.put("harga", harga);
        params.put("quantity", quantity);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", "Raw Response: " + response);
                        try {
                            // Parse the response and convert it to a DataUser object
                            JSONObject jsonResponse = new JSONObject(response);

                            // Check if "id_user" is not null or not missing
                            if (jsonResponse.has("nomor") && !jsonResponse.isNull("nomor")) {
                                DataHistory history = parseJsoHistory(jsonResponse);

                                // Invoke the callback with the fetched user data
                                callback.onSuccess(Collections.singletonList(history));
                            } else {
                                // Handle the absence of "id_user" or its value being null
                                callback.onError("Username Sudah Ada");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Handle JSON parsing error
                            callback.onError("Username Sudah Ada");
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle registration error
                        callback.onError(error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
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

    }


    private static DataUser parseJsonUser(JSONObject jsonObject) {
        try {
            String idUser = jsonObject.getString("id_user");
            String email = jsonObject.getString("email");
            String namaUser = jsonObject.getString("nama_user");
            String password = jsonObject.getString("password");
            String valid = jsonObject.getString("valid");

            return new DataUser(idUser, email, namaUser, password, valid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static DataHistory parseJsoHistory(JSONObject jsonObject) {
        try {
            String nomor = jsonObject.getString("nomor");
            String id_produk = jsonObject.getString("id_produk");
            String kuantitas = jsonObject.getString("kuantitas");
            String tanggal = jsonObject.getString("tanggal");
            String nama_produk = jsonObject.getString("nama_produk");
            String total = jsonObject.getString("total");
            String tipeHarga = jsonObject.getString("tipe_harga");

            return new DataHistory(nomor, id_produk, kuantitas, tipeHarga, tanggal, nama_produk, total );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


}
