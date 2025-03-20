package service;

import model.book.Book;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import dal.bookDao.BookDAO;
import model.bookCategory.BookCategory;

public class Newpage {
    
    private static final int PAGE_SIZE = 9; // Số sách hiển thị trên mỗi trang
     BookDAO bookDAO =new BookDAO();
   public List<Book> getBooksByPage(int page, String sortType, Integer categoryId, Double minPrice, Double maxPrice) {
    List<Book> allBooks = bookDAO.selectAllBooks(); // Lấy tất cả sách từ database

    // Lọc theo thể loại nếu có
    if (categoryId != null) {
        allBooks.removeIf(book -> book.getCategoryId() != categoryId);
    }

    // Lọc theo khoảng giá
    if (minPrice != null) {
        allBooks.removeIf(book -> book.getPrice() < minPrice);
    }
    if (maxPrice != null) {
        allBooks.removeIf(book -> book.getPrice() > maxPrice);
    }

    // Sắp xếp theo giá nếu có yêu cầu
    if ("asc".equals(sortType)) {
        allBooks.sort(Comparator.comparingDouble(Book::getPrice)); // Giá tăng dần
    } else if ("desc".equals(sortType)) {
        allBooks.sort(Comparator.comparingDouble(Book::getPrice).reversed()); // Giá giảm dần
    }

    // Phân trang
    int totalBooks = allBooks.size();
    int start = (page - 1) * PAGE_SIZE;
    int end = Math.min(start + PAGE_SIZE, totalBooks);

    if (start >= totalBooks) {
        return new ArrayList<>();
    }
    return allBooks.subList(start, end);
}

public int getTotalPages(Integer categoryId, Double minPrice, Double maxPrice) {
    List<Book> allBooks = bookDAO.selectAllBooks();

    // Lọc theo thể loại
    if (categoryId != null) {
        allBooks.removeIf(book -> book.getCategoryId() != categoryId);
    }

    // Lọc theo khoảng giá
    if (minPrice != null) {
        allBooks.removeIf(book -> book.getPrice() < minPrice);
    }
    if (maxPrice != null) {
        allBooks.removeIf(book -> book.getPrice() > maxPrice);
    }

    return (int) Math.ceil((double) allBooks.size() / PAGE_SIZE);
}


    public List<BookCategory> getAllCategories() {
        return BookDAO.getAllCategories(); // Lấy danh sách tất cả thể loại sách từ database
    }
}