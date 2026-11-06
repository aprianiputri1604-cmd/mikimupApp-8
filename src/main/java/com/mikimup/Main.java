package com.mikimup;

import com.mikimup.config.ConnectionProvider;
import com.mikimup.config.MySqlConnectionProvider;
import com.mikimup.controller.LoginController;
import com.mikimup.controller.ProductController;
import com.mikimup.model.Product;
import com.mikimup.model.User;
import com.mikimup.repository.*;
import com.mikimup.service.*;
import com.mikimup.service.validation.*;
import com.mikimup.view.LoginView;
import com.mikimup.view.ProductView;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/** Composition root: satu-satunya tempat yang memilih implementasi konkret (DIP). */
public class Main {
    private static ProductService productService;
    private static AuthService authService;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::start);
    }

    private static void start() {
        PasswordHasher hasher = new Sha256PasswordHasher();
        ProductRepository productRepo;
        UserRepository userRepo;

        if (databaseAvailable()) {
            ConnectionProvider provider = new MySqlConnectionProvider();
            productRepo = new JdbcProductRepository(provider);
            userRepo = new JdbcUserRepository(provider);
        } else {
            int pilih = JOptionPane.showConfirmDialog(null,
                    "Database tidak dapat dihubungi.\nJalankan mode demo (tanpa database)?\nLogin: admin / admin123",
                    "Mikimup", JOptionPane.YES_NO_OPTION);
            if (pilih != JOptionPane.YES_OPTION) return;
            productRepo = demoRepository();
            String hash = hasher.hash("admin123");
            userRepo = u -> u.equals("admin") ? new User(1, "Admin Mikimup", "admin", hash, "Admin/Penjual") : null;
        }

        List<ValidationRule<Product>> rules = Arrays.asList(
                new NameRequiredRule(), new CategoryRequiredRule(), new PricePositiveRule(),
                new StockNotNegativeRule(), new UniqueNameRule(productRepo));
        productService = new ProductService(productRepo, rules);
        authService = new AuthService(userRepo, hasher);

        showLogin();
    }

    private static void showLogin() {
        LoginView loginView = new LoginView();
        new LoginController(loginView, authService, Main::showProducts);
        loginView.setVisible(true);
    }

    private static void showProducts(User user) {
        ProductView view = new ProductView(user.getNama());
        new ProductController(view, productService, Main::showLogin);
        view.setVisible(true);
    }

    private static boolean databaseAvailable() {
        try (java.sql.Connection c = new MySqlConnectionProvider().getConnection()) {
            return c != null;
        } catch (Exception e) {
            return false;
        }
    }

    private static ProductRepository demoRepository() {
        ProductRepository repo = new InMemoryProductRepository();
        repo.insert(new Product(0, "Kimbab Original", new BigDecimal("15000"), "Kimbab", "Kimbab isi sayur dan telur", 20, 5));
        repo.insert(new Product(0, "Nigiri Salmon", new BigDecimal("12000"), "Nigiri", "Nigiri salmon segar", 4, 5));
        return repo;
    }
}
