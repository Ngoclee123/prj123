package ultils;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author ASUS
 */
public class LoginManager {
    // Sử dụng ConcurrentHashMap để hỗ trợ đa luồng nếu cần
    private static final Map<String, LocalDateTime> loggedInUsers = new ConcurrentHashMap<>();
    private static final Map<String, HttpSession> userSessions = new ConcurrentHashMap<>();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Thêm user vào danh sách khi đăng nhập
    public static void addUser(String username, HttpSession session, ServletContext context) {
        LocalDateTime loginTime = LocalDateTime.now();
        loggedInUsers.put(username, loginTime);
        userSessions.put(username, session);
        System.out.println("User " + username + " logged in at " + loginTime.format(formatter));
        // Cập nhật danh sách và số người online vào ServletContext
        updateServletContext(context);
    }

    // Xóa user khỏi danh sách khi đăng xuất (phiên bản gốc)
    public static void removeUser(String username, ServletContext context) {
        if (loggedInUsers.containsKey(username)) {
            loggedInUsers.remove(username);
            System.out.println("User " + username + " logged out.");
            // Cập nhật lại ServletContext
            updateServletContext(context);
        }
        userSessions.remove(username); // Chỉ xóa session khỏi map, không invalidate
    }

    // Thêm phương thức mới để hỗ trợ chữ ký có HttpSession
    public static void removeUser(String username, HttpSession session, ServletContext context) {
        if (loggedInUsers.containsKey(username)) {
            loggedInUsers.remove(username);
            System.out.println("User " + username + " logged out.");
            // Cập nhật lại ServletContext
            updateServletContext(context);
        }
        userSessions.remove(username); // Chỉ xóa session khỏi map, không invalidate
    }

    // Cập nhật danh sách người dùng và số người online vào ServletContext
    private static void updateServletContext(ServletContext context) {
        // Chuyển LocalDateTime thành chuỗi để JSP dễ hiển thị
        Map<String, String> usersWithTime = new ConcurrentHashMap<>();
        for (Map.Entry<String, LocalDateTime> entry : loggedInUsers.entrySet()) {
            usersWithTime.put(entry.getKey(), entry.getValue().format(formatter));
        }
        context.setAttribute("activeUsersList", usersWithTime);
        context.setAttribute("onlineUsers", loggedInUsers.size()); // Thêm cập nhật số người online
        System.out.println("Updated onlineUsers to: " + loggedInUsers.size());
    }

    // Kiểm tra user có đang đăng nhập không
    public static boolean isUserLoggedIn(String username) {
        return loggedInUsers.containsKey(username);
    }

    // Lấy danh sách tất cả user đang đăng nhập
    public static Map<String, LocalDateTime> getLoggedInUsers() {
        return loggedInUsers;
    }
    
    public static void logoutUser(String username, ServletContext context) {
        if (loggedInUsers.containsKey(username)) {
            loggedInUsers.remove(username);
            updateServletContext(context);
        }
        HttpSession session = userSessions.remove(username);
        if (session != null) {
            try {
                session.invalidate();
                System.out.println("Session của " + username + " đã bị hủy.");
            } catch (IllegalStateException e) {
                System.out.println("Session đã bị vô hiệu hóa trước đó.");
            }
        } else {
            System.out.println("Không tìm thấy session của " + username);
        }
    }
}