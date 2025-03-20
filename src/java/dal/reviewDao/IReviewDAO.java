package dal.reviewDao;

import java.sql.SQLException;
import java.util.List;
import model.review.Review;

public interface IReviewDAO {
    void insertReview(Review review) throws SQLException;
    Review selectReview(int id);
    List<Review> selectAllReviews();
    boolean deleteReview(int id) throws SQLException;
    boolean updateReview(Review review) throws SQLException;
    List<Review> searchReviews(String keyword);
    List<Review> getReviewsByBookId(int bookId);
    //List<Review> getReviewsByBookId(int bookId, int pageSize, int offset); // Thêm phương thức phân trang
    //int getTotalReviewsByBookId(int bookId); // Thêm phương thức đếm tổng số đánh giá
}