package com.mikimup.controller;

import com.mikimup.model.User;
import com.mikimup.repository.RepositoryException;
import com.mikimup.service.AuthService;
import com.mikimup.view.LoginView;
import java.util.function.Consumer;

/** Penghubung LoginView dan AuthService. */
public class LoginController {
    private final LoginView view;
    private final AuthService authService;
    private final Consumer<User> onSuccess;

    public LoginController(LoginView view, AuthService authService, Consumer<User> onSuccess) {
        this.view = view;
        this.authService = authService;
        this.onSuccess = onSuccess;
        this.view.addLoginListener(e -> handleLogin());
    }

    private void handleLogin() {
        String username = view.getUsername();
        String password = view.getPassword();
        if (username.isEmpty() || password.isEmpty()) {
            view.showMessage("Data harus diisi");
            return;
        }
        try {
            User user = authService.login(username, password);
            if (user == null) {
                view.showMessage("Username atau password salah");
                view.clearPassword();
                return;
            }
            view.dispose();
            onSuccess.accept(user);
        } catch (RepositoryException ex) {
            view.showMessage("Tidak dapat terhubung ke database: " + ex.getMessage());
        }
    }
}
