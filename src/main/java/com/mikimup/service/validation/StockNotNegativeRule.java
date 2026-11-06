package com.mikimup.service.validation;

import com.mikimup.model.Product;

public class StockNotNegativeRule implements ValidationRule<Product> {
    @Override
    public String validate(Product p) {
        return (p.getJumlahStok() < 0 || p.getStokMinimum() < 0)
                ? "Stok dan stok minimum tidak boleh negatif" : null;
    }
}
