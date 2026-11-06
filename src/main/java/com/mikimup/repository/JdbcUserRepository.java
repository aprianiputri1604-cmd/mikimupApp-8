package com.mikimup.repository;

import com.mikimup.config.ConnectionProvider;
import com.mikimup.model.User;
import java.sql.*;

public class JdbcUserRepository implements UserRepository {
    private final ConnectionProvider provider;

    public JdbcUserRepository(ConnectionProvider provider) { this.provider = provider; }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT idUser, nama, username, password, role FROM user WHERE username = ?";
        try (Connection c = provider.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Gagal memeriksa akun", e);
        }
    }
}
