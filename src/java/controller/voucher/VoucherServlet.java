package controller.voucher;

import dal.voucher.VoucherDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import model.account.Account;
import service.AccountService;
import ultils.EmailService;
import model.voucher.Voucher;

@WebServlet(name = "VoucherServlet", urlPatterns = {"/voucher"})
public class VoucherServlet extends HttpServlet {
    private VoucherDAO voucherDAO = new VoucherDAO();
    private AccountService accountService = new AccountService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("email");
        Account account = accountService.getAccountByEmail(email);

        if (account == null) {
            out.print("Email không tồn tại trong hệ thống!");
            return;
        }

        List<Voucher> availableVouchers = voucherDAO.getAvailableVouchers();
        if (availableVouchers.isEmpty()) {
            out.print("Hiện tại không có mã giảm giá nào!");
            return;
        }

        Voucher randomVoucher = availableVouchers.get((int) (Math.random() * availableVouchers.size()));
        voucherDAO.assignVoucherToUser(account.getUserId(), randomVoucher.getId());

        String subject = "Mã giảm giá từ Book Store";
        String content = "Chào " + account.getFullName() + ",\n\n" // Thay getUsername() thành getFullName()
                + "Bạn đã nhận được mã giảm giá: " + randomVoucher.getCode() + "\n"
                + "Giảm: " + randomVoucher.getDiscount() + "%\n\n"
                + "Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi!\n"
                + "Book Store Team";
        EmailService.sendEmail(email, subject, content);

        out.print("Mã giảm giá đã được gửi đến email của bạn!");
    }
}