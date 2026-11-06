package com.mikimup.model;

public class User {
    private final int idUser;
    private final String nama;
    private final String username;
    private final String passwordHash;
    private final String role;

    public User(int idUser, String nama, String username, String passwordHash, String role) {
        this.idUser = idUser;
        this.nama = nama;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public int getIdUser() { return idUser; }
    public String getNama() { return nama; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public String getRole() { return role; }
}
