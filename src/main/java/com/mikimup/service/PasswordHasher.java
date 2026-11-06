package com.mikimup.service;

public interface PasswordHasher {
    String hash(String plain);
}
