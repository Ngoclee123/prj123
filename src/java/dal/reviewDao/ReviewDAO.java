package dal.reviewDao;

import dal.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.review.Review;

public class ReviewDAO implements IReviewDAO {
    private static final String INSERT_REVIEW = "INSERT INTO Reviews (user_id, book_id, rating, comment) VALUES (?, ?, ?, ?)";
    private static final String SELECT_REVIEW_BY_ID = "SELECT r.*, a.FullName FROM Reviews r JOIN Account a ON r.user_id = a.userid WHERE r.id = ?";
    private static final String SELECT_ALL_REVIEWS = "SELECT r.*, a.FullName FROM Reviews r JOIN Account a ON r.user_id = a.userid";
    private static final String DELETE_REVIEW = "DELETE FROM Reviews WHERE id = ?";
    private static final String UPDATE_REVIEW = "UPDATE Reviews SET user_id = ?, book_id = ?, rating = ?, comment = ? WHERE id = ?";
    private static final String SEARCH_REVIEWS_BY_COMMENT = "SELECT r.*, a.FullName FROM Reviews r JOIN Account a ON r.user_id = a.userid WHERE r.comment LIKE ?";
    private static final String SELECT_REVIEWS_BY_BOOK_ID = "SELECT r.*, a.FullName FROM Reviews r JOIN Account a ON r.user_id = a.userid WHERE r.book_id = ?";
    //private static final String SELECT_REVIEWS_BY_BOOK_ID
    private final DBContext dbContext;

    public ReviewDAO() {
        this.dbContext = new DBContext();
    }

    @Override
    public void insertReview(Review review) throws SQLException {
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_REVIEW)) {
            stmt.setInt(1, review.getUserId());
            stmt.setInt(2, review.getBookId());
            stmt.setInt(3, review.getRating());
            stmt.setString(4, review.getComment());
            stmt.executeUpdate();
        }
    }

    @Override
    public Review selectReview(int id) {
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_REVIEW_BY_ID)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Review(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("book_id"),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getTimestamp("review_date"),
                        rs.getString("FullName")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Review> selectAllReviews() {
        List<Review> reviews = new ArrayList<>();
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_REVIEWS);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                reviews.add(new Review(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("book_id"),
                    rs.getInt("rating"),
                    rs.getString("comment"),
                    rs.getTimestamp("review_date"),
                    rs.getString("FullName")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    @Override
    public boolean deleteReview(int id) throws SQLException {
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_REVIEW)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateReview(Review review) throws SQLException {
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_REVIEW)) {
            stmt.setInt(1, review.getUserId());
            stmt.setInt(2, review.getBookId());
            stmt.setInt(3, review.getRating());
            stmt.setString(4, review.getComment());
            stmt.setInt(5, review.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public List<Review> searchReviews(String keyword) {
        List<Review> reviews = new ArrayList<>();
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SEARCH_REVIEWS_BY_COMMENT)) {
            stmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reviews.add(new Review(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("book_id"),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getTimestamp("review_date"),
                        rs.getString("FullName")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    @Override
    public List<Review> getReviewsByBookId(int bookId) {
        List<Review> reviews = new ArrayList<>();
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_REVIEWS_BY_BOOK_ID)) {
            stmt.setInt(1, bookId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reviews.add(new Review(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("book_id"),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getTimestamp("review_date"),
                        rs.getString("FullName")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}