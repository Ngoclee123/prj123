package model.book;

import java.util.Date;

public class Book {

    private int id;
    private String title;
    private String author;
    private double price;
    private String description;
    private int stock;
    private java.sql.Date publishDate;
    private String imgUrl;
    private int categoryId;
    private boolean status;
    private String categoryName; // Thêm thuộc tính để lưu tên thể loại

    // Constructor, Getters, Setters
    public Book() {
    }
     public Book(int id, String title, String author, double price, String description, 
                int stock, Date publishDate, String imgUrl, int categoryId, boolean status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.publishDate = (java.sql.Date) publishDate;
        this.imgUrl = imgUrl;
        this.categoryId = categoryId;
        this.status = status;
    }
     
     
    public Book(int id, String title, String author, double price, String description, int stock,
            java.sql.Date publishDate, String imgUrl, int categoryId, boolean status, String categoryName) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.publishDate = publishDate;
        this.imgUrl = imgUrl;
        this.categoryId = categoryId;
        this.status = status;
        this.categoryName = categoryName;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public java.sql.Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(java.sql.Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
