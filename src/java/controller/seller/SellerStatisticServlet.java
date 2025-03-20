package controller.seller;

import service.interfaces.AccountService;
import service.AccountServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ultils.LoginManager;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.OrderDayData.OrderDayData;
import com.google.gson.Gson;

@WebServlet("/SellerStatisticServlet")
public class SellerStatisticServlet extends HttpServlet {
    private AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer onlineUsers = (Integer) getServletContext().getAttribute("onlineUsers");
        if (onlineUsers == null) {
            onlineUsers = LoginManager.getLoggedInUsers().size();
            getServletContext().setAttribute("onlineUsers", onlineUsers);
        }
        request.setAttribute("onlineUsers", onlineUsers);

        Map<String, String> activeUsersList = (Map<String, String>) getServletContext().getAttribute("activeUsersList");
        if (activeUsersList == null) {
            activeUsersList = LoginManager.getLoggedInUsers().entrySet().stream()
                    .collect(Collectors.toMap(
                            entry -> entry.getKey(),
                            entry -> entry.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    ));
            getServletContext().setAttribute("activeUsersList", activeUsersList);
        }
        request.setAttribute("activeUsersList", activeUsersList);

        request.setAttribute("activeUsers", accountService.getTotalActiveUsers());
        request.setAttribute("totalRevenue", accountService.getTotalRevenue());
        request.setAttribute("completedOrderCount", accountService.getCompletedOrderCount());

        List<OrderDayData> ordersByDay = accountService.getCompletedOrdersByDay("2025-03-01", "2025-03-31");
        Gson gson = new Gson();
        String ordersJson = gson.toJson(ordersByDay);
        request.setAttribute("ordersByDay", ordersJson);

        request.setAttribute("activeTab", "statistic");
        request.getRequestDispatcher("/seller/statistic.jsp").forward(request, response);
    }
}