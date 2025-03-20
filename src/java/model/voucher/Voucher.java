package model.voucher;

public class Voucher {

    private int id;
    private String code;
    private double discount;
    private boolean status;

    public Voucher(int id, String code, double discount, boolean status) {
        this.id = id;
        this.code = code;
        this.discount = discount;
        this.status = status;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public double getDiscount() {
        return discount;
    }

    public boolean isStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Voucher{" + "id=" + id + ", code=" + code + ", discount=" + discount + ", status=" + status + '}';
    }
    
}
