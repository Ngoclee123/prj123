package service.interfaces;

import model.order.Cart;

public interface IOrderService {
    int createOrder(int userId, double finalPrice, String address, String phone, String shippingMethod, String paymentMethod);
    void addOrderDetails(int orderId, Cart cart, double finalPrice);
}