package com.mikimup.service.validation;

import com.mikimup.model.Product;
import com.mikimup.repository.ProductReader;

/** Hanya butuh kemampuan membaca, maka bergantung pada ProductReader (ISP + DIP). */
public class UniqueNameRule implements ValidationRule<Product> {
    private final ProductReader reader;

    public UniqueNameRule(ProductReader reader) { this.reader = reader; }

    @Override
    public String validate(Product p) {
        if (p.getNamaProduct() == null || p.getNamaProduct().trim().isEmpty()) return null;
        return reader.existsByName(p.getNamaProduct().trim(), p.getIdProduct())
                ? "Nama produk sudah ada" : null;
    }
}
