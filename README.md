# Mikimup App - MVP Kelola Produk (FR-014) + Login (FR-016)

Java + Maven. Arsitektur: MVC + Service + Repository (JDBC), prinsip SOLID.

## Menjalankan di NetBeans
1. File > Open Project > pilih folder `MikimupApp` (yang berisi `pom.xml`).
   Tunggu Maven mengunduh dependency (mysql-connector-j) - butuh internet pertama kali.
2. Nyalakan MySQL (XAMPP), jalankan `database/schema.sql` di phpMyAdmin.
3. Sesuaikan USER/PASSWORD di `config/MySqlConnectionProvider.java` bila perlu.
4. Klik kanan project > Run (main class: `com.mikimup.Main`). Login: `admin` / `admin123`.
   Bila database mati, aplikasi menawarkan mode demo (data di memori).

Jika JDK kamu bukan 17, ubah `maven.compiler.source/target` di `pom.xml` (mis. 11 atau 21).
