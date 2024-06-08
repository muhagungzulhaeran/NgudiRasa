package com.example.ngudirasa.Model;

public class DataUser {

    private String id_user;

    private String email;
    private String nama_user;
    private String password;
    private String valid;


    public DataUser(String id_user, String email, String nama_user, String password, String valid) {
        this.id_user = id_user;
        this.email = email;
        this.nama_user = nama_user;
        this.password = password;
        this.valid = valid;
    }

    public String getIdUser() {
        return id_user;
    }

    public String getEmail() {
        return email;
    }

    public String getNamaUser() {
        return nama_user;
    }

    public String getPassword() {
        return password;
    }

    public String isValid() {
        return valid;
    }

}
