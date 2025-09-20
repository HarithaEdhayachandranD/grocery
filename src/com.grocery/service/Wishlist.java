package com.grocery.service;

import com.grocery.model.Wishlist;

public class WishlistService {

    public WishlistService() {
    }

    public Wishlist loadWishlist(int userId) {
        return new Wishlist(userId);
    }

    public void saveWishlist(Wishlist wishlist) {
        // Do nothing for now
    }
}
