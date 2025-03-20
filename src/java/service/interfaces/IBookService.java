package service.interfaces;

import model.book.Book;
import java.util.List;

public interface IBookService {
    List<Book> searchBooks(String keyword);
    List<Book> selectAllBooks();
    Book getBookById(int id);
    boolean addBook(String title, String author, double price, String description, int stock, String publishDate, String imgUrl, int categoryId);
    boolean updateBook(int id, String title, String author, double price, String description, int stock, String publishDate, String imgUrl, int categoryId);
    boolean deleteBook(int id);
    List<Book> searchBooks(String keyword,String sortType, Integer categoryId,Double minPrice, Double maxPrice);
}
