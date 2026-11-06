package com.mikimup.service.validation;

import com.mikimup.model.Product;

public class CategoryRequiredRule implements ValidationRule<Product> {
    @Override
    public String validate(Product p) {
        return (p.getKategori() == null || p.getKategori().trim().isEmpty())
                ? "Kategori wajib diisi" : null;
    }
}
