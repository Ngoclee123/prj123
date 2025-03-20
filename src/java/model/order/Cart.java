package model.order;

import java.util.HashMap;
import java.util.Map;
import model.book.Book;

public class Cart {
    private Map<Integer, CartItem> items;

    public Cart() {
        this.items = new HashMap<>();
    }

    public void addItem(Book book, int quantity) {
        int bookId = book.getId();
        if (items.containsKey(bookId)) {
            items.get(bookId).increaseQuantity(quantity);
        } else {
            items.put(bookId, new CartItem(book, quantity));
        }
    }

    public void updateItem(int bookId, int quantity) {
        if (items.containsKey(bookId)) {
            if (quantity > 0) {
                items.get(bookId).setQuantity(quantity);
            } else {
                items.remove(bookId);
            }
        }
    }

    public void removeItem(int bookId) {
        items.remove(bookId);
    }

    public void clearCart() {
        items.clear();
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items.values()) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public Map<Integer, CartItem> getItems() {
        return items;
    }
}

//class CartItem {
//    private Product product;
//    private int quantity;
//
//    public CartItem(Product product, int quantity) {
//        this.product = product;
//        this.quantity = quantity;
//    }
//
//    public void increaseQuantity(int amount) {
//        this.quantity += amount;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
//
//    public double getTotalPrice() {
//        return product.getPrice() * quantity;
//    }
//}
