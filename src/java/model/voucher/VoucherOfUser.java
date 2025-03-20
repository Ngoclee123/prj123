package model.voucher;

public class VoucherOfUser {
    private int userId;
    private int voucherId;
    private String assignedDate; // Hoặc Date nếu bạn muốn sử dụng java.util.Date

    // Constructor
    public VoucherOfUser(int userId, int voucherId, String assignedDate) {
        this.userId = userId;
        this.voucherId = voucherId;
        this.assignedDate = assignedDate;
    }

    // Getters và Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public String getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(String assignedDate) {
        this.assignedDate = assignedDate;
    }

    @Override
    public String toString() {
        return "VoucherOfUser{userId=" + userId + ", voucherId=" + voucherId + ", assignedDate='" + assignedDate + "'}";
    }
}