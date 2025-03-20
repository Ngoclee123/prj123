package model.order;

import model.book.Book;



public class CartItem  {
    private Book book;
    private int quantity;

    public CartItem(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }
 public void increaseQuantity(int amount) {
        this.quantity += amount;
    }

    // Getters and Setters
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
    public double getTotalPrice() {
        return this.book.getPrice() * this.quantity;
    }
    
   
}
