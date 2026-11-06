package com.mikimup.controller;

import com.mikimup.model.Product;
import com.mikimup.repository.RepositoryException;
import com.mikimup.service.ProductService;
import com.mikimup.service.ValidationException;
import com.mikimup.view.ProductView;
import java.math.BigDecimal;

/** Penghubung ProductView dan ProductService: baca input, panggil service, perbarui tampilan. */
public class ProductController {
    private final ProductView view;
    private final ProductService service;

    public ProductController(ProductView view, ProductService service, Runnable onLogout) {
        this.view = view;
        this.service = service;

        view.addTambahListener(e -> tambah());
        view.addUbahListener(e -> ubah());
        view.addHapusListener(e -> hapus());
        view.addResetListener(e -> view.clearForm());
        view.addLogoutListener(e -> { view.dispose(); onLogout.run(); });
        view.addSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && view.getSelectedProduct() != null)
                view.showProduct(view.getSelectedProduct());
        });
        refresh();
    }

    private void refresh() {
        try {
            view.setProducts(service.getAll());
        } catch (RepositoryException ex) {
            view.showError(ex.getMessage());
        }
    }

    private void tambah() {
        Product p = readForm(0);
        if (p == null) return;
        execute(() -> service.add(p), "Produk berhasil ditambahkan");
    }

    private void ubah() {
        Product selected = view.getSelectedProduct();
        if (selected == null) { view.showMessage("Pilih produk pada tabel terlebih dahulu"); return; }
        Product p = readForm(selected.getIdProduct());
        if (p == null) return;
        execute(() -> service.update(p), "Produk berhasil diubah");
    }

    private void hapus() {
        Product selected = view.getSelectedProduct();
        if (selected == null) { view.showMessage("Pilih produk pada tabel terlebih dahulu"); return; }
        if (!view.confirm("Hapus produk \"" + selected.getNamaProduct() + "\"?")) return;
        execute(() -> service.remove(selected.getIdProduct()), "Produk berhasil dihapus");
    }

    /** Menjalankan aksi service, menangani error validasi dan database di satu tempat. */
    private void execute(Runnable action, String successMessage) {
        try {
            action.run();
            view.clearForm();
            refresh();
            view.showMessage(successMessage);
        } catch (ValidationException ex) {
            view.showError(ex.getMessage());
        } catch (RepositoryException ex) {
            view.showError(ex.getMessage());
        }
    }

    private Product readForm(int id) {
        try {
            BigDecimal harga = view.getHargaText().isEmpty() ? null : new BigDecimal(view.getHargaText());
            int stok = Integer.parseInt(view.getStokText());
            int stokMin = Integer.parseInt(view.getStokMinText());
            return new Product(id, view.getNama(), harga, view.getKategori(), view.getDeskripsi(), stok, stokMin);
        } catch (NumberFormatException ex) {
            view.showError("Harga, stok, dan stok minimum harus berupa angka");
            return null;
        }
    }
}
