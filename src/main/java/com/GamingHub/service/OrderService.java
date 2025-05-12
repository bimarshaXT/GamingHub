package com.GamingHub.service;

import com.GamingHub.dao.OrderDAO;

public class OrderService {
    private final OrderDAO orderDAO = new OrderDAO();

    public boolean placeOrder(int customerId, int productId, int quantity, float totalPrice) {
        return orderDAO.placeOrder(customerId, productId, quantity, totalPrice);
    }
}
