package com.grocery.dao;

import com.grocery.model.Order;
import com.grocery.util.DBConnection;
import java.sql.*;

public class OrderDAO {

    public boolean createOrder(Order order) {
        String sql = "INSERT INTO orders(user_id, total_amount, status) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, order.getUserId());
            ps.setDouble(2, order.getTotalAmount());
            ps.setString(3, order.getStatus());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
