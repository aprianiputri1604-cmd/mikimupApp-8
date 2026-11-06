package com.mikimup.view;

import com.mikimup.model.Product;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/** View Kelola Produk (FR-014): tabel + form CRUD. Tanpa logika bisnis. */
public class ProductView extends JFrame {
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"ID", "Nama", "Kategori", "Harga", "Stok", "Stok Min", "Status"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(tableModel);
    private final JTextField txtNama = new JTextField();
    private final JTextField txtKategori = new JTextField();
    private final JTextField txtHarga = new JTextField();
    private final JTextField txtDeskripsi = new JTextField();
    private final JTextField txtStok = new JTextField("0");
    private final JTextField txtStokMin = new JTextField("0");
    private final JButton btnTambah = new JButton("Tambah");
    private final JButton btnUbah = new JButton("Ubah");
    private final JButton btnHapus = new JButton("Hapus");
    private final JButton btnReset = new JButton("Reset Form");
    private final JButton btnLogout = new JButton("Logout");
    private final NumberFormat rupiah = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"));
    private List<Product> shown = new ArrayList<>();

    public ProductView(String namaAdmin) {
        super("Mikimup - Kelola Produk (" + namaAdmin + ")");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(0, 4, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Data Produk"));
        form.add(new JLabel("Nama")); form.add(txtNama);
        form.add(new JLabel("Kategori")); form.add(txtKategori);
        form.add(new JLabel("Harga (Rp)")); form.add(txtHarga);
        form.add(new JLabel("Deskripsi")); form.add(txtDeskripsi);
        form.add(new JLabel("Jumlah Stok")); form.add(txtStok);
        form.add(new JLabel("Stok Minimum")); form.add(txtStokMin);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnTambah); buttons.add(btnUbah); buttons.add(btnHapus);
        buttons.add(btnReset); buttons.add(btnLogout);

        JPanel south = new JPanel(new BorderLayout(5, 5));
        south.add(form, BorderLayout.CENTER);
        south.add(buttons, BorderLayout.SOUTH);
        add(south, BorderLayout.SOUTH);

        setSize(850, 520);
        setLocationRelativeTo(null);
    }

    // ----- menampilkan data -----
    public void setProducts(List<Product> products) {
        shown = new ArrayList<>(products);
        tableModel.setRowCount(0);
        for (Product p : products) {
            tableModel.addRow(new Object[]{p.getIdProduct(), p.getNamaProduct(), p.getKategori(),
                    rupiah.format(p.getHarga()), p.getJumlahStok(), p.getStokMinimum(), p.getStatusStok()});
        }
    }

    public Product getSelectedProduct() {
        int row = table.getSelectedRow();
        return row < 0 ? null : shown.get(row);
    }

    public void showProduct(Product p) {
        txtNama.setText(p.getNamaProduct());
        txtKategori.setText(p.getKategori());
        txtHarga.setText(p.getHarga().toPlainString());
        txtDeskripsi.setText(p.getDeskripsi());
        txtStok.setText(String.valueOf(p.getJumlahStok()));
        txtStokMin.setText(String.valueOf(p.getStokMinimum()));
    }

    public void clearForm() {
        txtNama.setText(""); txtKategori.setText(""); txtHarga.setText("");
        txtDeskripsi.setText(""); txtStok.setText("0"); txtStokMin.setText("0");
        table.clearSelection();
    }

    // ----- membaca input -----
    public String getNama() { return txtNama.getText().trim(); }
    public String getKategori() { return txtKategori.getText().trim(); }
    public String getHargaText() { return txtHarga.getText().trim(); }
    public String getDeskripsi() { return txtDeskripsi.getText().trim(); }
    public String getStokText() { return txtStok.getText().trim(); }
    public String getStokMinText() { return txtStokMin.getText().trim(); }

    // ----- listener & dialog -----
    public void addTambahListener(ActionListener l) { btnTambah.addActionListener(l); }
    public void addUbahListener(ActionListener l) { btnUbah.addActionListener(l); }
    public void addHapusListener(ActionListener l) { btnHapus.addActionListener(l); }
    public void addResetListener(ActionListener l) { btnReset.addActionListener(l); }
    public void addLogoutListener(ActionListener l) { btnLogout.addActionListener(l); }
    public void addSelectionListener(ListSelectionListener l) { table.getSelectionModel().addListSelectionListener(l); }

    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg, "Mikimup", JOptionPane.INFORMATION_MESSAGE); }
    public void showError(String msg) { JOptionPane.showMessageDialog(this, msg, "Terjadi Kesalahan", JOptionPane.ERROR_MESSAGE); }
    public boolean confirm(String msg) {
        return JOptionPane.showConfirmDialog(this, msg, "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
