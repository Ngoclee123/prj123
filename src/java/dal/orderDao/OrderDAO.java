package dal.orderDao;

import model.order.Order;
import dal.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.OrderDetails.OrderDetails;

public class OrderDAO implements IOrderDao {
    private static final String SELECT_ORDER_BY_ID = "SELECT * FROM Orders WHERE id = ?";
    private static final String INSERT_ORDER = 
        "INSERT INTO Orders (user_id, total_price, status, address, phone, shipping_method, payment_method) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_ORDERDETAIL = 
        "INSERT INTO OrderDetails (order_id, book_id, quantity, price) VALUES (?, ?, ?, ?)";
    
    private static final String SELECT_ORDER_DETAILS_BY_ORDER_ID_AND_USER_ID = 
        "SELECT * FROM OrderDetails " +
        "JOIN Orders ON OrderDetails.order_id = Orders.id " +
        "WHERE OrderDetails.order_id = ? AND Orders.user_id = ?";
    
    

    public OrderDAO() {}
    
    
    
    
    @Override
    public Order getOrderById(int id) {
        Order order = null;
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ORDER_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                order = new Order(
                    rs.getInt("id"), 
                    rs.getInt("user_id"), 
                    rs.getDouble("total_price"), 
                    rs.getString("status"),
                    rs.getString("address"),
                    rs.getString("phone"),
                    rs.getString("shipping_method"),
                    rs.getString("payment_method")
                );
            } else {
                System.out.println("❌ Order not found!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return order;
    }

    @Override
    public void insertOrder(Order orderObj) throws SQLException {
        // Code to insert order
    }

    @Override
    public List<Order> selectAllOrders() throws SQLException {
        return null; // Code to select all orders
    }

    @Override
    public boolean deleteOrder(int id) throws SQLException {
        return false; // Code to delete order
    }

    @Override
    public boolean updateOrder(Order orderObj) throws SQLException {
        return false; // Code to update order
    }

    public int createOrder(Order order) {
        int orderId = -1;
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) { 

            // Gán giá trị vào SQL
            stmt.setInt(1, order.getUserId());
            stmt.setDouble(2, order.getTotalPrice());
            stmt.setString(3, order.getStatus());
            stmt.setString(4, order.getAddress());
            stmt.setString(5, order.getPhone());
            stmt.setString(6, order.getShippingMethod());
            stmt.setString(7, order.getPaymentMethod());

            // Thực thi INSERT
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
throw new SQLException("❌ Order creation failed, no rows affected.");
            }

            // Lấy ID của đơn hàng mới tạo
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    orderId = rs.getInt(1);
                    System.out.println("✅ Order created successfully, ID: " + orderId);
                } else {
                    System.out.println("❌ Failed to retrieve order ID.");
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ SQL error when creating order: " + e.getMessage());
            e.printStackTrace();
        }
        return orderId;
    }

    public void addOrderDetail(int orderId, int bookId, int quantity, Double price) {
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_ORDERDETAIL)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, bookId);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, price);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ SQL error when adding order detail: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
     public OrderDetails getOrderDetailsByOrderIdAndUserId(int orderId, int userId) {
        OrderDetails orderDetails = null;
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ORDER_DETAILS_BY_ORDER_ID_AND_USER_ID)) {
            ps.setInt(1, orderId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Khởi tạo đối tượng OrderDetails và trả về
                orderDetails = new OrderDetails(
                    rs.getInt("order_id"),
                    rs.getInt("book_id"),
                    rs.getInt("quantity"),
                    rs.getDouble("price"),
                    rs.getDouble("subtotal")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return orderDetails;
    }
     
     
     public Integer getLatestOrderIdForUser(int userId) {
    // Query the database to get the most recent order ID for the user
    String sql = "SELECT TOP 1 id \n" +
"FROM Orders \n" +
"WHERE user_id = ? \n" +
"ORDER BY order_date DESC;";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, userId);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

//
public List<Order> getOrdersByUserId(int userId) {
    List<Order> orders = new ArrayList<>();
    String sql = "SELECT o.id AS order_id, o.order_date, o.total_price, o.status, o.address, o.phone, " +
                 "o.shipping_method, o.payment_method, od.book_id, od.quantity, od.price, od.subtotal " +
                 "FROM Orders o " +
                 "JOIN OrderDetails od ON o.id = od.order_id " +
                 "WHERE o.user_id = ? ORDER BY o.order_date DESC"; // Fetch orders by user ID

    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Order order = new Order();
            order.setId(rs.getInt("order_id"));
            order.setOrderDate(rs.getDate("order_date"));
            order.setTotalPrice(rs.getDouble("total_price"));
            order.setStatus(rs.getString("status"));
            order.setAddress(rs.getString("address"));
            order.setPhone(rs.getString("phone"));
            order.setShippingMethod(rs.getString("shipping_method"));
            order.setPaymentMethod(rs.getString("payment_method"));
            
            // Create and set the order details
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setBookId(rs.getInt("book_id"));
            orderDetails.setQuantity(rs.getInt("quantity"));
            orderDetails.setPrice(rs.getDouble("price"));
            orderDetails.setSubtotal(rs.getDouble("subtotal"));
            
            order.addOrderDetails(orderDetails); // Assuming `Order` class has a method to add order details
            orders.add(order);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return orders;  // Return the list of orders
}
     






public boolean updateOrderStatus(Order order) {
    String sql = "UPDATE Orders SET status = ? WHERE id = ?";
    
    try (Connection conn = DBContext.getConnection();
         PreparedStatement st = conn.prepareStatement(sql)) {
        
        st.setString(1, order.getStatus());
        st.setInt(2, order.getId());
        
        int affectedRows = st.executeUpdate();
        if (affectedRows > 0) {
            System.out.println("✅ Order status updated successfully!");
            return true;
        } else {
            System.out.println("⚠️ No order found with ID: " + order.getId());
            return false;
        }
        
    } catch (SQLException ex) {
        System.out.println("❌ SQL error when updating order status: " + ex.getMessage());
        ex.printStackTrace();
        return false;
    }
}

}