package controller.cart;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.account.Account;
import model.order.Cart;
import model.order.CartItem;
import service.AccountService;
import service.OrderService;
import ultils.EmailService;
import dal.voucher.VoucherDAO;
import jakarta.servlet.RequestDispatcher;
import service.CartService;

@WebServlet(name = "CheckOutServlet", urlPatterns = {"/checkout"})
public class CheckOutServlet extends HttpServlet {

    private OrderService orderService = new OrderService();
    private AccountService accountService = new AccountService();
    private VoucherDAO voucherDAO = new VoucherDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/cart/Cart.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getItems().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart/Cart.jsp");
            return;
        }

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }
        if ("confirm".equals(request.getParameter("action"))) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/cart/confirm_order.jsp");
            dispatcher.forward(request, response);
            return;
        }
        CartService cartService = new CartService();
        String voucher = request.getParameter("voucher");
        session.setAttribute("voucher", voucher);
        System.out.println("checkout" + voucher);
        cartService.calculateCartTotal(cart, request); // Tính toán lại tổng tiền và giảm giá

        if ("done".equals(request.getParameter("action"))) {
            double finalPrice2 = Double.parseDouble(request.getParameter("finalPrice2"));
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String paymentMethod = request.getParameter("paymentMethod");
            String shippingMethod = request.getParameter("shippingMethod");
            String orderNote = request.getParameter("orderNote");

            session.setAttribute("phone", phone);
            session.setAttribute("address", address);
            session.setAttribute("paymentMethod", paymentMethod);
            session.setAttribute("shippingMethod", shippingMethod);
            session.setAttribute("orderNote", orderNote);
            session.setAttribute("finalPrice2", finalPrice2);

            if (paymentMethod == null || shippingMethod == null || address == null || phone == null
                    || paymentMethod.isEmpty() || shippingMethod.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                session.setAttribute("msg", "❌ Vui lòng nhập đầy đủ thông tin giao hàng.");
                response.sendRedirect(request.getContextPath() + "/cart/confirm_order.jsp");
                return;
            }

            int orderId = orderService.createOrder(userId, finalPrice2, address, phone, shippingMethod, paymentMethod);
            
            System.out.println("id la" + orderId);
            
            if (orderId > 0) {
                System.out.println("voucher dc doi");
                voucherDAO.markVoucherAsUsed(userId,  voucher );
                System.out.println("voucherla" + voucher);
                
                Account account = accountService.getAccountById(userId);
                String email = account.getEmail();

                String subject = "Xác nhận đặt hàng thành công";
                String content = "Chào " + account.getUsername() + ",\n\n"
                        + "Cảm ơn bạn đã đặt hàng tại BOOKSALE!\n"
                        + "Mã đơn hàng của bạn: " + orderId + "\n"
                        + "Tổng tiền: " + finalPrice2 + " VNĐ\n\n"
                        + "Chúng tôi sẽ xử lý đơn hàng của bạn sớm nhất.\n\n"
                        + "Trân trọng,\n BOOKSALE Team";
                EmailService.sendEmail(email, subject, content);

                orderService.addOrderDetails(orderId, cart, finalPrice2);

                // Đánh dấu voucher đã sử dụng nếu có
                Integer selectedVoucherId = (Integer) session.getAttribute("selectedVoucherId");
                if (selectedVoucherId != null) {
                    voucherDAO.markVoucherAsUsed(userId, selectedVoucherId);
                    session.removeAttribute("selectedVoucherId");
                    session.removeAttribute("selectedVoucherCode");
                }

                session.setAttribute("msg", "✅ Đơn hàng của bạn đã được ghi nhận.");
                session.removeAttribute("voucherPrice");
                session.removeAttribute("finalPrice");
                session.removeAttribute("voucher");
                session.removeAttribute("cart");
            } else {
                session.setAttribute("msg", "❌ Lỗi: Không thể tạo đơn hàng.");
            }
        }
        response.sendRedirect(request.getContextPath() + "/cart/Cart.jsp");
    }

    private double calculateTotalPrice(Cart cart) {
        double total = 0;
        for (CartItem item : cart.getItems().values()) {
            total += item.getQuantity() * item.getBook().getPrice();
        }
        return total;
    }

}
