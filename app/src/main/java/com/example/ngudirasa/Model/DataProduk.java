package com.example.ngudirasa.Model;

public class DataProduk {

    private String idProduk;
    private String namaProduk;
    private String hargaPremium;
    private String hargaEkonomis;
    private String kategori;
    private String deskripsi;
    private String foto;

    public DataProduk(String idProduk, String namaProduk, String hargaPremium, String hargaEkonomis, String kategori, String deskripsi) {
        this.idProduk = idProduk;
        this.namaProduk = namaProduk;
        this.hargaPremium = hargaPremium;
        this.hargaEkonomis = hargaEkonomis;
        this.kategori = kategori;
        this.deskripsi = deskripsi;
    }

    public String getIdProduk() {
        return idProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public String getHargaPremium() {
        return hargaPremium;
    }

    public String getHargaEkonomis() {
        return hargaEkonomis;
    }

    public String getKategori() {
        return kategori;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getFoto() {
        return foto;
    }
}
