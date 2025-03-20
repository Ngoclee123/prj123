/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */

package filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hi
 */
@WebFilter(filterName="RoleFiltera", urlPatterns={"/*SellerServlet", "/*userservlet", "/*AdminServlet"})
public class RoleFiltera implements Filter {
    
    private static final boolean debug = true;
    private FilterConfig filterConfig = null;
    
    public RoleFiltera() {
    }    
    
    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("RoleFilter:DoBeforeProcessing");
        }
    }    
    
    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("RoleFilter:DoAfterProcessing");
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        if (debug) {
            log("RoleFilter:doFilter()");
        }
        
        doBeforeProcessing(request, response);
        
        HttpServletRequest request1 = (HttpServletRequest) request;
        HttpServletResponse response1 = (HttpServletResponse) response;
        HttpSession session = request1.getSession();
        
        // Kiểm tra nếu chưa đăng nhập
        if (session.getAttribute("sessionLogin") == null) {
            if (!request1.getRequestURI().endsWith("login.jsp")) {
                response1.sendRedirect(request1.getContextPath() + "/auth/login.jsp");
            } else {
                chain.doFilter(request, response);
            }
            return;
        }

        // Tạo danh sách vai trò và đích đến
        List<String> roles = new ArrayList<>();
        List<String> destinations = new ArrayList<>();
        
        // Thêm các vai trò và trang đích tương ứng dựa trên các servlet bạn cung cấp
        roles.add("isAdmin");
        roles.add("isSeller");
        destinations.add(request1.getContextPath() + "/AdminServlet");  // Trang admin
        destinations.add(request1.getContextPath() + "/SellerServlet"); // Trang seller
        
        // Trang mặc định cho user thường
        String defaultDestination = request1.getContextPath() + "/home/home.jsp";
        
        // Duyệt qua danh sách vai trò
        boolean roleMatched = false;
        String currentPath = request1.getRequestURI();
        
        for (int i = 0; i < roles.size(); i++) {
            Boolean hasRole = (Boolean) session.getAttribute(roles.get(i));
            if (hasRole != null && hasRole) {
                String targetPath = destinations.get(i);
                // Chỉ redirect nếu không ở đúng trang tương ứng với vai trò
                if (!currentPath.contains(targetPath.substring(targetPath.lastIndexOf('/')))) {
                    response1.sendRedirect(targetPath);
                    return;
                }
                roleMatched = true;
                break;
            }
        }
        
        // Nếu không match với admin hoặc seller (tức là user thường)
        if (!roleMatched) {
            if (!currentPath.endsWith("/home.jsp") && !currentPath.contains("/userservlet")) {
                response1.sendRedirect(defaultDestination);
                return;
            }
        }

        // Tiếp tục chuỗi filter nếu không cần redirect
        Throwable problem = null;
        try {
            chain.doFilter(request, response);
        } catch (Throwable t) {
            problem = t;
            t.printStackTrace();
        }
        
        doAfterProcessing(request, response);

        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void destroy() {        
    }

    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {                
                log("RoleFilter:Initializing filter");
            }
        }
    }

    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("RoleFilter()");
        }
        StringBuffer sb = new StringBuffer("RoleFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);        
        
        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);                
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n");
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");                
                pw.print(stackTrace);                
                pw.print("</pre></body>\n</html>");
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }
    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);        
    }
}