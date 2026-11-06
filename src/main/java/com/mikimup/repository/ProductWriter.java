package com.mikimup.repository;

import com.mikimup.model.Product;

/** ISP: antarmuka khusus operasi tulis. */
public interface ProductWriter {
    void insert(Product product);
    void update(Product product);
    void delete(int id);
}
