package service.interfaces;

import model.book.Book;
import model.order.Cart;

public interface ICartService {
    void addToCart(Cart cart, Book product, int quantity);
    void updateCartItem(Cart cart, int bookId, int quantity);
    void removeCartItem(Cart cart, int bookId);
    double getTotalPrice(Cart cart);
}