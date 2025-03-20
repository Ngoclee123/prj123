package service;
import java.sql.SQLException;

import dal.orderDao.OrderDAO;
import model.book.Book;
import model.order.Cart;
import model.order.CartItem;
import model.order.Order;
import service.interfaces.IOrderService;

public class OrderService implements IOrderService {

    private OrderDAO orderDAO;

    public OrderService() {
        this.orderDAO = new OrderDAO();
    }

    @Override
    public int createOrder(int userId, double finalPrice, String address, String phone, String shippingMethod, String paymentMethod) {
        Order order = new Order(0, userId, finalPrice, "Pending", address, phone, shippingMethod, paymentMethod);
        return orderDAO.createOrder(order);
    }

//    // Phương thức mới hỗ trợ lưu thông tin voucher
//    public int createOrder(int userId, double finalPrice, String address, String phone, String shippingMethod, String paymentMethod, String voucherCode, double discountAmount) {
//        Order order = new Order(0, userId, finalPrice, "Pending", address, phone, shippingMethod, paymentMethod);
//        order.setVoucherCode(voucherCode.equals("Không sử dụng") ? null : voucherCode);
//        order.setDiscountAmount(discountAmount);
//        return orderDAO.createOrder(order);
//    }

    @Override
    public void addOrderDetails(int orderId, Cart cart, double finalPrice) {
        double totalPrice = calculateTotalPrice(cart);
        double discountPercent = (totalPrice - finalPrice) / totalPrice;
        System.out.println("Total Price: " + totalPrice);
        System.out.println("Final Price: " + finalPrice);
        for (CartItem item : cart.getItems().values()) {
            Book book = item.getBook();
            int quantity = item.getQuantity();
            double discountedPrice = (discountPercent == 0)
                    ? book.getPrice()
                    : book.getPrice() * (1 - discountPercent);
            orderDAO.addOrderDetail(orderId, book.getId(), quantity, discountedPrice);
        }
    }

    public boolean updateOrderStatus(Order order) throws SQLException {
        return orderDAO.updateOrderStatus(order);
    }

    private double calculateTotalPrice(Cart cart) {
        double total = 0;
        for (CartItem item : cart.getItems().values()) {
            total += item.getQuantity() * item.getBook().getPrice();
        }
        return total;
    }
}
