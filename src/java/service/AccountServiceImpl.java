package service;

import dal.accountDao.AccountDao;
import service.interfaces.AccountService;
import model.account.Account;
import model.OrderDayData.OrderDayData;
import java.util.List;

public class AccountServiceImpl implements AccountService {
    private AccountDao accountDAO = new AccountDao();

    @Override
    public void addUser(Account account) {
        accountDAO.addUser(account);
    }

    @Override
    public void updateUser(Account account) {
        accountDAO.updateUser(account);
    }

    @Override
    public void updateAccountProfile(Account account) {
        accountDAO.updateAccountProfile(account);
    }

    @Override
    public void toggleUserStatus(int userId) {
        accountDAO.toggleUserStatus(userId);
    }

    @Override
    public Account getAccountById(int id) {
        return accountDAO.getAccountById(id);
    }

    @Override
    public List<Account> getAccountsByPage(int page) {
        return accountDAO.getAccountsByPage(page);
    }

    @Override
    public List<Account> getAccountsByRoleAndPage(int roleId, int page) {
        return accountDAO.getAccountsByRoleAndPage(roleId, page);
    }

    @Override
    public int getTotalAccounts() {
        return accountDAO.getTotalAccounts();
    }

    @Override
    public int getTotalAccountsByRole(int roleId) {
        return accountDAO.getTotalAccountsByRole(roleId);
    }

    @Override
    public boolean isUsernameExists(String username) {
        return accountDAO.isUsernameExists(username);
    }

    @Override
    public int getTotalActiveUsers() {
        return accountDAO.getTotalActiveUsers();
    }

    @Override
    public double getTotalRevenue() {
        return accountDAO.getTotalRevenue();
    }

    @Override
    public int getCompletedOrderCount() {
        return accountDAO.getCompletedOrderCount();
    }

    @Override
    public List<OrderDayData> getCompletedOrdersByDay(String startDate, String endDate) {
        return accountDAO.getCompletedOrdersByDay(startDate, endDate);
    }
}