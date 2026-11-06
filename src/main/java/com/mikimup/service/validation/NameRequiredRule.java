package com.mikimup.service.validation;

import com.mikimup.model.Product;

public class NameRequiredRule implements ValidationRule<Product> {
    @Override
    public String validate(Product p) {
        return (p.getNamaProduct() == null || p.getNamaProduct().trim().isEmpty())
                ? "Nama produk wajib diisi" : null;
    }
}
