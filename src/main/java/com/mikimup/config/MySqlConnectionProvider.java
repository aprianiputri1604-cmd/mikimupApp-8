package com.mikimup.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** SRP: satu-satunya kelas yang tahu detail koneksi MySQL. */
public class MySqlConnectionProvider implements ConnectionProvider {
    private static final String URL = "jdbc:mysql://localhost:3306/mikimup_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
