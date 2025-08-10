package com.Model;

import java.sql.*;
import java.util.ArrayList;

public class ContactDAO {
    private Connection connect(){
        String userHome = System.getProperty("user.home");
        String dbPath = userHome + "/contact.db";

        String url = "jdbc:sqlite:" + dbPath;
        String sql = "CREATE TABLE IF NOT EXISTS contact (\n"
                + " id INTEGER PRIMARY KEY,\n"
                + " name TEXT NOT NULL,\n"
                + " phone TEXT NOT NULL,\n"
                + " mail TEXT NOT NULL\n"
                + ");";

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);

            try(Statement stmt = conn.createStatement()){
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

            try{
                if(conn != null){
                    conn.close();
                }
            } catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }

        return conn;
    }

    public int insertContact(Contact contact){
        String sql = "INSERT INTO contact(name, phone, mail) VALUES(?, ?, ?);";
        int generatedId = -1;

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pstmt.setString(1, contact.getName());
            pstmt.setString(2, contact.getPhone());
            pstmt.setString(3, contact.getMail());

            pstmt.executeUpdate();

            try(ResultSet rs = pstmt.getGeneratedKeys()){
                if(rs.next()){
                    generatedId = rs.getInt(1);
                }
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return generatedId;
    }

    public ArrayList<Contact> getAllContacts(){
        String sql = "SELECT id, name, phone, mail FROM contact;";
        ArrayList<Contact> contacts = new ArrayList<>();

        try(Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()){
                Contact contact = new Contact(rs.getString("name"), rs.getString("phone"), rs.getString("mail"));
                contact.setExistingIdContact(rs.getInt("id"));

                contacts.add(contact);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return contacts;
    }

    public void updateContact(Contact contact){
        String sql = "UPDATE contact SET name = ?, phone = ?, mail = ? WHERE id = ?;";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, contact.getName());
            pstmt.setString(2, contact.getPhone());
            pstmt.setString(3, contact.getMail());
            pstmt.setInt(4, contact.getIdContact());

            pstmt.executeUpdate();
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteContact(int idContact){
        String sql = "DELETE FROM contact WHERE id = ?;";

        try(Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, idContact);

            pstmt.executeUpdate();
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void clearAllContacts(){
        String sql = "DELETE FROM contact;";

        try(Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.executeUpdate();
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
