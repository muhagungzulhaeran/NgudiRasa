package com.example.ngudirasa.Model;

public class Data {

    private int image;
    private String nama_produk, harga_produk;

    public Data(int image, String nama_produk, String harga_produk){
        this.image = image;
        this.nama_produk = nama_produk;
        this.harga_produk = harga_produk;
    }

    public int getImage() {
        return image;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public String getHarga_produk() {
        return harga_produk;
    }
}
