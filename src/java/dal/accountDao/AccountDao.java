//package dal.accountDao;
//
//import constant.MessageConstant;
//import model.account.Account;
//import dal.DBContext;
//import dto.Response;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// *
// * @author ASUS
// */
//public class AccountDao extends DBContext {
//
//    public Account checkLogin(String username, String password) {
//        String sql = "SELECT * FROM Account WHERE Username = ? AND Password = ?";
//
//        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setString(1, username);
//            ps.setString(2, password);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    return new Account(
//                            rs.getInt("userid"),
//                            rs.getString("username"),
//                            rs.getString("password"),
//                            rs.getInt("roleid"),
//                            rs.getBoolean("status"), rs.getString("email"),
//                            rs.getString("FullName"),
//                            rs.getString("PhoneNumber"));
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null; // Đăng nhập thất bại
//    }
//
//    public Account getAccountByUsername(String username) {
//        String sql = "SELECT * FROM Account WHERE Username = ?";
//        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setString(1, username);
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    return new Account(
//                            rs.getInt("userid"),
//                            rs.getString("username"),
//                            rs.getString("password"),
//                            rs.getInt("roleid"),
//                            rs.getBoolean("status"), rs.getString("email"),
//                            rs.getString("FullName"),
//                            rs.getString("PhoneNumber"));
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null; // Không tìm thấy username => có thể đăng ký
//    }
//
//    public boolean registerAccount(String username, String password, String email, int roleid,
//            boolean status, String fullName, String phoneNumber, String address) {
//        if (getAccountByUsername(username) != null) {
//            return false; // Không cho phép đăng ký nếu username đã tồn tại
//        }
//
//        String sql = "INSERT INTO Account (Username, Password, Email, roleid, status, FullName, PhoneNumber, Address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setString(1, username);
//            ps.setString(2, password);
//            ps.setString(3, email);
//            ps.setInt(4, roleid);
//            ps.setBoolean(5, status);
//            ps.setString(6, fullName);
//            ps.setString(7, phoneNumber);
//            ps.setString(8, address);
//
//            int rowsInserted = ps.executeUpdate();
//            return rowsInserted > 0;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public List<Account> getAllAccounts() {
//        List<Account> accountList = new ArrayList<>();
//        String sql = "SELECT * FROM Account";
//        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
//            while (rs.next()) {
//                Account account = new Account(
//                        rs.getInt("userid"),
//                        rs.getString("username"),
//                        rs.getString("password"),
//                        rs.getInt("roleid"),
//                        rs.getBoolean("status"), rs.getString("email"),
//                        rs.getString("FullName"),
//                        rs.getString("PhoneNumber"));
//                accountList.add(account);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return accountList;
//    }
//
//    public Account getAccountById(int id) {
//        String sql = "SELECT * FROM Account WHERE userid = ?";
//        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setInt(1, id);
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    return new Account(
//                            rs.getInt("userid"),
//                            rs.getString("username"),
//                            rs.getString("password"),
//                            rs.getInt("roleid"),
//                            rs.getBoolean("status"), rs.getString("email"),
//                            rs.getString("FullName"),
//                            rs.getString("PhoneNumber"));
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public Response<Account> checkAccountRole(String username) {
//        String sql = "SELECT * FROM Account WHERE Username = ?";
//        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setString(1, username);
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    Account account = new Account(
//                            rs.getInt("userid"),
//                            rs.getString("Username"),
//                            rs.getString("Password"),
//                            rs.getInt("roleid"),
//                            rs.getBoolean("status"), rs.getString("Email"),
//                            rs.getString("FullName"),
//                            rs.getString("PhoneNumber"));
//                    return new Response<>(account, true, MessageConstant.MESSAGE_SUCCESS);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return new Response<>(null, false, MessageConstant.MESSAGE_Fail);
//    }
//
//    public boolean updateUser(int id, String username, String email, String password, int roleId,
//            String fullName, String phoneNumber, String address) {
//        String sql = "UPDATE Account SET Username = ?, Email = ?, Password = ?, roleid = ?, FullName = ?, PhoneNumber = ?, Address = ? WHERE userid = ?";
//
//        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setString(1, username);
//            ps.setString(2, email);
//            ps.setString(3, password);
//            ps.setInt(4, roleId);
//            ps.setString(5, fullName);
//            ps.setString(6, phoneNumber);
//            ps.setString(7, address);
//            ps.setInt(8, id);
//
//            int rowsUpdated = ps.executeUpdate();
//            return rowsUpdated > 0;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public boolean deleteAccountById(int id) {
//        String sql = "DELETE FROM Account WHERE userid = ?";
//        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setInt(1, id);
//            int rowsDeleted = ps.executeUpdate();
//            return rowsDeleted > 0;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public boolean isAccountDisabled(String username) {
//        String sql = "SELECT status FROM Account WHERE username = ?";
//        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, username);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    return !rs.getBoolean("status"); // status = false (0) nghĩa là bị vô hiệu hóa
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public boolean updateUserStatus(int userId, boolean status) {
//        String sql = "UPDATE Account SET status = ? WHERE userid = ?";
//        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setBoolean(1, status);
//            stmt.setInt(2, userId);
//            int rowsUpdated = stmt.executeUpdate();
//            return rowsUpdated > 0;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public String getFullNameById(int userId) {
//        String sql = "SELECT FullName FROM Account WHERE userid = ?";
//        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setInt(1, userId);
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    return rs.getString("FullName");
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null; // Trả về null nếu không tìm thấy hoặc có lỗi
//    }
//
//    //
//    public Account getAccountByEmail(String email) {
//        String sql = "SELECT * FROM Account WHERE email = ?";
//        try (Connection conn = DBContext.getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setString(1, email);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                Account account = new Account(
//                    rs.getInt("userid"),
//                    rs.getString("username"),
//                    rs.getString("password"),
//                    rs.getInt("roleid"),
//                    rs.getBoolean("status"), rs.getString("email"),
//                    rs.getString("FullName"),
//                    rs.getString("PhoneNumber"));
//                System.out.println("Retrieved account from DB - Email: " + email + ", FullName: " + account.getFullName());
//                return account;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public boolean createAccountGoogle(String username, String email) {
//        if (getAccountByEmail(email) != null) {
//            return false;
//        }
//
//        String query = "INSERT INTO Account (username, password, email, roleid, status, FullName) " +
//                       "VALUES (?,'',?,2,1,?)";
//        try (Connection conn = DBContext.getConnection();
//             PreparedStatement ps = conn.prepareStatement(query)) {
//            ps.setString(1, username);
//            ps.setString(2, email);
//            ps.setString(3, username);
//            int rowsAffected = ps.executeUpdate();
//            System.out.println("Inserted Google account - Username: " + username + ", Email: " + email + ", Rows affected: " + rowsAffected);
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("lỗi");
//        }
//        return false;
//    }
////
//    
//    
//}


package dal.accountDao;

import constant.MessageConstant;
import model.account.Account;
import dal.DBContext;
import dto.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.OrderDayData.OrderDayData; // Giả sử đây là class tồn tại
import ultils.PasswordHasher; // Thêm import để dùng hashPassword

/**
 *
 * @author ASUS
 */
public class AccountDao extends DBContext {

    // Thêm từ dal.adminDao.AccountDAO
    private String hashPassword(String password) {
        return PasswordHasher.toSHA1(password);
    }

    public Account checkLogin(String username, String password) {
        String sql = "SELECT * FROM Account WHERE Username = ? AND Password = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Account(
                            rs.getInt("userid"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getInt("roleid"),
                            rs.getBoolean("status"), rs.getString("email"),
                            rs.getString("FullName"),
                            rs.getString("PhoneNumber"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Đăng nhập thất bại
    }

    public Account getAccountByUsername(String username) {
        String sql = "SELECT * FROM Account WHERE Username = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Account(
                            rs.getInt("userid"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getInt("roleid"),
                            rs.getBoolean("status"), rs.getString("email"),
                            rs.getString("FullName"),
                            rs.getString("PhoneNumber"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Không tìm thấy username => có thể đăng ký
    }

    public boolean registerAccount(String username, String password, String email, int roleid,
            boolean status, String fullName, String phoneNumber, String address) {
        if (getAccountByUsername(username) != null) {
            return false; // Không cho phép đăng ký nếu username đã tồn tại
        }

        String sql = "INSERT INTO Account (Username, Password, Email, roleid, status, FullName, PhoneNumber, Address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, email);
            ps.setInt(4, roleid);
            ps.setBoolean(5, status);
            ps.setString(6, fullName);
            ps.setString(7, phoneNumber);
            ps.setString(8, address);

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Account> getAllAccounts() {
        List<Account> accountList = new ArrayList<>();
        String sql = "SELECT * FROM Account";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Account account = new Account(
                        rs.getInt("userid"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("roleid"),
                        rs.getBoolean("status"), rs.getString("email"),
                        rs.getString("FullName"),
                        rs.getString("PhoneNumber"));
                accountList.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountList;
    }

    public Account getAccountById(int id) {
        String sql = "SELECT * FROM Account WHERE userid = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Account(
                            rs.getInt("userid"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getInt("roleid"),
                            rs.getBoolean("status"), rs.getString("email"),
                            rs.getString("FullName"),
                            rs.getString("PhoneNumber"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response<Account> checkAccountRole(String username) {
        String sql = "SELECT * FROM Account WHERE Username = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Account account = new Account(
                            rs.getInt("userid"),
                            rs.getString("Username"),
                            rs.getString("Password"),
                            rs.getInt("roleid"),
                            rs.getBoolean("status"), rs.getString("Email"),
                            rs.getString("FullName"),
                            rs.getString("PhoneNumber"));
                    return new Response<>(account, true, MessageConstant.MESSAGE_SUCCESS);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Response<>(null, false, MessageConstant.MESSAGE_Fail);
    }

    public boolean updateUser(int id, String username, String email, String password, int roleId,
            String fullName, String phoneNumber, String address) {
        String sql = "UPDATE Account SET Username = ?, Email = ?, Password = ?, roleid = ?, FullName = ?, PhoneNumber = ?, Address = ? WHERE userid = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setInt(4, roleId);
            ps.setString(5, fullName);
            ps.setString(6, phoneNumber);
            ps.setString(7, address);
            ps.setInt(8, id);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAccountById(int id) {
        String sql = "DELETE FROM Account WHERE userid = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isAccountDisabled(String username) {
        String sql = "SELECT status FROM Account WHERE username = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return !rs.getBoolean("status"); // status = false (0) nghĩa là bị vô hiệu hóa
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUserStatus(int userId, boolean status) {
        String sql = "UPDATE Account SET status = ? WHERE userid = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, status);
            stmt.setInt(2, userId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getFullNameById(int userId) {
        String sql = "SELECT FullName FROM Account WHERE userid = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("FullName");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy hoặc có lỗi
    }

    public Account getAccountByEmail(String email) {
        String sql = "SELECT * FROM Account WHERE email = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account account = new Account(
                    rs.getInt("userid"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getInt("roleid"),
                    rs.getBoolean("status"), rs.getString("email"),
                    rs.getString("FullName"),
                    rs.getString("PhoneNumber"));
                System.out.println("Retrieved account from DB - Email: " + email + ", FullName: " + account.getFullName());
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createAccountGoogle(String username, String email) {
        if (getAccountByEmail(email) != null) {
            return false;
        }

        String query = "INSERT INTO Account (username, password, email, roleid, status, FullName) " +
                       "VALUES (?,'',?,2,1,?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, username);
            int rowsAffected = ps.executeUpdate();
            System.out.println("Inserted Google account - Username: " + username + ", Email: " + email + ", Rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("lỗi");
        }
        return false;
    }

    // Thêm từ dal.adminDao.AccountDAO
    public List<Account> getAccountsByPage(int page) {
        List<Account> accounts = new ArrayList<>();
        int pageSize = 10;
        int offset = (page - 1) * pageSize;
        String sql = "SELECT * FROM Account ORDER BY userid OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, offset);
            stmt.setInt(2, pageSize);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Account account = new Account(
                        rs.getInt("userid"),
                        rs.getString("Username"),
                        rs.getString("password"),
                        rs.getInt("roleid"),
                        rs.getBoolean("status"),
                        rs.getString("Email"),
                        rs.getString("FullName"),
                        rs.getString("PhoneNumber"));
                account.setAddress(rs.getString("Address"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy danh sách tài khoản: " + e.getMessage(), e);
        }
        return accounts;
    }

    public int getTotalAccounts() {
        String sql = "SELECT COUNT(*) FROM Account";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi đếm tổng số tài khoản: " + e.getMessage(), e);
        }
    }

    public int getTotalActiveUsers() {
        String sql = "SELECT COUNT(*) FROM Account WHERE status = 1";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi đếm số người dùng đang hoạt động: " + e.getMessage(), e);
        }
    }

    public void addUser(Account account) {
        String sql = "INSERT INTO Account (Username, Password, Email, roleid, status, FullName, PhoneNumber, Address) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getUsername());
            String hashedPassword = hashPassword(account.getPassword());
            System.out.println("Saving hashed password for " + account.getUsername() + ": " + hashedPassword);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, account.getEmail());
            stmt.setInt(4, account.getRoleId());
            stmt.setBoolean(5, account.isStatus());
            stmt.setString(6, account.getFullName());
            stmt.setString(7, account.getPhoneNumber());
            stmt.setString(8, account.getAddress());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thêm tài khoản: " + e.getMessage(), e);
        }
    }

    public void updateUser(Account account) {
        String sql = "UPDATE Account SET Username = ?, Password = ?, Email = ?, roleid = ?, status = ?, "
                + "FullName = ?, PhoneNumber = ?, Address = ? WHERE userid = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getUsername());
            String hashedPassword = hashPassword(account.getPassword());
            System.out.println("Updating hashed password for " + account.getUsername() + ": " + hashedPassword);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, account.getEmail());
            stmt.setInt(4, account.getRoleId());
            stmt.setBoolean(5, account.isStatus());
            stmt.setString(6, account.getFullName());
            stmt.setString(7, account.getPhoneNumber());
            stmt.setString(8, account.getAddress());
            stmt.setInt(9, account.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi cập nhật tài khoản: " + e.getMessage(), e);
        }
    }

    public void updateAccountProfile(Account account) {
        String sql = "UPDATE Account SET Email = ?, FullName = ?, PhoneNumber = ?, Address = ? WHERE userid = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getEmail());
            stmt.setString(2, account.getFullName());
            stmt.setString(3, account.getPhoneNumber());
            stmt.setString(4, account.getAddress());
            stmt.setInt(5, account.getUserId());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Không tìm thấy tài khoản với userId: " + account.getUserId());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi cập nhật hồ sơ tài khoản: " + e.getMessage(), e);
        }
    }

    public void toggleUserStatus(int userId) {
        String sql = "UPDATE Account SET status = CASE WHEN status = 1 THEN 0 ELSE 1 END WHERE userid = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Không tìm thấy tài khoản với userId: " + userId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thay đổi trạng thái tài khoản: " + e.getMessage(), e);
        }
    }

    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM Account WHERE Username = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi kiểm tra tên người dùng: " + e.getMessage(), e);
        }
    }

    public List<Account> getAccountsByRoleAndPage(int roleId, int page) {
        List<Account> accounts = new ArrayList<>();
        int pageSize = 10;
        int offset = (page - 1) * pageSize;
        String sql = "SELECT * FROM Account WHERE roleid = ? ORDER BY userid OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roleId);
            stmt.setInt(2, offset);
            stmt.setInt(3, pageSize);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Account account = new Account(
                        rs.getInt("userid"),
                        rs.getString("Username"),
                        rs.getString("password"),
                        rs.getInt("roleid"),
                        rs.getBoolean("status"),
                        rs.getString("Email"),
                        rs.getString("FullName"),
                        rs.getString("PhoneNumber"));
                account.setAddress(rs.getString("Address"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy danh sách tài khoản theo role: " + e.getMessage(), e);
        }
        return accounts;
    }

    public int getTotalAccountsByRole(int roleId) {
        String sql = "SELECT COUNT(*) FROM Account WHERE roleid = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roleId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi đếm tổng số tài khoản theo role: " + e.getMessage(), e);
        }
    }

    public double getTotalRevenue() {
        String sql = "SELECT COALESCE(SUM(od.subtotal), 0) as total FROM OrderDetails od "
                + "JOIN Orders o ON od.order_id = o.id "
                + "WHERE o.status = 'Paid'";
        try (Connection conn = getConnection()) {
            System.out.println("Connection established: " + conn);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                System.out.println("Executing SQL: " + sql);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        double total = rs.getDouble("total");
                        System.out.println("Calculated total revenue: " + total);
                        return total;
                    } else {
                        System.out.println("No rows returned from query.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Returning 0.0 due to no data or error.");
        return 0.0;
    }

    public int getCompletedOrderCount() {
        String sql = "SELECT COUNT(*) as total_orders FROM Orders WHERE status = 'Paid'";
        try (Connection conn = getConnection()) {
            System.out.println("Connection established for getCompletedOrderCount: " + conn);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                System.out.println("Executing SQL for getCompletedOrderCount: " + sql);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt("total_orders");
                        System.out.println("Total completed orders: " + count);
                        return count;
                    } else {
                        System.out.println("No rows returned from getCompletedOrderCount query.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in getCompletedOrderCount: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Returning 0 due to no data or error in getCompletedOrderCount.");
        return 0;
    }

    public List<OrderDayData> getCompletedOrdersByDay(String startDate, String endDate) {
        List<OrderDayData> orderDayDataList = new ArrayList<>();
        String sql = "SELECT DAY(order_date) AS OrderDay, COUNT(*) AS OrderCount "
                + "FROM Orders "
                + "WHERE Status = 'Paid' AND order_date BETWEEN ? AND ? "
                + "GROUP BY DAY(order_date) "
                + "ORDER BY OrderDay";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, startDate);
            ps.setString(2, endDate);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderDayData data = new OrderDayData();
                data.setOrderDay(rs.getInt("OrderDay"));
                data.setOrderCount(rs.getInt("OrderCount"));
                orderDayDataList.add(data);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy số đơn hàng hoàn thành theo ngày: " + e.getMessage(), e);
        }
        return orderDayDataList;
    }
}