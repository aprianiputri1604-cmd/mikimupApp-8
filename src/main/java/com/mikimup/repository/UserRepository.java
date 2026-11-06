package com.mikimup.repository;

import com.mikimup.model.User;

public interface UserRepository {
    /** @return user, atau null bila tidak ditemukan */
    User findByUsername(String username);
}
