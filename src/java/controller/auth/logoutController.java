package controller.auth;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ultils.LoginManager;

/**
 *
 * @author ASUS
 */
@WebServlet(name="logoutController", urlPatterns={"/logout"})
public class logoutController extends HttpServlet {
   
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
            out.println("<title>Servlet logoutController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet logoutController at " + request.getContextPath() + "</h1>");
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
        logoutUser(request, response);
        // Ngăn cache và chuyển hướng qua AdminServlet
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        // Kiểm tra lại session sau khi hủy
        HttpSession sessionAfterLogout = request.getSession(false);
        if (sessionAfterLogout == null) {
            System.out.println("Session is null after logout - successfully invalidated.");
        } else {
            System.out.println("WARNING: Session still exists after logout - possible issue.");
        }
        response.sendRedirect(request.getContextPath() + "/home/logout-success.jsp");
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    
    // Xử lý đăng xuất người dùng
    private void logoutUser(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String username = (String) session.getAttribute("sessionLogin");
            if (username != null) {
                // Gọi removeUser để xóa khỏi danh sách và cập nhật ServletContext
                LoginManager.removeUser(username, session, getServletContext());
                System.out.println("Removed user " + username + " from LoginManager.");
            } else {
                System.out.println("No logged-in user found in session.");
            }
            // Hủy session trong logoutController
            session.invalidate();
            System.out.println("Session invalidated for user (if any): " + username);
        } else {
            System.out.println("No active session found to invalidate.");
        }
        clearLoginCookies(request, response);
    }

    // Xóa cookie đăng nhập
    private void clearLoginCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("COOKIE_USERNAME".equals(cookie.getName()) || "COOKIE_PASSWORD".equals(cookie.getName())) {
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    System.out.println("Cleared cookie: " + cookie.getName());
                }
            }
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}