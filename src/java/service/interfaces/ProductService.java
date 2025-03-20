package service.interfaces;

import model.product.Product;
import java.util.List;

public interface ProductService {
    void addBook(Product product);
    void updateBook(Product product);
    void toggleBookStatus(int id);
    Product getBookById(int id);
    List<Product> getBooksByPage(int page);
    int getTotalBooks();
}