package service;

import dal.reviewDao.IReviewDAO;
import dal.reviewDao.ReviewDAO;
import model.review.Review;
import java.util.List;
import java.util.ArrayList;

public class ReviewService {
    private static final int DEFAULT_PAGE_SIZE = 5; // Số review mặc định mỗi trang
    private final IReviewDAO reviewDAO;

    public ReviewService() {
        this.reviewDAO = new ReviewDAO();
    }

    // Lấy tất cả review của một sách
    public List<Review> getReviewsByBookId(int bookId) {
        List<Review> reviews = reviewDAO.getReviewsByBookId(bookId);
        System.out.println("Reviews for bookId " + bookId + ": " + reviews);
        for (Review review : reviews) {
            System.out.println("Comment: " + review.getComment());
        }
        return reviews;
    }

    // Lấy review phân trang
    public List<Review> getReviewsByBookId(int bookId, int pageSize, int pageNumber) {
        List<Review> allReviews = reviewDAO.getReviewsByBookId(bookId); // Lấy tất cả review
        int totalReviews = allReviews.size();
        int start = (pageNumber - 1) * pageSize;
        int end = Math.min(start + pageSize, totalReviews);

        if (start >= totalReviews) {
            return new ArrayList<>();
        }
        return allReviews.subList(start, end);
    }

    // Lấy tổng số trang
    public int getTotalReviewsByBookId(int bookId) {
        List<Review> allReviews = reviewDAO.getReviewsByBookId(bookId);
        return allReviews.size();
    }

    // Tính tổng số trang dựa trên pageSize
    public int getTotalPages(int bookId, int pageSize) {
        int totalReviews = getTotalReviewsByBookId(bookId);
        return (int) Math.ceil((double) totalReviews / pageSize);
    }

    public boolean addReview(int userId, int bookId, int rating, String comment) {
        if (rating < 1 || rating > 5 || comment == null || comment.trim().isEmpty()) {
            return false;
        }
        try {
            Review review = new Review(0, userId, bookId, rating, comment, null);
            reviewDAO.insertReview(review);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}