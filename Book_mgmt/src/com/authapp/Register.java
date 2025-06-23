package com.authapp;

import javax.swing.*;
import java.sql.*;

public class Register {
    public static void main(String[] args) {
        new Register();
    }

    public Register() {
        JFrame f = new JFrame("Register");

        JLabel l1 = new JLabel("Username");
        l1.setBounds(20, 20, 100, 30);
        JTextField tf1 = new JTextField();
        tf1.setBounds(130, 20, 150, 30);

        JLabel l2 = new JLabel("Password");
        l2.setBounds(20, 60, 100, 30);
        JPasswordField pf1 = new JPasswordField();
        pf1.setBounds(130, 60, 150, 30);

        JButton b = new JButton("Register");
        b.setBounds(130, 100, 100, 30);

        b.addActionListener(e -> {
            String user = tf1.getText().trim();
            String pass = new String(pf1.getPassword()).trim();

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(f, "Username and password cannot be empty.");
                return;
            }

            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("INSERT INTO users(username, password) VALUES(?, ?)");
                ps.setString(1, user);
                ps.setString(2, pass);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(f, "Registered Successfully!");
                con.close();

                f.dispose();         
                new Login();         
            } catch (SQLException ex) {
                if (ex.getErrorCode() == 1062) {  
                    JOptionPane.showMessageDialog(f, "Username already exists. Try another.");
                } else {
                    JOptionPane.showMessageDialog(f, "Database error: " + ex.getMessage());
                }
                ex.printStackTrace();
            }
        });

        f.add(l1); f.add(tf1); f.add(l2); f.add(pf1); f.add(b);
        f.setSize(400, 200);
        f.setLayout(null);
        f.setLocationRelativeTo(null);  
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
