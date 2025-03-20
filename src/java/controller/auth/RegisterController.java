package controller.auth;

import dto.RegisterRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AccountService;
import dal.voucher.IVoucherDAO;
import dal.voucher.VoucherDAO;
import model.account.Account;
import model.voucher.Voucher;
import ultils.EmailService;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "RegisterController", urlPatterns = {"/registerc"})
public class RegisterController extends HttpServlet {

    private AccountService accountService = new AccountService();
    
    
    private IVoucherDAO voucherDAO = new VoucherDAO();
    
    
    
    private static final Logger LOGGER = Logger.getLogger(RegisterController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Giữ nguyên logic doGet (hiện tại không có gì)
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("username");
        String passWord = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmpassword");
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullname");
        String phoneNum = request.getParameter("phonenum");
        String address = request.getParameter("address");
        int role = 2;

        // Kiểm tra mật khẩu
        if (!passWord.equals(confirmPassword)) {
            request.setAttribute("err", "Mật khẩu và xác nhận mật khẩu không khớp!");
            request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
            return;
        }

        // Tạo đối tượng RegisterRequest
        RegisterRequest registerRequest = new RegisterRequest(userName, passWord, email, role, fullName, phoneNum, address);

        // Đăng ký tài khoản
        if (accountService.register(registerRequest)) {
            // Lấy thông tin tài khoản vừa đăng ký
            Account account = accountService.getAccountByEmail(email);
            if (account == null) {
                request.setAttribute("err", "Không thể tìm thấy tài khoản vừa đăng ký!");
                request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
                return;
            }
            int userId = account.getUserId();
            
            //check email trùng 
//            if (account.getEmail() != registerRequest.getEmail()) {
//                request.setAttribute("err", "Tên đăng nhập hoặc email đã tồn tại!");
//                request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
//                return ;
//            }
            
            
            // Lấy danh sách voucher khả dụng từ database
            List<Voucher> availableVouchers = voucherDAO.getAvailableVouchers();
            System.out.println("uh" + availableVouchers);
            if (!availableVouchers.isEmpty()) {
                // Chọn ngẫu nhiên một voucher từ danh sách
                Voucher randomVoucher = availableVouchers.get((int) (Math.random() * availableVouchers.size()));
                // Gán voucher cho người dùng
                System.out.println("voucche" + randomVoucher);
                voucherDAO.assignVoucherToUser(userId, randomVoucher.getId());
                System.out.println("ganvoucher");
                // Gửi email chứa mã voucher
                String subject = "Chào mừng bạn đến với Book Store - Mã giảm giá đầu tiên!";
                String content = "Chào " + fullName + ",\n\n"
                        + "Cảm ơn bạn đã đăng ký tài khoản tại Book Store!\n"
                        + "Đây là mã giảm giá đầu tiên của bạn: " + randomVoucher.getCode() + "\n"
                        + "Giảm: " + randomVoucher.getDiscount() + "%\n\n"
                        + "Hãy sử dụng mã này khi thanh toán đơn hàng của bạn!\n"
                        + "Book Store Team";
                try {
                    EmailService.sendEmail(email, subject, content);
                    LOGGER.log(Level.INFO, "Đã gửi email chứa voucher {0} đến {1}", new Object[]{randomVoucher.getCode(), email});
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Lỗi khi gửi email đến {0}: {1}", new Object[]{email, e.getMessage()});
                    // Dù email gửi thất bại, vẫn cho phép người dùng tiếp tục đăng ký
                }
            } else {
                // Nếu không có voucher khả dụng, gửi email chào mừng không kèm mã
                String subject = "Chào mừng bạn đến với Book Store!";
                String content = "Chào " + fullName + ",\n\n"
                        + "Cảm ơn bạn đã đăng ký tài khoản tại Book Store!\n"
                        + "Hiện tại chúng tôi không có mã giảm giá khả dụng, nhưng hãy theo dõi email để nhận ưu đãi trong tương lai!\n"
                        + "Book Store Team";
                try {
                    EmailService.sendEmail(email, subject, content);
                    LOGGER.log(Level.INFO, "Đã gửi email chào mừng đến {0}", email);
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Lỗi khi gửi email chào mừng đến {0}: {1}", new Object[]{email, e.getMessage()});
                }
            }
            System.out.println("cccc");
            // Chuyển hướng đến trang đăng nhập
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
        } else {
            request.setAttribute("err", "Tên đăng nhập hoặc email đã tồn tại!");
            request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
