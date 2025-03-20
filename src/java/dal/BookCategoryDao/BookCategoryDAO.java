package dal.BookCategoryDao;

import dal.DBContext;
import model.bookCategory.BookCategory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookCategoryDAO {

    public List<BookCategory> getAllCategories() {
        List<BookCategory> categories = new ArrayList<>();
        String sql = "SELECT * FROM BookCategory";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                BookCategory category = new BookCategory(
                    rs.getInt("id"),
                    rs.getString("name")
                );
                categories.add(category);
            }
            System.out.println("Đã lấy " + categories.size() + " danh mục từ cơ sở dữ liệu.");
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy danh sách danh mục: " + e.getMessage(), e);
        }
        return categories;
    }
}