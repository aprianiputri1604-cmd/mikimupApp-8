package com.mikimup.service.validation;

import com.mikimup.model.Product;
import java.math.BigDecimal;

public class PricePositiveRule implements ValidationRule<Product> {
    @Override
    public String validate(Product p) {
        return (p.getHarga() == null || p.getHarga().compareTo(BigDecimal.ZERO) <= 0)
                ? "Harga harus berupa angka lebih dari 0" : null;
    }
}
