package controller.cart;

import dal.viewDao.ViewCounterDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.book.Book;
import model.order.Cart;
import service.CartService;

@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {

    private CartService cartService;
    private ViewCounterDAO viewCounterDAO;

    public void init() {
        viewCounterDAO = new ViewCounterDAO();
        cartService = new CartService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/cart/Cart.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Kiểm tra đăng nhập
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        // Lấy giỏ hàng
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        // Kiểm tra và lấy userId
        Object userIdObj = session.getAttribute("userId");
        if (userIdObj == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }
        int userId = Integer.parseInt(userIdObj.toString());

        // Lấy tham số từ request
        String action = request.getParameter("action");
        String redirect = request.getParameter("redirect"); // Lấy tham số redirect
        int bookId = Integer.parseInt(request.getParameter("bookId"));

        switch (action) {
            case "add" -> {
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                Book book = cartService.getBookById(bookId);
                if (book != null) {
                    cartService.updateViewCount(userId, bookId);
                    cartService.addToCart(cart, book, quantity);
                    session.setAttribute("cart", cart);
                    // Chuyển hướng về trang chi tiết
                    response.sendRedirect(request.getContextPath() + "/BookDetailController?bookId=" + bookId);
                } else {
                    response.sendRedirect(request.getContextPath() + "/home/home.jsp");
                }
            }
            case "buyNow" -> {
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                Book book = cartService.getBookById(bookId);
                if (book != null) {
                    cartService.updateViewCount(userId, bookId);
                    cartService.addToCart(cart, book, quantity);
                    cartService.calculateCartTotal(cart, request);
                    session.setAttribute("cart", cart);
                    // Chuyển hướng đến trang giỏ hàng
                    response.sendRedirect(request.getContextPath() + "/cart/Cart.jsp");
                } else {
                    response.sendRedirect(request.getContextPath() + "/home/home.jsp");
                }
            }
            case "update" -> {
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                cartService.updateCartItem(cart, bookId, quantity);
                cartService.calculateCartTotal(cart, request);
                session.setAttribute("cart", cart);
                response.sendRedirect(request.getContextPath() + "/cart/Cart.jsp");
            }
            case "remove" -> {
                cart.removeItem(bookId);
                cartService.calculateCartTotal(cart, request);
                session.setAttribute("cart", cart);
                response.sendRedirect(request.getContextPath() + "/cart/Cart.jsp");
            }
        }
    }
}