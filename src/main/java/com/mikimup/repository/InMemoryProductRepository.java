package com.mikimup.repository;

import com.mikimup.model.Product;
import java.util.ArrayList;
import java.util.List;

/** Pengganti JdbcProductRepository tanpa database (mode demo / pengujian). Perilakunya sama (LSP). */
public class InMemoryProductRepository implements ProductRepository {
    private final List<Product> data = new ArrayList<>();
    private int nextId = 1;

    @Override
    public List<Product> findAll() { return new ArrayList<>(data); }

    @Override
    public Product findById(int id) {
        for (Product p : data) if (p.getIdProduct() == id) return p;
        return null;
    }

    @Override
    public boolean existsByName(String nama, int excludeId) {
        for (Product p : data)
            if (p.getIdProduct() != excludeId && p.getNamaProduct().equalsIgnoreCase(nama)) return true;
        return false;
    }

    @Override
    public void insert(Product product) {
        product.setIdProduct(nextId++);
        data.add(product);
    }

    @Override
    public void update(Product product) {
        for (int i = 0; i < data.size(); i++)
            if (data.get(i).getIdProduct() == product.getIdProduct()) { data.set(i, product); return; }
    }

    @Override
    public void delete(int id) { data.removeIf(p -> p.getIdProduct() == id); }
}
