package com.mikimup.repository;

/** Membungkus SQLException agar lapisan atas tidak bergantung pada JDBC. */
public class RepositoryException extends RuntimeException {
    public RepositoryException(String message, Throwable cause) { super(message, cause); }
}
