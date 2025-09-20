//package com.grocery;
//
//import com.grocery.model.*;
//import com.grocery.service.*;
//
//import java.util.List;
//import java.util.Scanner;
//
//public class Main {
//    private static Scanner sc = new Scanner(System.in);
//    private static UserService userService = new UserService();
//    private static ProductService productService = new ProductService();
//    private static OrderService orderService = new OrderService();
//
//    public static void main(String[] args) {
//        while (true) {
//            System.out.println("\n=== Online Grocery Store ===");
//            System.out.println("1. Register");
//            System.out.println("2. Login");
//            System.out.println("3. Exit");
//            int choice = sc.nextInt();
//            sc.nextLine();
//
//            switch (choice) {
//                case 1 -> register();
//                case 2 -> login();
//                case 3 -> System.exit(0);
//                default -> System.out.println("Invalid choice!");
//            }
//        }
//    }
//
//    private static void register() {
//        System.out.print("Enter name: ");
//        String name = sc.nextLine();
//        System.out.print("Enter email: ");
//        String email = sc.nextLine();
//        System.out.print("Enter phone: ");
//        String phone = sc.nextLine();
//        System.out.print("Enter password: ");
//        String password = sc.nextLine();
//
//        User user = new User(0, name, email, phone, password);
//        if (userService.register(user)) {
//            System.out.println("✅ Registration successful!");
//        } else {
//            System.out.println("❌ Registration failed!");
//        }
//    }
//
//    private static void login() {
//        System.out.print("Enter email: ");
//        String email = sc.nextLine();
//        System.out.print("Enter password: ");
//        String password = sc.nextLine();
//
//        User user = userService.login(email, password);
//        if (user != null) {
//            System.out.println("✅ Welcome, " + user.getName() + "!");
//            afterLoginMenu(user);
//        } else {
//            System.out.println("❌ Invalid credentials!");
//        }
//    }
//
//    private static void afterLoginMenu(User user) {
//        while (true) {
//            System.out.println("\n--- Menu ---");
//            System.out.println("1. Browse Products");
//            System.out.println("2. Place Order");
//            System.out.println("3. Logout");
//
//            int choice = sc.nextInt();
//            sc.nextLine();
//
//            switch (choice) {
//                case 1 -> browseProducts();
//                case 2 -> placeOrder(user);
//                case 3 -> { return; }
//                default -> System.out.println("Invalid choice!");
//            }
//        }
//    }
//
//    private static void browseProducts() {
//        List<Product> products = productService.listProducts();
//        System.out.println("\nAvailable Products:");
//        for (Product p : products) {
//            System.out.println(p.getId() + ". " + p.getName() + " - Rs." + p.getPrice() + " (Stock: " + p.getStock() + ")");
//        }
//    }
//
//    private static void placeOrder(User user) {
//        System.out.print("Enter total amount: ");
//        double amount = sc.nextDouble();
//        sc.nextLine();
//
//        Order order = new Order(0, user.getId(), amount, "Processing");
//        if (orderService.placeOrder(order)) {
//            System.out.println("✅ Order placed successfully!");
//        } else {
//            System.out.println("❌ Failed to place order!");
//        }
//    }
//}

package com.grocery;

import com.grocery.model.*;
import com.grocery.service.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static UserService userService = new UserService();
    private static ProductService productService = new ProductService();
    private static OrderService orderService = new OrderService();
    private static CartService cartService = new CartService();
    private static WishlistService wishlistService = new WishlistService();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Online Grocery Store ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> register();
                case 2 -> login();
                case 3 -> System.exit(0);
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void register() {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter phone: ");
        String phone = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        User user = new User(0, name, email, phone, password);
        if (userService.register(user)) {
            System.out.println("✅ Registration successful!");
        } else {
            System.out.println("❌ Registration failed!");
        }
    }

    private static void login() {
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        User user = userService.login(email, password);
        if (user != null) {
            System.out.println("✅ Welcome, " + user.getName() + "!");
            afterLoginMenu(user);
        } else {
            System.out.println("❌ Invalid credentials!");
        }
    }

    private static void afterLoginMenu(User user) {
        Cart cart = cartService.loadCart(user.getId());
        Wishlist wishlist = wishlistService.loadWishlist(user.getId());

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Browse Products");
            System.out.println("2. View Cart");
            System.out.println("3. View Wishlist");
            System.out.println("4. Place Order");
            System.out.println("5. Logout");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> browseProducts(cart, wishlist);
                case 2 -> manageCart(cart, user);
                case 3 -> manageWishlist(wishlist);
                case 4 -> placeOrder(user, cart);
                case 5 -> {
                    cartService.saveCart(cart);         // Save cart before logout
                    wishlistService.saveWishlist(wishlist); // Save wishlist
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void browseProducts(Cart cart, Wishlist wishlist) {
        List<Product> products = productService.listProducts();
        System.out.println("\nAvailable Products:");
        for (Product p : products) {
            System.out.println(p.getId() + ". " + p.getName() + " - Rs." + p.getPrice() + " (Stock: " + p.getStock() + ")");
        }

        System.out.print("Enter product ID to add to Cart/Wishlist (0 to go back): ");
        int pid = sc.nextInt();
        sc.nextLine();
        if (pid == 0) return;

        System.out.println("1. Add to Cart");
        System.out.println("2. Add to Wishlist");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.print("Enter quantity: ");
                int qty = sc.nextInt();
                sc.nextLine();
                cart.addItem(pid, qty);
                System.out.println("✅ Added to cart!");
            }
            case 2 -> {
                wishlist.addItem(pid);
                System.out.println("✅ Added to wishlist!");
            }
            default -> System.out.println("Invalid choice!");
        }
    }

    private static void manageCart(Cart cart, User user) {
        System.out.println("\n--- Your Cart ---");
        if (cart.getItems().isEmpty()) {
            System.out.println("Cart is empty!");
            return;
        }

        cart.getItems().forEach((pid, qty) -> {
            Product p = productService.getProductById(pid);
            if (p != null) {
                System.out.println(p.getId() + ". " + p.getName() + " - Rs." + p.getPrice() + " x " + qty);
            }
        });

        System.out.println("1. Update Quantity");
        System.out.println("2. Remove Item");
        System.out.println("3. Clear Cart");
        System.out.println("4. Back");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.print("Enter Product ID: ");
                int pid = sc.nextInt();
                sc.nextLine();
                System.out.print("Enter new Quantity: ");
                int qty = sc.nextInt();
                sc.nextLine();
                cart.updateItemQuantity(pid, qty);
            }
            case 2 -> {
                System.out.print("Enter Product ID to remove: ");
                int pid = sc.nextInt();
                sc.nextLine();
                cart.removeItem(pid);
            }
            case 3 -> cart.clearCart();
            case 4 -> { return; }
            default -> System.out.println("Invalid choice!");
        }
    }

    private static void manageWishlist(Wishlist wishlist) {
        System.out.println("\n--- Your Wishlist ---");
        if (wishlist.getItems().isEmpty()) {
            System.out.println("Wishlist is empty!");
            return;
        }

        wishlist.getItems().forEach(pid -> {
            Product p = productService.getProductById(pid);
            if (p != null) {
                System.out.println(p.getId() + ". " + p.getName() + " - Rs." + p.getPrice());
            }
        });

        System.out.println("1. Remove Item");
        System.out.println("2. Back");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            System.out.print("Enter Product ID to remove: ");
            int pid = sc.nextInt();
            sc.nextLine();
            wishlist.removeItem(pid);
        }
    }

    private static void placeOrder(User user, Cart cart) {
        if (cart.getItems().isEmpty()) {
            System.out.println("Cart is empty! Add items before placing order.");
            return;
        }

        double totalAmount = cart.getItems().entrySet().stream()
                .mapToDouble(entry -> {
                    Product p = productService.getProductById(entry.getKey());
                    return (p != null ? p.getPrice() * entry.getValue() : 0);
                })
                .sum();

        Order order = new Order(0, user.getId(), totalAmount, "Processing");
        if (orderService.placeOrder(order)) {
            System.out.println("✅ Order placed successfully! Total: Rs." + totalAmount);
            cart.clearCart();
        } else {
            System.out.println("❌ Failed to place order!");
        }
    }
}
