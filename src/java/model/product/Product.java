package model.product;

import java.time.LocalDate;

public class Product {
    private int id;
    private String title;
    private String author;
    private double price;
    private String description;
    private int stock;
    private LocalDate publishDate;  // Sử dụng LocalDate thay cho java.util.Date
    private String imgUrl;
    private Integer categoryId;
    private boolean status;

    // Constructor
    public Product(int id, String title, String author, double price, String description, int stock, LocalDate publishDate, String imgUrl, Integer categoryId, boolean status) {
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
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public int getStock() { return stock; }
    public LocalDate getPublishDate() { return publishDate; }
    public String getImgUrl() { return imgUrl; }
    public Integer getCategoryId() { return categoryId; }
    public boolean getStatus() { return status; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setPrice(double price) { this.price = price; }
    public void setDescription(String description) { this.description = description; }
    public void setStock(int stock) { this.stock = stock; }
    public void setPublishDate(LocalDate publishDate) { this.publishDate = publishDate; }
    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
    public void setStatus(boolean status) { this.status = status; }

    // Optional: Override toString() for easier debugging and logging
    @Override
    public String toString() {
        return "Book{id=" + id + ", title='" + title + "', author='" + author + "', price=" + price + ", description='" + description + "', stock=" + stock + ", publishDate=" + publishDate + ", imgUrl='" + imgUrl + "', categoryId=" + categoryId + ", status=" + status + "}";
    }
}
