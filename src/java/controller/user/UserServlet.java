/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.user;
import dal.accountDao.AccountDao;
import dto.RegisterRequest;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.account.Account;
import service.AccountService;
import ultils.LoginManager;


/**
 *
 * @author ASUS
 */
@WebServlet(name="UserServlet", urlPatterns={"/userservlet"})
public class UserServlet extends HttpServlet {
     private static final long serialVersionUID = 1L;
     private AccountDao accountDao;
     AccountService accountService ;

    public void init() {
        accountDao = new AccountDao();
        accountService = new AccountService();
    }
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "create" ->
                showNewForm(request, response);
            case "edit" -> {
                try {
                    showEditForm(request, response);
                } catch (SQLException ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                case "enable" -> {
                try {
                    updateUserStatus(request, response, true); // Kích hoạt user
                } catch (SQLException ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            case "disable" -> {
                try {
                    updateUserStatus(request, response, false); // Vô hiệu hóa user
                } catch (SQLException ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                   
            case "delete" -> {
            try {
                deleteUser(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            default -> {
            }
        }
//                    deleteUser(request, response);
//                    listUser(request, response);
    }
        
    
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
            String action = request.getParameter("action");
            if (action == null) {
                action = "";
            }
            try {
                switch (action) {
                    case "create" -> insertUser(request, response);
                    case "edit"->
                        updateUser(request, response);
                       
                }
            } catch (SQLException ex) {
                throw new ServletException(ex);
            }
        }


    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

       private void listAccounts(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Account> listUser = accountDao.getAllAccounts();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/userList.jsp");
        dispatcher.forward(request, response);
    }

   private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/createUser.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Account existingUser = accountService.getAccountById(id);
        request.setAttribute("user", existingUser);
        request.getRequestDispatcher("/admin/editUser.jsp").forward(request, response);
    }

    
    private void insertUser(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, IOException, ServletException {
        HttpSession session = request.getSession(false);
 
String username = request.getParameter("username");
    String password = request.getParameter("password");
    String email = request.getParameter("email");
    
        RegisterRequest r = new RegisterRequest(username, password, email,2,username, username, email);
    boolean isRegistered = accountService.register(r);
    
    if (isRegistered) {
       
        session.setAttribute("listAccount", accountDao.getAllAccounts());
        response.sendRedirect(request.getContextPath()+"/admin/adminpage.jsp"); // Chuyển hướng về trang danh sách user sau khi thêm thành công
    } else {
        request.setAttribute("errorMessage", "Username already exists!");
        request.getRequestDispatcher("/admin/createUser.jsp").forward(request, response);
    }
}


    private void updateUser(HttpServletRequest request, HttpServletResponse response)
     throws SQLException, IOException, ServletException {
     int id = Integer.parseInt(request.getParameter("id"));
    String username = request.getParameter("username");
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    int roleId = Integer.parseInt(request.getParameter("roleid")); // Lấy role từ form

    System.out.println("Updating user: ID=" + id + ", Username=" + username + ", Email=" + email + ", Role=" + roleId);

    // Gọi DAO để cập nhật dữ liệu
    
    boolean success = accountService.updateUser(id, username, email, password, roleId);

    if (success) {
        System.out.println("User updated successfully!");
        List<Account> listAccounts = accountService.getAllAccounts();
        request.setAttribute("listAccount", listAccounts);
        request.getRequestDispatcher("/admin/adminpage.jsp").forward(request, response);
        // Quay về trang quản lý nếu cập nhật thành công
    } else {
        System.out.println("Failed to update user.");
        request.setAttribute("error", "Update failed!");
        RequestDispatcher dispatcher = request.getRequestDispatcher("editUser.jsp");
        dispatcher.forward(request, response); // Giữ lại trang edit nếu lỗi
    }
}


    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
       int id = Integer.parseInt(request.getParameter("id"));
        accountService.deleteAccount(id);

        List<Account> listAccounts = accountService.getAllAccounts();
        request.setAttribute("listAccount", listAccounts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminpage.jsp");
        dispatcher.forward(request, response);
    }
    
    private void updateUserStatus(HttpServletRequest request, HttpServletResponse response, boolean status)
        throws SQLException, IOException, ServletException {
    int id = Integer.parseInt(request.getParameter("id"));

    // Gọi AccountService để cập nhật trạng thái user
    boolean success = accountService.updateUserStatus(id, status);

    if (success) {
        System.out.println("User status updated successfully!");
        String username = accountService.getAccountById(id).getUsername();

//        // Nếu user đang đăng nhập thì logout ngay
//      if (!status) { // Nếu disable user thì logout
//    if (LoginManager.isUserLoggedIn(username)) {
//        LoginManager.logoutUser(username);
//       
//    }
//} else { 
//    // Nếu enable user, thêm lại vào danh sách đăng nhập luôn
//    HttpSession session = request.getSession();
//    LoginManager.addUser(username, session);
//}

    }
List<Account> listAccounts = accountService.getAllAccounts();
        request.setAttribute("listAccount", listAccounts);
    // Load lại danh sách users và quay về admin page
    RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/adminpage.jsp");
        dispatcher.forward(request, response);
    }
}

    
//    public static void main(String[] args) {
//        UserDAO d = new UserDAO();
//        List<User> listUser = d.selectAllUsers();
//System.out.println("Danh sach User:");
//listUser.forEach(System.out::println);
//    }


    
    

