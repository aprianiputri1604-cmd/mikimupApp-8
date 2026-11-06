package com.mikimup.view;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

/** View Login (FR-016). Hanya menampilkan UI dan meneruskan event; tanpa logika bisnis. */
public class LoginView extends JFrame {
    private final JTextField txtUsername = new JTextField(15);
    private final JPasswordField txtPassword = new JPasswordField(15);
    private final JButton btnLogin = new JButton("Login");

    public LoginView() {
        super("Mikimup - Login Admin/Penjual");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("Mikimup App");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2; g.anchor = GridBagConstraints.CENTER;
        form.add(title, g);

        g.gridwidth = 1; g.anchor = GridBagConstraints.WEST;
        g.gridx = 0; g.gridy = 1; form.add(new JLabel("Username"), g);
        g.gridx = 1; form.add(txtUsername, g);
        g.gridx = 0; g.gridy = 2; form.add(new JLabel("Password"), g);
        g.gridx = 1; form.add(txtPassword, g);
        g.gridx = 0; g.gridy = 3; g.gridwidth = 2; g.fill = GridBagConstraints.HORIZONTAL;
        form.add(btnLogin, g);

        setContentPane(form);
        getRootPane().setDefaultButton(btnLogin);
        pack();
        setLocationRelativeTo(null);
    }

    public String getUsername() { return txtUsername.getText().trim(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
    public void addLoginListener(ActionListener l) { btnLogin.addActionListener(l); }
    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg, "Mikimup", JOptionPane.WARNING_MESSAGE); }
    public void clearPassword() { txtPassword.setText(""); }
}
