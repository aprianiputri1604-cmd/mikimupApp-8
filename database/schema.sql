CREATE DATABASE IF NOT EXISTS mikimup_db;
USE mikimup_db;

CREATE TABLE IF NOT EXISTS user (
    idUser INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL,          -- SHA-256 hex
    role VARCHAR(20) NOT NULL DEFAULT 'Admin/Penjual'
);

CREATE TABLE IF NOT EXISTS product (
    idProduct INT AUTO_INCREMENT PRIMARY KEY,
    namaProduct VARCHAR(100) NOT NULL,
    harga DECIMAL(12,2) NOT NULL,
    kategori VARCHAR(50) NOT NULL,
    deskripsi VARCHAR(255),
    aktif TINYINT(1) NOT NULL DEFAULT 1     -- 0 = dinonaktifkan (soft delete)
);

CREATE TABLE IF NOT EXISTS stock (
    idStock INT AUTO_INCREMENT PRIMARY KEY,
    idProduct INT NOT NULL,
    jumlahStok INT NOT NULL DEFAULT 0,
    stokMinimum INT NOT NULL DEFAULT 0,
    FOREIGN KEY (idProduct) REFERENCES product(idProduct)
);

INSERT INTO user (nama, username, password, role)
VALUES ('Admin Mikimup', 'admin', SHA2('admin123', 256), 'Admin/Penjual');

INSERT INTO product (namaProduct, harga, kategori, deskripsi) VALUES
 ('Kimbab Original', 15000, 'Kimbab', 'Kimbab isi sayur dan telur'),
 ('Nigiri Salmon', 12000, 'Nigiri', 'Nigiri salmon segar');
INSERT INTO stock (idProduct, jumlahStok, stokMinimum) VALUES (1, 20, 5), (2, 4, 5);
