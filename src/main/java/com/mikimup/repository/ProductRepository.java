package com.mikimup.repository;

/** Gabungan reader + writer. Implementasi apa pun harus bisa saling menggantikan (LSP). */
public interface ProductRepository extends ProductReader, ProductWriter {
}
