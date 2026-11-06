package com.mikimup.service;

import com.mikimup.model.User;
import com.mikimup.repository.UserRepository;

/** FR-016 Login: pesan gagal dibuat umum (tidak menyebut kolom mana yang salah). */
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordHasher hasher;

    public AuthService(UserRepository userRepository, PasswordHasher hasher) {
        this.userRepository = userRepository;
        this.hasher = hasher;
    }

    /** @return User bila berhasil, null bila gagal */
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) return null;
        return user.getPasswordHash().equals(hasher.hash(password)) ? user : null;
    }
}
