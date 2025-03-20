package model.OrderDayData; // Chỉ dùng "model", không phải "model.OrderDayData"

public class OrderDayData {
    private int orderDay;
    private int orderCount;

    public OrderDayData() {}

    public int getOrderDay() {
        return orderDay;
    }

    public void setOrderDay(int orderDay) {
        this.orderDay = orderDay;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }
}