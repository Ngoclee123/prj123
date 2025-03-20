package dal.viewDao;

import dal.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import model.book.Book;

public class ViewCounterDAO {
    public void updateViewCount(int userId, int bookId) {
        String sql = "MERGE INTO BookViews AS target " +
                     "USING (SELECT ? AS userId, ? AS bookId) AS source " +
                     "ON target.userId = source.userId AND target.bookId = source.bookId " +
                     "WHEN MATCHED THEN " +
                     "    UPDATE SET viewCount = target.viewCount + 1 " +
                     "WHEN NOT MATCHED THEN " +
                     "    INSERT (userId, bookId, viewCount) VALUES (?, ?, 1);";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            stmt.setInt(3, userId);
            stmt.setInt(4, bookId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getBookViewCount(int bookId) throws SQLException {
        String sql = "SELECT SUM(viewCount) FROM BookViews WHERE bookId = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public Map<Book, Integer> getUserClicks(int userId) {
        Map<Book, Integer> clickMap = new HashMap<>();
        String sql = "SELECT b.id, b.title, b.author, b.price, b.description, b.stock, b.publish_date, b.img_url, b.category_id, b.status, v.viewCount " +
                     "FROM BookViews v " +
                     "JOIN Book b ON v.bookId = b.id " +
                     "WHERE v.userId = ?";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
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
                    rs.getBoolean("status")
                );
                int viewCount = rs.getInt("viewCount");
                clickMap.put(book, viewCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clickMap;
    }
}
