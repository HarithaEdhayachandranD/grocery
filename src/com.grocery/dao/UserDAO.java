package com.grocery.dao;

import com.grocery.model.User;
import com.grocery.util.DBConnection;
import java.sql.*;

public class UserDAO {

    public boolean registerUser(User user) {
        String sql = "INSERT INTO users(name, email, phone, password) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getPassword());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User loginUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email=? AND password=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("password")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
