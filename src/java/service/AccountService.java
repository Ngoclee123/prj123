package service;

import constant.MessageConstant;
import dal.accountDao.AccountDao;
import dto.RegisterRequest;
import dto.Response;
import java.util.List;
import model.account.Account;
import service.interfaces.IAccountService;
import ultils.PasswordHasher;

/**
 *
 * @author ASUS
 */
public class AccountService implements IAccountService {

 private final AccountDao accountDao;

    public AccountService() {
        accountDao = new AccountDao();
    }

    @Override
    public Response<Account> checkLogin(String username, String password) {
        Account account = accountDao.getAccountByUsername(username);
        
        if (account != null) {
            String hashedInputPassword = PasswordHasher.toSHA1(password);
            System.out.println("Mật khẩu nhập vào: " + password);
            System.out.println("Mật khẩu sau khi mã hóa: " + hashedInputPassword);
            System.out.println("Mật khẩu trong database: " + account.getPassword());

            if (account.getPassword() != null && hashedInputPassword.equals(account.getPassword())) {
                return new Response<>(account, true, MessageConstant.LOGIN_SUCCESS);
            }
        }
        return new Response<>(null, false, MessageConstant.LOGIN_FAILED);
    }

    @Override
    public boolean register(RegisterRequest r) {
        // Kiểm tra xem username đã tồn tại chưa
        if (accountDao.getAccountByUsername(r.getUsername()) != null) {
            return false; // Username đã tồn tại
        }

        // Mã hóa mật khẩu và tạo tài khoản mới
        String hashedPassword = PasswordHasher.toSHA1(r.getPassword());
        return accountDao.registerAccount(
            r.getUsername(), 
            hashedPassword, 
            r.getEmail(), 
            r.getRole(), 
            true, // status mặc định là true (kích hoạt)
            r.getFullName(),  // Thêm FullName
            r.getPhoneNumber(), // Thêm PhoneNumber
            r.getAddress()    // Thêm Address
        );
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountDao.getAllAccounts();
    }

    @Override
    public Account getAccountById(int id) {
        return accountDao.getAccountById(id);
    }
     public String getFullnameById(int id) {
        return accountDao.getFullNameById(id);
    }

    @Override
    public boolean isAdmin(String username) {
        Account account = accountDao.getAccountByUsername(username);
        return account != null && account.getRoleId() == 1;
    }
    
    public boolean isSeller(String username) {
        Account account = accountDao.getAccountByUsername(username);
        return account != null && account.getRoleId() == 3; 
    }

    @Override
    public boolean updateUser(int id, String username, String email, String password, int roleId) {
        // Giả sử mật khẩu đã được mã hóa trước khi gọi phương thức này
        Account account = accountDao.getAccountById(id);
        if (account == null) {
            return false;
        }
        
        // Nếu password không thay đổi, giữ nguyên password cũ
        String finalPassword = (password == null || password.isEmpty()) 
            ? account.getPassword() 
            : PasswordHasher.toSHA1(password);
        
        return accountDao.updateUser(
            id, 
            username, 
            email, 
            finalPassword, 
            roleId,
            account.getFullName(),  // Giữ nguyên FullName cũ
            account.getPhoneNumber(), // Giữ nguyên PhoneNumber cũ
            account.getAddress()    // Giữ nguyên Address cũ
        );
    }

    @Override
    public boolean deleteAccount(int id) {
        return accountDao.deleteAccountById(id);
    }

    @Override
    public boolean isAccountDisabled(String username) {
        return accountDao.isAccountDisabled(username);
    }

    @Override
    public boolean updateUserStatus(int userId, boolean status) {
        return accountDao.updateUserStatus(userId, status);
    }
    
    public Account getAccountByEmail(String email) {
        return accountDao.getAccountByEmail(email);
    }

    public boolean createGoogleAccount(String username, String email) {
        return accountDao.createAccountGoogle(username, email);
    
}
    
}