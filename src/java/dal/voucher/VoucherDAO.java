package dal.voucher;

import dal.DBContext;
import model.voucher.Voucher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VoucherDAO implements IVoucherDAO {
    private static final Logger LOGGER = Logger.getLogger(VoucherDAO.class.getName());

    @Override
    public List<Voucher> getVouchersByUserId(int userId) {
        List<Voucher> vouchers = new ArrayList<>();
        String sql = "SELECT v.id, v.code, v.discount, v.status FROM Vouchers v " +
                     "JOIN VoucherOfUser vu ON v.id = vu.voucher_id " +
                     "WHERE vu.user_id = ? AND v.status = 1";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Voucher voucher = new Voucher(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getDouble("discount"),
                        rs.getBoolean("status")
                );
                vouchers.add(voucher);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy danh sách voucher cho user {0}: {1}", new Object[]{userId, e.getMessage()});
            e.printStackTrace();
        }
        return vouchers;
    }

    @Override
    public boolean isVoucherValidForUser(int userId, String code) {
        String sql = "SELECT COUNT(*) FROM Vouchers v " +
                     "JOIN VoucherOfUser vu ON v.id = vu.voucher_id " +
                     "WHERE vu.user_id = ? AND v.code = ? AND v.status = 1 AND vu.used = 0";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi kiểm tra voucher {0} cho user {1}: {2}", new Object[]{code, userId, e.getMessage()});
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public double getDiscountByCode(String code) {
        String sql = "SELECT discount FROM Vouchers WHERE code = ? AND status = 1";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("discount");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy discount của voucher {0}: {1}", new Object[]{code, e.getMessage()});
            e.printStackTrace();
        }
        return 0.0;
    }

    @Override
    public void markVoucherAsUsed(int userId, String code) {
        String sql = "UPDATE VoucherOfUser  " +
                     "SET used = 1 " +
                     "WHERE user_id = ? AND voucher_id = (SELECT id FROM Vouchers WHERE code = ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, code);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi đánh dấu voucher {0} là đã dùng cho user {1}: {2}", new Object[]{code, userId, e.getMessage()});
            e.printStackTrace();
        }
    }

    @Override
    public List<Voucher> getAvailableVouchers() {
        List<Voucher> vouchers = new ArrayList<>();
        String sql = "SELECT id, code, discount, status FROM Vouchers WHERE status = 1";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Voucher voucher = new Voucher(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getDouble("discount"),
                        rs.getBoolean("status")
                );
                vouchers.add(voucher);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy danh sách voucher khả dụng: {0}", e.getMessage());
            e.printStackTrace();
        }
        return vouchers;
    }

    @Override
    public void assignVoucherToUser(int userId, int voucherId) {
        String sql = "INSERT INTO VoucherOfUser (user_id, voucher_id) VALUES (?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, voucherId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                LOGGER.log(Level.INFO, "Đã gán voucher {0} cho user {1}", new Object[]{voucherId, userId});
            } else {
                LOGGER.log(Level.WARNING, "Không thể gán voucher {0} cho user {1}: Không có hàng nào bị ảnh hưởng", new Object[]{voucherId, userId});
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi gán voucher {0} cho user {1}: {2}", new Object[]{voucherId, userId, e.getMessage()});
            e.printStackTrace();
        }
    }

     
    
    @Override
    public List<Voucher> getAvailableVouchersForUser(int userId) {
        List<Voucher> vouchers = new ArrayList<>();
        String sql = "SELECT v.id, v.code, v.discount, v.status " +
                     "FROM Vouchers v " +
                     "JOIN VoucherOfUser vu ON v.id = vu.voucher_id " +
                     "WHERE vu.user_id = ? AND vu.used = 0 AND v.status = 1";
        try (Connection conn = DBContext.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Voucher voucher = new Voucher(
                    rs.getInt("id"),
                    rs.getString("code"),
                    rs.getDouble("discount"),
                    rs.getBoolean("status")
                );
                vouchers.add(voucher);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy danh sách voucher khả dụng cho user {0}: {1}", 
                       new Object[]{userId, e.getMessage()});
            e.printStackTrace();
        }
        return vouchers;
    }

    @Override
    public void markVoucherAsUsed(int userId, int voucherId) {
        String sql = "UPDATE VoucherOfUser SET used = 1 WHERE user_id = ? AND voucher_id = ?";
        try (Connection conn = DBContext.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, voucherId);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi đánh dấu voucher {0} đã dùng cho user {1}: {2}", 
                       new Object[]{voucherId, userId, e.getMessage()});
            e.printStackTrace();
        }
    }
}