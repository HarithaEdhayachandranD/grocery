import java.util.*;

// --- Product Class ---
class Product {
    private int id;
    private String name;
    private double price;
    private int stock;

    public Product(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    public void reduceStock(int quantity) {
        if (quantity <= stock) {
            stock -= quantity;
        }
    }

    @Override
    public String toString() {
        return id + ". " + name + " - ₹" + price + " (Stock: " + stock + ")";
    }
}

// --- CartItem Class ---
class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getTotalPrice() { return product.getPrice() * quantity; }

    @Override
    public String toString() {
        return product.getName() + " x " + quantity + " = ₹" + getTotalPrice();
    }
}

// --- Cart Class ---
class Cart {
    private List<CartItem> items = new ArrayList<>();

    public void addToCart(Product product, int quantity) {
        if (quantity > product.getStock()) {
            System.out.println("❌ Not enough stock for " + product.getName());
            return;
        }
        items.add(new CartItem(product, quantity));
        product.reduceStock(quantity);
        System.out.println("✅ Added " + quantity + " " + product.getName() + " to cart.");
    }

    public void viewCart() {
        if (items.isEmpty()) {
            System.out.println("🛒 Cart is empty.");
            return;
        }
        System.out.println("🛒 Cart Items:");
        for (CartItem item : items) {
            System.out.println(" - " + item);
        }
    }

    public double checkout() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        items.clear();
        return total;
    }
}

// --- Main Application ---
public class SwizzleApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Sample Products (later from DB)
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Rice 5kg", 250.0, 10));
        products.add(new Product(2, "Milk 1L", 45.0, 20));
        products.add(new Product(3, "Bread", 30.0, 15));
        products.add(new Product(4, "Eggs (12 pack)", 70.0, 8));

        Cart cart = new Cart();
        int choice;

        System.out.println("=== Welcome to Swizzle - Intelligent Grocery Platform ===");

        do {
            System.out.println("View Menu : ");
            System.out.println("\nMenu:");
            System.out.println("1. View Products");
            System.out.println("2. Add to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Checkout");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\nAvailable Products:");
                    for (Product p : products) {
                        System.out.println(p);
                    }
                    break;

                case 2:
                    System.out.print("Enter product ID: ");
                    int id = sc.nextInt();
                    System.out.print("Enter quantity: ");
                    int qty = sc.nextInt();

                    Product selected = null;
                    for (Product p : products) {
                        if (p.getId() == id) {
                            selected = p;
                            break;
                        }
                    }
                    if (selected != null) {
                        cart.addToCart(selected, qty);
                    } else {
                        System.out.println("❌ Invalid product ID.");
                    }
                    break;

                case 3:
                    cart.viewCart();
                    break;

                case 4:
                    double total = cart.checkout();
                    System.out.println("✅ Order placed! Total bill: ₹" + total);
                    break;

                case 0:
                    System.out.println("👋 Thank you for using Swizzle!");
                    break;

                default:
                    System.out.println("❌ Invalid choice.");
            }

        } while (choice != 0);

        sc.close();
    }
}
