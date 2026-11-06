package com.mikimup.model;

import java.math.BigDecimal;

/** Model produk (gabungan entitas Product dan Stock pada SRS). */
public class Product {
    private int idProduct;
    private String namaProduct;
    private BigDecimal harga;
    private String kategori;
    private String deskripsi;
    private int jumlahStok;
    private int stokMinimum;

    public Product(int idProduct, String namaProduct, BigDecimal harga, String kategori,
                   String deskripsi, int jumlahStok, int stokMinimum) {
        this.idProduct = idProduct;
        this.namaProduct = namaProduct;
        this.harga = harga;
        this.kategori = kategori;
        this.deskripsi = deskripsi;
        this.jumlahStok = jumlahStok;
        this.stokMinimum = stokMinimum;
    }

    public int getIdProduct() { return idProduct; }
    public void setIdProduct(int idProduct) { this.idProduct = idProduct; }
    public String getNamaProduct() { return namaProduct; }
    public BigDecimal getHarga() { return harga; }
    public String getKategori() { return kategori; }
    public String getDeskripsi() { return deskripsi; }
    public int getJumlahStok() { return jumlahStok; }
    public int getStokMinimum() { return stokMinimum; }

    /** statusStok sesuai SRS: habis / menipis / tersedia. */
    public String getStatusStok() {
        if (jumlahStok == 0) return "habis";
        if (jumlahStok <= stokMinimum) return "menipis";
        return "tersedia";
    }
}
