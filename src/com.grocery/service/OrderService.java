package com.grocery.service;

import com.grocery.dao.OrderDAO;
import com.grocery.model.Order;

public class OrderService {
    private OrderDAO orderDAO = new OrderDAO();

    public boolean placeOrder(Order order) {
        return orderDAO.createOrder(order);
    }
}
