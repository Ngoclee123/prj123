package service.interfaces;

import dto.RegisterRequest;
import dto.Response;
import model.account.Account;
import java.util.List;

public interface IAccountService {
    Response<Account> checkLogin(String username, String password);
    boolean register(RegisterRequest r);
    boolean isAdmin(String username);
    List<Account> getAllAccounts();
    Account getAccountById(int id);
public boolean updateUser(int id, String username, String email, String password, int roleId);
public boolean deleteAccount(int id);
public boolean isAccountDisabled(String username);
public boolean updateUserStatus(int userId, boolean status);
}
