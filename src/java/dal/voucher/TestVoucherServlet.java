/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package dal.voucher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.voucher.Voucher;

@WebServlet("/testVoucher")
public class TestVoucherServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        VoucherDAO voucherDAO = new VoucherDAO();
        List<Voucher> vouchers = voucherDAO.getAvailableVouchersForUser(20); // Thay 1 bằng userId thực tế
        response.getWriter().println("Vouchers: " + vouchers.size());
        for (Voucher v : vouchers) {
            response.getWriter().println(v.getCode() + " - " + v.getDiscount());
        }
    }
}