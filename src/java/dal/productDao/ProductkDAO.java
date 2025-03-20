package dal.productDao;

import dal.DBContext;
import model.product.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductkDAO {

    public List<Product> getAllBooks() {
        return getBooksByPage(1); // Trang mặc định là 1
    }

    public List<Product> getBooksByPage(int page) {
        List<Product> books = new ArrayList<>();
        int pageSize = 10; // 10 mục mỗi trang
        int offset = (page - 1) * pageSize;
        String sql = "SELECT * FROM Book ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (conn == null) {
                throw new SQLException("Kết nối cơ sở dữ liệu là null");
            }
            stmt.setInt(1, offset);
            stmt.setInt(2, pageSize);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product book = new Product(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getDouble("price"),
                    rs.getString("description"),
                    rs.getInt("stock"),
                    rs.getDate("publish_date") != null ? rs.getDate("publish_date").toLocalDate() : null,
                    rs.getString("img_url"),
                    rs.getInt("category_id"),
                    rs.getInt("status") == 1
                );
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy danh sách sách: " + e.getMessage(), e);
        }
        return books;
    }

    public int getTotalBooks() {
        String sql = "SELECT COUNT(*) FROM Book";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (conn == null) {
                throw new SQLException("Kết nối cơ sở dữ liệu là null");
            }
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi đếm tổng số sách: " + e.getMessage(), e);
        }
    }

    public void addBook(Product book) {
        String sql = "INSERT INTO Book (title, author, price, description, stock, publish_date, img_url, category_id, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (conn == null) {
                throw new SQLException("Kết nối cơ sở dữ liệu là null");
            }
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setDouble(3, book.getPrice());
            stmt.setString(4, book.getDescription());
            stmt.setInt(5, book.getStock());
            stmt.setObject(6, book.getPublishDate() != null ? java.sql.Date.valueOf(book.getPublishDate()) : null);
            stmt.setString(7, book.getImgUrl());
            stmt.setObject(8, book.getCategoryId() != null ? book.getCategoryId() : null);
            stmt.setInt(9, book.getStatus() ? 1 : 0);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thêm sách: " + e.getMessage(), e);
        }
    }

    public void updateBook(Product book) {
        String sql = "UPDATE Book SET title = ?, author = ?, price = ?, description = ?, stock = ?, publish_date = ?, img_url = ?, category_id = ?, status = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (conn == null) {
                throw new SQLException("Kết nối cơ sở dữ liệu là null");
            }
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setDouble(3, book.getPrice());
            stmt.setString(4, book.getDescription());
            stmt.setInt(5, book.getStock());
            stmt.setObject(6, book.getPublishDate() != null ? java.sql.Date.valueOf(book.getPublishDate()) : null);
            stmt.setString(7, book.getImgUrl());
            stmt.setObject(8, book.getCategoryId() != null ? book.getCategoryId() : null);
            stmt.setInt(9, book.getStatus() ? 1 : 0);
            stmt.setInt(10, book.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi cập nhật sách: " + e.getMessage(), e);
        }
    }

    public void toggleBookStatus(int id) {
        String sql = "UPDATE Book SET status = 1 - status WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (conn == null) {
                throw new SQLException("Kết nối cơ sở dữ liệu là null");
            }
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Không thể thay đổi trạng thái sách. ID không tồn tại?");
            }
            System.out.println("Đã thay đổi trạng thái sách ID: " + id);
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thay đổi trạng thái sách: " + e.getMessage(), e);
        }
    }

    public Product getBookById(int id) {
        String sql = "SELECT * FROM Book WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (conn == null) {
                throw new SQLException("Kết nối cơ sở dữ liệu là null");
            }
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Product(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getDouble("price"),
                    rs.getString("description"),
                    rs.getInt("stock"),
                    rs.getDate("publish_date") != null ? rs.getDate("publish_date").toLocalDate() : null,
                    rs.getString("img_url"),
                    rs.getInt("category_id"),
                    rs.getInt("status") == 1
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy sách theo ID: " + e.getMessage(), e);
        }
    }
}