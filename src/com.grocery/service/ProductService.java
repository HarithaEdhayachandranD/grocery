package com.grocery.service;

import com.grocery.dao.ProductDAO;
import com.grocery.model.Product;
import java.util.List;

public class ProductService {
    private ProductDAO productDAO = new ProductDAO();

    public List<Product> listProducts() {
        return productDAO.getAllProducts();
    }

    // new
    public Product getProductById(int id) {
        for (Product p : listProducts()) {
            if (p.getId() == id) return p;
        }
        return null; // not found
    }

}
