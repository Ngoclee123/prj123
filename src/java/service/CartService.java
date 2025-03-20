package service;

import dal.bookDao.BookDAO;
import dal.viewDao.ViewCounterDAO;
import dal.voucher.VoucherDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.book.Book;
import model.order.Cart;
import model.order.CartItem;
import model.voucher.Voucher;
import service.interfaces.ICartService;
import java.util.List;

public class CartService implements ICartService {
    private BookDAO bookDAO = new BookDAO();
    private ViewCounterDAO viewCounterDAO = new ViewCounterDAO();
    private VoucherDAO voucherDAO = new VoucherDAO();

    @Override
    public void addToCart(Cart cart, Book book, int quantity) {
        cart.addItem(book, quantity);
    }

    @Override
    public void updateCartItem(Cart cart, int productId, int quantity) {
        cart.updateItem(productId, quantity);
    }

    @Override
    public void removeCartItem(Cart cart, int bookId) {
        cart.removeItem(bookId);
    }

    @Override
    public double getTotalPrice(Cart cart) {
        double totalPrice = 0;
        for (CartItem item : cart.getItems().values()) {
            totalPrice += item.getQuantity() * item.getBook().getPrice();
        }
        return totalPrice;
    }

    public void calculateCartTotal(Cart cart, HttpServletRequest request) {
        double totalPrice = getTotalPrice(cart);
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        System.out.println("sai used"+userId);
        if (userId == null) {
            System.out.println("chay vao day");
            session.setAttribute("totalPrice", totalPrice);
            session.setAttribute("voucherPrice", 0.0);
            session.setAttribute("finalPrice", totalPrice);
            return;
        }

        double discount = 0;
        String voucherCode = (String) session.getAttribute("voucher");
        System.out.println("lay voucher " + voucherCode); 
       if (voucherCode != null && !voucherCode.equals("none")) {
            
            List<Voucher> userVouchers = voucherDAO.getAvailableVouchersForUser(userId);
            System.out.println("list voucher" + userVouchers);
            for (Voucher v : userVouchers) {
                if (v.getCode().equals(voucherCode)) {
                    discount = totalPrice * (v.getDiscount() / 100);
                    
                    session.setAttribute("selectedVoucherId", v.getId());
                    session.setAttribute("selectedVoucherCode", v.getCode());
                    break;
                }
            }
        } else {
            session.removeAttribute("selectedVoucherId");
            session.removeAttribute("selectedVoucherCode");
        }

        double finalPrice = totalPrice - discount;
        System.out.println("discount:"+discount);
        session.setAttribute("totalPrice", totalPrice);
        System.out.println(totalPrice);
        session.setAttribute("voucherPrice", discount);
                System.out.println(discount);

        session.setAttribute("finalPrice", finalPrice);
                System.out.println(finalPrice);

        session.setAttribute("voucher", voucherCode);
                System.out.println(voucherCode);

        System.out.println("toi day");
    }

    public Book getBookById(int bookId) {
        return bookDAO.selectBook(bookId);
    }

    public void updateViewCount(int userId, int bookId) {
        viewCounterDAO.updateViewCount(userId, bookId);
    }
}