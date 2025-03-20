package model.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.OrderDetails.OrderDetails;

public class Order {
    private int id;
    private int userId;
    private Date orderDate;
    private double totalPrice;
    private String status;
    private String address;
    private String phone;
    private String shippingMethod;
    private String paymentMethod;
    
    // List to store order details
    private List<OrderDetails> orderDetailsList;

    public Order() {
        this.orderDetailsList = new ArrayList<>();  // Initialize the list
    }

    public Order(int id, int userId, double totalPrice, String status, 
                 String address, String phone, String shippingMethod, String paymentMethod) {
        this();
        this.id = id;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.status = status;
        this.address = address;
        this.phone = phone;
        this.shippingMethod = shippingMethod;
        this.paymentMethod = paymentMethod;
    }

    // Getter and Setter methods

    public List<OrderDetails> getOrderDetailsList() {
        return orderDetailsList;
    }

    public void addOrderDetails(OrderDetails orderDetails) {
        this.orderDetailsList.add(orderDetails);  // Add order detail to the list
    }

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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    // Other getter and setter methods...

    @Override
    public String toString() {
        return "Order { id=" + id + ", userId=" + userId + 
               ", orderDate=" + orderDate + ", totalPrice=" + totalPrice + 
               ", status=" + status + ", address=" + address + 
               ", phone=" + phone + ", shippingMethod=" + shippingMethod + 
               ", paymentMethod=" + paymentMethod + " }";
    }
}
