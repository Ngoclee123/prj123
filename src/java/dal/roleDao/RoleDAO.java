package dal.roleDao;

import dal.DBContext;
import model.account.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {

    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT * FROM Role";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Role role = new Role(
                    rs.getInt("roleid"),
                    rs.getString("rolename") // Sửa từ name thành rolename
                );
                roles.add(role);
            }
            System.out.println("Đã lấy " + roles.size() + " vai trò từ cơ sở dữ liệu.");
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy danh sách vai trò: " + e.getMessage(), e);
        }
        return roles;
    }
}