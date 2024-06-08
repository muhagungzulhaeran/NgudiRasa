package com.example.ngudirasa.Model;
public class DataHistory {
    private String nomor;
    private String idProduk;

    private String tipeHarga;

    private String kuantitas;
    private String tanggal;

    private String namaProduk;

    private String total;


    public DataHistory(String nomor, String idProduk, String kuantitas, String tipeHarga, String tanggal, String namaProduk, String total) {
        this.nomor = nomor;
        this.idProduk = idProduk;
        this.tipeHarga = tipeHarga;
        this.kuantitas = kuantitas;
        this.tanggal = tanggal;
        this.namaProduk = namaProduk;
        this.total = total;

    }


    public String getNomor() {
        return nomor;
    }

    public String getIdProduk() {
        return idProduk;
    }


    public String getKuantitas() {
        return kuantitas;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public String getTotal() {
        return total;
    }

    public String getTipeHarga() {return tipeHarga;}
}
