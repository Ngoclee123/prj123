package model.review;

import java.sql.Timestamp;

public class Review {
    private int id;
    private int userId;
    private int bookId;
    private int rating;
    private String comment;
    private Timestamp reviewDate;
    private String fullname;

    // Constructor mặc định
    public Review() {
    }

    // Constructor không có fullname
    public Review(int id, int userId, int bookId, int rating, String comment, Timestamp reviewDate) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    // Constructor có fullname
    public Review(int id, int userId, int bookId, int rating, String comment, Timestamp reviewDate, String fullname) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
        this.fullname = fullname;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Timestamp reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}