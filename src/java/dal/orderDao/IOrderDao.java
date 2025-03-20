/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.orderDao;

import java.util.List;
import model.order.Order;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public interface IOrderDao {
    Order getOrderById(int id);
    void insertOrder(Order orderObj) throws SQLException;
    List<Order> selectAllOrders() throws SQLException;
    boolean deleteOrder(int id) throws SQLException;
    boolean updateOrder(Order orderObj) throws SQLException;
    
}
