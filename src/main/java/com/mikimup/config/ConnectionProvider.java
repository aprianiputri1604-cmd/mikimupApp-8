package com.mikimup.config;

import java.sql.Connection;
import java.sql.SQLException;

/** Abstraksi penyedia koneksi (DIP): repository bergantung pada interface ini, bukan pada MySQL. */
public interface ConnectionProvider {
    Connection getConnection() throws SQLException;
}
