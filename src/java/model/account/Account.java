package model.account;

public class Account {
    private int userId;        // Đổi tên từ id thành userId để khớp với tên cột trong SQL
    private String username;
    private String password;
    private String email;
    private int roleId;
    private boolean status;
    private String fullName;   // Thêm FullName
    private String phoneNumber;// Thêm PhoneNumber
    private String address;    // Thêm Address

    // Constructor đầy đủ
    public Account(int userId, String username, String password, int roleId, boolean status, String email, String fullName, String phoneNumber) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roleId = roleId;
        this.status = status;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

 
    
    
    
    
    // Constructor mặc định
    public Account() {
        this.roleId = 2;    // DEFAULT 2 từ SQL
        this.status = true; // DEFAULT 1 từ SQL (BIT: 1 = true)
    }

   

    // Getters và Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Optional: toString() để debug
    @Override
    public String toString() {
        return "Account{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", roleId=" + roleId +
                ", status=" + status +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}