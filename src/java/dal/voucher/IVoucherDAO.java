package dal.voucher;

import model.voucher.Voucher;
import java.util.List;

public interface IVoucherDAO {

    List<Voucher> getVouchersByUserId(int userId);

    boolean isVoucherValidForUser(int userId, String code);

    double getDiscountByCode(String code);

    void markVoucherAsUsed(int userId, String code);

    List<Voucher> getAvailableVouchers();

    void assignVoucherToUser(int userId, int voucherId);

    List<Voucher> getAvailableVouchersForUser(int userId);

    void markVoucherAsUsed(int userId, int voucherId);
}
