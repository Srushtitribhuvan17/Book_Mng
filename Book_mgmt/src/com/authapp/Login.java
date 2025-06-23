package com.authapp;

import javax.swing.*;
import java.sql.*;

public class Login {
    public static void main(String[] args) {
        new Login();
    }

    public Login() {
        JFrame f = new JFrame("Login");

        JLabel l1 = new JLabel("Username");
        l1.setBounds(20, 20, 100, 30);
        JTextField tf1 = new JTextField();
        tf1.setBounds(130, 20, 150, 30);

        JLabel l2 = new JLabel("Password");
        l2.setBounds(20, 60, 100, 30);
        JPasswordField pf1 = new JPasswordField();
        pf1.setBounds(130, 60, 150, 30);

        JButton b = new JButton("Login");
        b.setBounds(130, 100, 100, 30);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(240, 100, 100, 30);

        b.addActionListener(e -> {
            String user = tf1.getText().trim();
            String pass = new String(pf1.getPassword()).trim();

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(f, "Please enter username and password.");
                return;
            }

            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT password FROM users WHERE username = ?");
                ps.setString(1, user);
                ResultSet rs = ps.executeQuery();

                if (rs.next() && rs.getString("password").equals(pass)) {
                    JOptionPane.showMessageDialog(f, "Login Successful!");
                    f.dispose(); 
                    new Dashboard();  
                } else {
                    JOptionPane.showMessageDialog(f, "Invalid Credentials.");
                }

                con.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(f, "Database error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        registerBtn.addActionListener(e -> {
            f.dispose();          
            new Register();       
        });

        f.add(l1);
        f.add(tf1);
        f.add(l2);
        f.add(pf1);
        f.add(b);
        f.add(registerBtn);

        f.setSize(400, 200);
        f.setLayout(null);
        f.setLocationRelativeTo(null); 
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
