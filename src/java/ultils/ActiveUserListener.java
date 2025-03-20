package ultils;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class ActiveUserListener implements HttpSessionListener {
    private static int activeUsers = 0;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        //moi them
        // Chỉ tăng nếu session thực sự mới
        activeUsers++;
    
            System.out.println("New session created, active users: " + activeUsers);

    }

    @Override
    public   void  sessionDestroyed(HttpSessionEvent se) {
        
        
            System.out.println("Session destroyed event triggered, before decrement: " + activeUsers);

        if (activeUsers > 0) {
            activeUsers--;
        }
            System.out.println("Session destroyed, active users: " + activeUsers);

    }

    public static int getActiveUsers() {
        return activeUsers;
    }
}
