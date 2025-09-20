
package com.grocery.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private int userId;
    private Map<Integer, Integer> items = new HashMap<>();

    public Cart(int userId) {
        this.userId = userId;
    }

    public void addItem(int productId, int quantity) {
        items.put(productId, items.getOrDefault(productId, 0) + quantity);
    }

    public void removeItem(int productId) {
        items.remove(productId);
    }

    public void updateItemQuantity(int productId, int quantity) {
        if (items.containsKey(productId)) {
            items.put(productId, quantity);
        }
    }

    public void clearCart() {
        items.clear();
    }

    public Map<Integer, Integer> getItems() {
        return items;
    }
}
