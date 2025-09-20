package com.grocery.service;

import com.grocery.dao.ProductDAO;
import com.grocery.model.Product;
import java.util.List;

public class ProductService {

    private ProductDAO productDAO;

    public ProductService() {
        this.productDAO = new ProductDAO();
    }

    public List<Product> getAllProducts() {
        return productDAO.fetchAllProducts();
    }

    public Product findProductById(int productId) {
        List<Product> products = getAllProducts();
        for (Product product : products) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null; 
    }

    public List<Product> getProductsUnderPrice(double price) {
        List<Product> products = getAllProducts();
        return products.stream()
                .filter(p -> p.getPrice() <= price)
                .toList();
    }
}
