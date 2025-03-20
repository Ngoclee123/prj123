package controller.orderDetails;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

import model.OrderDetails.OrderDetails;
import model.order.Order;
import service.OrderDetailsService;

@WebServlet(name = "OrderDetailsServlet", urlPatterns = {"/orderDetails"})
public class OrderDetailsServlet extends HttpServlet {

    private OrderDetailsService orderDetailsService;

    public OrderDetailsServlet() {
        this.orderDetailsService = new OrderDetailsService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            session.setAttribute("msg", "❌ Người dùng không hợp lệ.");
            response.sendRedirect(request.getContextPath() + "/cart/Cart.jsp");
            return;
        }

        try {
            // Fetch all orders for the user
            List<Order> orderHistory = orderDetailsService.getOrderHistoryForUser(userId);
            if (orderHistory != null && !orderHistory.isEmpty()) {
                session.setAttribute("orderHistory", orderHistory);  // Store order history in session
                response.sendRedirect(request.getContextPath() + "/cart/order_details.jsp");  // Redirect to order details page
            } else {
                session.setAttribute("msg", "❌ Không tìm thấy lịch sử đơn hàng.");
                response.sendRedirect(request.getContextPath() + "/cart/Cart.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("msg", "❌ Lỗi khi truy vấn lịch sử đơn hàng.");
            response.sendRedirect(request.getContextPath() + "/cart/Cart.jsp");
        }
    }
}
