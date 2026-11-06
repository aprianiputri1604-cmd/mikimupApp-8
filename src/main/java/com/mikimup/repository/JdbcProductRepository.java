package com.mikimup.repository;

import com.mikimup.config.ConnectionProvider;
import com.mikimup.model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Implementasi JDBC (DAO). Hapus = nonaktifkan (soft delete) agar riwayat transaksi tetap utuh. */
public class JdbcProductRepository implements ProductRepository {
    private static final String SELECT =
        "SELECT p.idProduct, p.namaProduct, p.harga, p.kategori, p.deskripsi, "
      + "COALESCE(s.jumlahStok,0) AS jumlahStok, COALESCE(s.stokMinimum,0) AS stokMinimum "
      + "FROM product p LEFT JOIN stock s ON s.idProduct = p.idProduct WHERE p.aktif = 1";

    private final ConnectionProvider provider;

    public JdbcProductRepository(ConnectionProvider provider) {
        this.provider = provider;
    }

    @Override
    public List<Product> findAll() {
        List<Product> result = new ArrayList<>();
        try (Connection c = provider.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT + " ORDER BY p.idProduct");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) result.add(map(rs));
        } catch (SQLException e) {
            throw new RepositoryException("Gagal memuat data produk", e);
        }
        return result;
    }

    @Override
    public Product findById(int id) {
        try (Connection c = provider.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT + " AND p.idProduct = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        } catch (SQLException e) {
            throw new RepositoryException("Gagal mencari produk", e);
        }
    }

    @Override
    public boolean existsByName(String nama, int excludeId) {
        String sql = "SELECT COUNT(*) FROM product WHERE LOWER(namaProduct) = LOWER(?) "
                   + "AND aktif = 1 AND idProduct <> ?";
        try (Connection c = provider.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nama);
            ps.setInt(2, excludeId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RepositoryException("Gagal memeriksa nama produk", e);
        }
    }

    @Override
    public void insert(Product p) {
        try (Connection c = provider.getConnection()) {
            c.setAutoCommit(false);
            try {
                int newId;
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO product (namaProduct, harga, kategori, deskripsi, aktif) VALUES (?,?,?,?,1)",
                        Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, p.getNamaProduct());
                    ps.setBigDecimal(2, p.getHarga());
                    ps.setString(3, p.getKategori());
                    ps.setString(4, p.getDeskripsi());
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        keys.next();
                        newId = keys.getInt(1);
                    }
                }
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO stock (idProduct, jumlahStok, stokMinimum) VALUES (?,?,?)")) {
                    ps.setInt(1, newId);
                    ps.setInt(2, p.getJumlahStok());
                    ps.setInt(3, p.getStokMinimum());
                    ps.executeUpdate();
                }
                p.setIdProduct(newId);
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RepositoryException("Gagal menyimpan produk", e);
        }
    }

    @Override
    public void update(Product p) {
        try (Connection c = provider.getConnection()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement ps = c.prepareStatement(
                        "UPDATE product SET namaProduct=?, harga=?, kategori=?, deskripsi=? WHERE idProduct=?")) {
                    ps.setString(1, p.getNamaProduct());
                    ps.setBigDecimal(2, p.getHarga());
                    ps.setString(3, p.getKategori());
                    ps.setString(4, p.getDeskripsi());
                    ps.setInt(5, p.getIdProduct());
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = c.prepareStatement(
                        "UPDATE stock SET jumlahStok=?, stokMinimum=? WHERE idProduct=?")) {
                    ps.setInt(1, p.getJumlahStok());
                    ps.setInt(2, p.getStokMinimum());
                    ps.setInt(3, p.getIdProduct());
                    ps.executeUpdate();
                }
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RepositoryException("Gagal mengubah produk", e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection c = provider.getConnection();
             PreparedStatement ps = c.prepareStatement("UPDATE product SET aktif = 0 WHERE idProduct = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Gagal menghapus produk", e);
        }
    }

    private Product map(ResultSet rs) throws SQLException {
        return new Product(rs.getInt("idProduct"), rs.getString("namaProduct"),
                rs.getBigDecimal("harga"), rs.getString("kategori"), rs.getString("deskripsi"),
                rs.getInt("jumlahStok"), rs.getInt("stokMinimum"));
    }
}
