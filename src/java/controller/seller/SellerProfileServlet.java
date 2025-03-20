package controller.seller;

import service.interfaces.AccountService;
import service.AccountServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.account.Account;
import java.io.IOException;

@WebServlet("/SellerProfileServlet")
public class SellerProfileServlet extends HttpServlet {
    private AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.setAttribute("activeTab", "profile");
        request.getRequestDispatcher("/seller/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            String email = request.getParameter("email");
            String fullName = request.getParameter("fullName");
            String phoneNumber = request.getParameter("phoneNumber");
            String address = request.getParameter("address");

            if (email == null || email.trim().isEmpty()) {
                request.setAttribute("error", "Email không được để trống!");
                request.setAttribute("activeTab", "profile");
                request.getRequestDispatcher("/seller/profile.jsp").forward(request, response);
                return;
            }

            Account updatedAccount = new Account();
            updatedAccount.setUserId(userId);
            updatedAccount.setEmail(email);
            updatedAccount.setFullName(fullName);
            updatedAccount.setPhoneNumber(phoneNumber);
            updatedAccount.setAddress(address);

            accountService.updateAccountProfile(updatedAccount);

            account.setEmail(email);
            account.setFullName(fullName);
            account.setPhoneNumber(phoneNumber);
            account.setAddress(address);
            session.setAttribute("account", account);

            request.setAttribute("message", "Cập nhật hồ sơ thành công!");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Lỗi: ID không hợp lệ!");
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi khi cập nhật: " + e.getMessage());
        }
        request.setAttribute("activeTab", "profile");
        request.getRequestDispatcher("/seller/profile.jsp").forward(request, response);
    }
}