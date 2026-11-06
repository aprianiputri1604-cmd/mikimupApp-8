package com.mikimup.repository;

import com.mikimup.model.Product;
import java.util.List;

/** ISP: antarmuka khusus operasi baca. */
public interface ProductReader {
    List<Product> findAll();
    Product findById(int id);
    boolean existsByName(String nama, int excludeId);
}
