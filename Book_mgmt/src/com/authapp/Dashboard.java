package com.authapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class Dashboard extends JFrame {
    JTextField tfTitle = new JTextField();
    JTextField tfAuthor = new JTextField();
    JComboBox<String> cbCategory = new JComboBox<>();
    JTable table = new JTable();
    DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Title", "Author", "Category"}, 0);

    public Dashboard() {
        setTitle("Book Dashboard");
        setSize(600, 500);
        setLayout(null);

        // Labels
        addLabel("Title:", 20, 20);
        addLabel("Author:", 20, 60);
        addLabel("Category:", 20, 100);

       
        tfTitle.setBounds(100, 20, 150, 25);
        tfAuthor.setBounds(100, 60, 150, 25);
        cbCategory.setBounds(100, 100, 150, 25);
        add(tfTitle);
        add(tfAuthor);
        add(cbCategory);

     
        JButton btnAdd = new JButton("Add Book");
        JButton btnDelete = new JButton("Delete Selected");
        JButton btnRefresh = new JButton("Refresh");

        btnAdd.setBounds(270, 20, 120, 25);
        btnDelete.setBounds(270, 60, 150, 25);
        btnRefresh.setBounds(270, 100, 120, 25);

        add(btnAdd); add(btnDelete); add(btnRefresh);

        // Table
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(20, 150, 540, 280);
        table.setModel(model);
        add(pane);

        
        loadCategories();
        loadBooks();

       
        btnAdd.addActionListener(e -> addBook());
        btnDelete.addActionListener(e -> deleteSelectedBook());
        btnRefresh.addActionListener(e -> loadBooks());

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void addLabel(String text, int x, int y) {
        JLabel l = new JLabel(text);
        l.setBounds(x, y, 80, 25);
        add(l);
    }

    private void loadCategories() {
        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT name FROM categories");
            while (rs.next()) {
                cbCategory.addItem(rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBooks() {
        model.setRowCount(0);
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT b.id, b.title, b.author, c.name FROM books b JOIN categories c ON b.category_id = c.id";
            ResultSet rs = con.createStatement().executeQuery(query);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4)
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addBook() {
        try {
            String title = tfTitle.getText();
            String author = tfAuthor.getText();
            String category = (String) cbCategory.getSelectedItem();

            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO books (title, author, category_id) VALUES (?, ?, (SELECT id FROM categories WHERE name = ?))");
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, category);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book added.");
            loadBooks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteSelectedBook() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        int id = (int) model.getValueAt(row, 0);
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM books WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book deleted.");
            loadBooks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}
