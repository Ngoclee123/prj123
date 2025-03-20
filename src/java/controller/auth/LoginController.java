package controller.auth;

import constant.MessageConstant;
import controller.loginGG.GoogleLogin;
import dto.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.account.Account;
import model.book.Book;
import service.AccountService;
import service.BookService;
import ultils.LoginManager;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "loginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    private static final String LOGIN_URL = "/auth/login.jsp";
    private static final String WELCOME_URL = "welcome.jsp";
    private static final String HOME_URL = "/home/home.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        String action = request.getParameter("action");
        if (!"login".equals(action) && code == null) {
            request.getRequestDispatcher(HOME_URL).forward(request, response);

        } else if ("login".equals(action)) {
            response.sendRedirect(request.getContextPath() + LOGIN_URL);
        } else {
            // Bước 1: Lấy access token từ Google
            GoogleLogin googleLogin = new GoogleLogin();
            String accessToken = googleLogin.getToken(code);

            // Bước 2: Lấy thông tin user từ Google
            Account googleAccount = GoogleLogin.getUserInfo(accessToken);

            // Bước 3: Kiểm tra user trong database
            AccountService accountService = new AccountService();
            Account existingAccount = accountService.getAccountByEmail(googleAccount.getEmail());

            HttpSession session = request.getSession();
            if (existingAccount == null) {
                boolean isCreated = accountService.createGoogleAccount(googleAccount.getUsername(), googleAccount.getEmail());
                if (isCreated) {
                    session.setAttribute("user", googleAccount);
                    session.setAttribute("username", googleAccount.getUsername());
                    session.setAttribute("fullname", googleAccount.getUsername());
                    System.out.println("Insert thành công? " + isCreated);
                }
            } else {
                session.setAttribute("user", existingAccount);
            }
            response.sendRedirect(request.getContextPath() + HOME_URL);

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("username");
        String passWord = request.getParameter("password");
        boolean rememberMe = request.getParameter("rememberMe") != null;

        AccountService accountService = new AccountService();
        BookService bookService = new BookService();
        Response<Account> accountResponse = accountService.checkLogin(userName, passWord);

        if (accountResponse.isSuccess()) {
            Account account = accountResponse.getData();

            if (accountService.isAccountDisabled(userName)) {
                request.setAttribute("err", MessageConstant.MESSAGE_Disable);
                request.getRequestDispatcher(LOGIN_URL).forward(request, response);
                return;
            }

            HttpSession session = request.getSession();
            session.setAttribute("username", userName);
            session.setAttribute("sessionLogin", userName);
            session.setAttribute("userId", account.getUserId());
            session.setAttribute("isAdmin", accountService.isAdmin(userName));
            session.setAttribute("isSeller", accountService.isSeller(userName));
            session.setAttribute("fullname", account.getFullName()); // Cập nhật từ getFullnameById sang getFullName
            session.setAttribute("account", account);

            LoginManager.addUser(userName, session, getServletContext());

            List<Book> recommendBooks = bookService.getTopViewedBooks(account.getUserId());
            session.setAttribute("recommendBooks", recommendBooks);
            System.out.println("Recommended Books: " + recommendBooks);

            handleRememberMe(request, response, userName, passWord, rememberMe);

            if ((boolean) session.getAttribute("isAdmin")) {
                session.setAttribute("listAccount", accountService.getAllAccounts());
                response.sendRedirect(request.getContextPath() + "/AdminServlet");
            } else if ((boolean) session.getAttribute("isSeller")) {
                response.sendRedirect(request.getContextPath() + "/SellerServlet");
            } else {
                List<Book> booksList = bookService.selectAllBooks();
                request.setAttribute("bookList", booksList);
                System.out.println("Book List: " + booksList);
                request.getRequestDispatcher("/home/home.jsp").forward(request, response);
            }
        } else {
            System.out.println("Sai tài khoản hoặc mật khẩu");
            request.setAttribute("err", MessageConstant.LOGIN_FAILED);
            request.getRequestDispatcher(LOGIN_URL).forward(request, response);
        }
    }

    private void handleRememberMe(HttpServletRequest request, HttpServletResponse response,
            String username, String password, boolean remember) {
        if (remember) {
            Cookie usernameCookie = new Cookie("COOKIE_USERNAME", username);
            Cookie passwordCookie = new Cookie("COOKIE_PASSWORD", password);

            usernameCookie.setMaxAge(60 * 60 * 24);
            passwordCookie.setMaxAge(60 * 60 * 24);

            usernameCookie.setPath("/");
            passwordCookie.setPath("/");

            response.addCookie(usernameCookie);
            response.addCookie(passwordCookie);
        } else {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().equals("COOKIE_USERNAME") || c.getName().equals("COOKIE_PASSWORD")) {
                        c.setValue("");
                        c.setMaxAge(0);
                        c.setPath("/");
                        response.addCookie(c);
                    }
                }
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Login Controller for handling user authentication";
    }
}
