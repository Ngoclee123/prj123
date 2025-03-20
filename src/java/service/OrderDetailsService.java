//package service;
//
//import dal.DBContext;
//import dal.orderDao.OrderDAO;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//import model.OrderDetails.OrderDetails;
//import model.order.Order;
//
//public class OrderDetailsService {
//    private OrderDAO orderDAO;
//
//    public OrderDetailsService() {
//        this.orderDAO = new OrderDAO();
//    }
//
//    public OrderDetails getOrderDetailsByOrderIdAndUserId(int orderId, int userId) {
//        return orderDAO.getOrderDetailsByOrderIdAndUserId(orderId, userId);
//    }
//
//    public void addOrderDetail(int orderId, int bookId, int quantity, double price) {
//        orderDAO.addOrderDetail(orderId, bookId, quantity, price);
//    }
//
//    public Integer getLatestOrderIdForUser(int userId) {   
//        return orderDAO.getLatestOrderIdForUser(userId);
//    }
//
//    public List<Order> getOrderHistoryForUser(int userId) {
//        return orderDAO.getOrdersByUserId(userId);
//    }
//}


package service;

import dal.DBContext;
import dal.orderDao.OrderDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import model.OrderDetails.OrderDetails;
import model.order.Order;



public class OrderDetailsService {

   private OrderDAO orderDAO;

    public OrderDetailsService() {
        this.orderDAO= new OrderDAO();
    }

    // Lấy chi tiết đơn hàng theo order_id
      public OrderDetails getOrderDetailsByOrderIdAndUserId(int orderId, int userId) {
        // Truy vấn chi tiết đơn hàng từ CSDL bằng cả orderId và userId
        return orderDAO.getOrderDetailsByOrderIdAndUserId(orderId, userId);
    }

    // Thêm chi tiết đơn hàng vào cơ sở dữ liệu
    public void addOrderDetail(int orderId, int bookId, int quantity, double price) {
        orderDAO.addOrderDetail(orderId, bookId, quantity, price);
    }
    public Integer getLatestOrderIdForUsern(int userId) {   
    return orderDAO.getLatestOrderIdForUser(userId);
}
     public List<Order> getOrderHistoryForUser(int userId) {
        return orderDAO.getOrdersByUserId(userId);
    }
}
