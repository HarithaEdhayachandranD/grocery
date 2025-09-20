package com.grocery.model;

import java.util.HashSet;
import java.util.Set;

public class Wishlist {
    private int userId;
    private Set<Integer> items = new HashSet<>();

    public Wishlist(int userId) {
        this.userId = userId;
    }

    public void addItem(int productId) {
        items.add(productId);
    }

    public void removeItem(int productId) {
        items.remove(productId);
    }

    public Set<Integer> getItems() {
        return items;
    }
}
