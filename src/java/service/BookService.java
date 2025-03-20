package service;

import dal.bookDao.BookDAO;
import model.book.Book;
import service.interfaces.IBookService;
import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import model.review.Review;

public class BookService implements IBookService {

    private final BookDAO bookDAO;

    public BookService() {
        this.bookDAO = new BookDAO();
    }

    @Override
    public List<Book> searchBooks(String keyword) {

        return bookDAO.searchBooks(keyword);
    }

    @Override
    public List<Book> selectAllBooks() {
        return bookDAO.selectAllBooks();
    }

    @Override
    public Book getBookById(int id) {
        return bookDAO.selectBook(id);
    }

    @Override
    public boolean addBook(String title, String author, double price, String description, int stock, String publishDate, String imgUrl, int categoryId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean updateBook(int id, String title, String author, double price, String description, int stock, String publishDate, String imgUrl, int categoryId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean deleteBook(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Book> getTopViewedBooks(int userId) {
        return bookDAO.getTopViewedBooks(userId);
    }

    @Override
    public List<Book> searchBooks(String keyword, String sortType, Integer categoryId, Double minPrice, Double maxPrice) {

        return bookDAO.searchBooks(keyword, sortType, categoryId, minPrice, maxPrice);
    }

    // Phương thức lấy sản phẩm gợi ý khi không có kết quả tìm kiếm
    public List<Book> getSuggestedBooks() {
        List<Book> suggestedBooks = new ArrayList<>();
        try {
            // Replace this with your actual logic, e.g. fetching random books or popular ones
            suggestedBooks = bookDAO.findRandomBooks(); // or any other method to get suggested books
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suggestedBooks;
    }

    public List<Book> getRelatedBooks(int categoryId, int currentBookId) {
        return bookDAO.getRelatedBooks(categoryId, currentBookId);
    }
}
