/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.bookDao;

import dal.DBContext;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.book.Book;
import model.bookCategory.BookCategory;

/**
 *
 * @author ASUS
 */
public class BookDAO implements IBookDAO{
    
 private static final String INSERT_PRODUCT = "INSERT INTO Product (name, price, description, stock) VALUES (?, ?, ?, ?)";
    private static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM Product WHERE id = ?";
    private static final String UPDATE_PRODUCT = "UPDATE Product SET name = ?, price = ?, description = ?, stock = ? WHERE id = ?";
    private static final String SELECT_ALL_PRODUCT = "select * from Product";    
private static final String SELECT_RELATED_BOOKS = "SELECT TOP 6 b.*, bc.name AS categoryName FROM Book b LEFT JOIN BookCategory bc ON b.category_id = bc.id WHERE b.category_id = ? AND b.id != ? AND b.status = 1";


@Override
public void insertBook(Book book) throws SQLException {
    String sql = "INSERT INTO Book (title, author, price, description, stock, publish_date, img_url, category_id, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = DBContext.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, book.getTitle());
        stmt.setString(2, book.getAuthor());
        stmt.setDouble(3, book.getPrice());
        stmt.setString(4, book.getDescription());
        stmt.setInt(5, book.getStock());
        stmt.setDate(6, new java.sql.Date(book.getPublishDate().getTime()));
        stmt.setString(7, book.getImgUrl());
        stmt.setInt(8, book.getCategoryId());
        stmt.setBoolean(9, book.isStatus());

        stmt.executeUpdate();
    }
}


@Override
    public Book selectBook(int id) {
        String sql = "SELECT b.*, bc.name AS categoryName " +
                     "FROM Book b " +
                     "LEFT JOIN BookCategory bc ON b.category_id = bc.id " +
                     "WHERE b.id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getInt("stock"),
                        rs.getDate("publish_date"),
                        rs.getString("img_url"),
                        rs.getInt("category_id"),
                        rs.getBoolean("status"),
                        rs.getString("categoryName") // Thêm tên thể loại
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Nếu không tìm thấy sách, trả về null
    }


    @Override
   
       public List<Book> selectAllBooks() {
    List<Book> books = new ArrayList<>();
    String SELECT_ALL_BOOKS = "SELECT id, title, author, price, description, stock, publish_date, img_url, category_id FROM Book WHERE status = 1";

    try (Connection conn = DBContext.getConnection();
         PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_BOOKS);
         ResultSet rs = preparedStatement.executeQuery()) {

        while (rs.next()) {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String author = rs.getString("author");
            double price = rs.getDouble("price");
            String description = rs.getString("description");
            int stock = rs.getInt("stock");
            Date publishDate = rs.getDate("publish_date");
            String imgUrl = rs.getString("img_url");
            int categoryId = rs.getInt("category_id");

            books.add(new Book(id, title, author, price, description, stock, publishDate, imgUrl, categoryId,true));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return books;
}



   @Override
public boolean deleteBook(int id) throws SQLException {
    String sql = "DELETE FROM Book WHERE id = ?";

    try (Connection conn = DBContext.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);
        return stmt.executeUpdate() > 0; // Nếu xóa thành công, trả về true
    }
}


   @Override
public boolean updateBook(Book book) throws SQLException {
    String sql = "UPDATE Book SET title = ?, author = ?, price = ?, description = ?, stock = ?, publish_date = ?, img_url = ?, category_id = ?, status = ? WHERE id = ?";

    try (Connection conn = DBContext.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, book.getTitle());
        stmt.setString(2, book.getAuthor());
        stmt.setDouble(3, book.getPrice());
        stmt.setString(4, book.getDescription());
        stmt.setInt(5, book.getStock());
        stmt.setDate(6, new java.sql.Date(book.getPublishDate().getTime()));
        stmt.setString(7, book.getImgUrl());
        stmt.setInt(8, book.getCategoryId());
        stmt.setBoolean(9, book.isStatus());
        stmt.setInt(10, book.getId());

        return stmt.executeUpdate() > 0;
    }
}


    @Override
public List<Book> searchBooks(String keyword) {
    List<Book> booksSearchList = new ArrayList<>();
    String sql = "SELECT * FROM Book WHERE title LIKE ?";

    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String author = rs.getString("author");
            double price = rs.getDouble("price");
            String description = rs.getString("description");
            int stock = rs.getInt("stock");
            Date publishDate = rs.getDate("publish_date");
            String imgUrl = rs.getString("img_url");
            int categoryId = rs.getInt("category_id");
            boolean status = rs.getBoolean("status");

            booksSearchList.add(new Book(id, title, author, price, description, stock, publishDate, imgUrl, categoryId, status));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return booksSearchList;
}


   public static List<Book> getTopViewedBooks(int userId) {
    List<Book> books = new ArrayList<>();

    // Truy vấn 3 cuốn sách có lượt xem cao nhất của user
    String query = """
                   SELECT TOP 3 b.id, b.title, b.author, b.price, 
                                b.description, b.stock, b.publish_date, 
                                b.img_url, b.category_id, b.status
                   FROM Book b
                   JOIN BookViews bv ON b.id = bv.bookId
                   WHERE bv.userId = ?
                   ORDER BY bv.viewCount DESC""";

    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

        ps.setInt(1, userId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                books.add(new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getDouble("price"),
                    rs.getString("description"),
                    rs.getInt("stock"),
                    rs.getDate("publish_date"),
                    rs.getString("img_url"),
                    rs.getInt("category_id"),
                    rs.getBoolean("status")
                ));
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return books;
}
   
 @Override
   public List<Book> searchBooks(String keyword, String sortType, Integer categoryId, Double minPrice, Double maxPrice) {
    List<Book> booksSearchList = new ArrayList<>();
    StringBuilder sql = new StringBuilder("SELECT * FROM Book WHERE title LIKE ?");

    // Nếu có lọc theo thể loại
    if (categoryId != null) {
        sql.append(" AND category_id = ").append(categoryId);
    }

    // Nếu có lọc theo khoảng giá
    if (minPrice != null) {
        sql.append(" AND price >= ").append(minPrice);
    }
    if (maxPrice != null) {
        sql.append(" AND price <= ").append(maxPrice);
    }

    // Nếu có sắp xếp theo giá
    if ("asc".equals(sortType)) {
        sql.append(" ORDER BY price ASC");
    } else if ("desc".equals(sortType)) {
        sql.append(" ORDER BY price DESC");
    }

    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql.toString())) {

        ps.setString(1, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String author = rs.getString("author");
            double price = rs.getDouble("price");
            String description = rs.getString("description");
            int stock = rs.getInt("stock");
            Date publishDate = rs.getDate("publish_date");
            String imgUrl = rs.getString("img_url");
            int categoryIdDB = rs.getInt("category_id");
            boolean status = rs.getBoolean("status");

            booksSearchList.add(new Book(id, title, author, price, description, stock, publishDate, imgUrl, categoryIdDB, status));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return booksSearchList;
}





  public static List<BookCategory> getAllCategories() {
        List<BookCategory> categories = new ArrayList<>();
        String sql = "SELECT id, name FROM BookCategory";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                BookCategory category = new BookCategory();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }
   
  
  //
  public List<Book> getSuggestedBooks() {
        List<Book> suggestedBooks = new ArrayList<>();

        // Truy vấn để lấy một số sản phẩm nổi bật hoặc ngẫu nhiên
        String sql = "SELECT * FROM Book WHERE status = 1 LIMIT 5";  // Giới hạn 5 sản phẩm gợi ý

        try (Connection conn = DBContext.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                int stock = rs.getInt("stock");
                Date publishDate = rs.getDate("publish_date");
                String imgUrl = rs.getString("img_url");
                int categoryId = rs.getInt("category_id");
                boolean status = rs.getBoolean("status");

                suggestedBooks.add(new Book(id, title, author, price, description, stock, publishDate, imgUrl, categoryId, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return suggestedBooks;
    }
  
  
  //
     public List<Book> findRandomBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM Book ORDER BY NEWID() OFFSET 0 ROWS FETCH NEXT 3 ROWS ONLY";  // Use NEWID() for random in SQL Server
        
        // Get connection from DBContext
        try (Connection connection = DBContext.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Loop through result set and populate book list
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setPrice(rs.getDouble("price"));
                book.setImgUrl(rs.getString("img_url"));
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching random books: " + e.getMessage());
            throw e;  // Rethrow exception for higher-level handling
        }

        return books;
    }
     
     public List<Book> getRelatedBooks(int categoryId, int currentBookId) {
        List<Book> relatedBooks = new ArrayList<>();
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_RELATED_BOOKS)) {
            stmt.setInt(1, categoryId);
            stmt.setInt(2, currentBookId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getInt("stock"),
                        rs.getDate("publish_date"),
                        rs.getString("img_url"),
                        rs.getInt("category_id"),
                        rs.getBoolean("status"),
                        rs.getString("categoryName")
                    );
                    relatedBooks.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relatedBooks;
    }
}
    



