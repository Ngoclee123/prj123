package service.interfaces;

import model.account.Account;
import model.OrderDayData.OrderDayData;
import java.util.List;
import java.util.Map;

public interface AccountService {
    void addUser(Account account);
    void updateUser(Account account);
    void updateAccountProfile(Account account);
    void toggleUserStatus(int userId);
    Account getAccountById(int id);
    List<Account> getAccountsByPage(int page);
    List<Account> getAccountsByRoleAndPage(int roleId, int page);
    int getTotalAccounts();
    int getTotalAccountsByRole(int roleId);
    boolean isUsernameExists(String username);
    int getTotalActiveUsers();
    double getTotalRevenue();
    int getCompletedOrderCount();
    List<OrderDayData> getCompletedOrdersByDay(String startDate, String endDate);
}