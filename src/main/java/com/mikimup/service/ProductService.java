package com.mikimup.service;

import com.mikimup.model.Product;
import com.mikimup.repository.ProductRepository;
import com.mikimup.service.validation.ValidationRule;
import java.util.ArrayList;
import java.util.List;

/** SRP: logika bisnis produk. DIP: bergantung pada interface repository dan rule. */
public class ProductService {
    private final ProductRepository repository;
    private final List<ValidationRule<Product>> rules;

    public ProductService(ProductRepository repository, List<ValidationRule<Product>> rules) {
        this.repository = repository;
        this.rules = rules;
    }

    public List<Product> getAll() { return repository.findAll(); }

    public void add(Product p) {
        validate(p);
        repository.insert(p);
    }

    public void update(Product p) {
        validate(p);
        repository.update(p);
    }

    public void remove(int id) { repository.delete(id); }

    private void validate(Product p) {
        List<String> errors = new ArrayList<>();
        for (ValidationRule<Product> rule : rules) {
            String msg = rule.validate(p);
            if (msg != null) errors.add(msg);
        }
        if (!errors.isEmpty()) throw new ValidationException(errors);
    }
}
