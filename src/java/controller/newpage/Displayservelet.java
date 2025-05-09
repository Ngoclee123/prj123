/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.newpage;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.book.Book;
import model.bookCategory.BookCategory;
import service.BookService;
import service.Newpage;

/**
 *
 * @author Hi
 */
@WebServlet(name="Displayservelet", urlPatterns={"/display"})
public class Displayservelet extends HttpServlet {
       
    private Newpage newpageService;
        public void init() {
       newpageService = new Newpage();
}
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Displayservelet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Displayservelet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {        
    int page = 1;
    String pageStr = request.getParameter("page");
    if (pageStr != null) {
        try {
            page = Integer.parseInt(pageStr);
        } catch (NumberFormatException e) {
            page = 1;
        }
    }

    // Lấy giá trị sắp xếp theo giá (asc / desc)
    String sortType = request.getParameter("sort");
// Lấy giá trị lọc theo thể loại
    String categoryIdStr = request.getParameter("category");
    Integer categoryId = (categoryIdStr != null && !categoryIdStr.isEmpty()) ? Integer.parseInt(categoryIdStr) : null;

    // Lấy giá trị lọc theo khoảng giá
    String minPriceStr = request.getParameter("minPrice");
    String maxPriceStr = request.getParameter("maxPrice");

    Double minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? Double.parseDouble(minPriceStr) : null;
    Double maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty()) ? Double.parseDouble(maxPriceStr) : null;

    // Gọi service để lấy danh sách sách phù hợp với các bộ lọc
    List<Book> booksList = newpageService.getBooksByPage(page, sortType, categoryId, minPrice, maxPrice);
    int totalPages = newpageService.getTotalPages(categoryId, minPrice, maxPrice);
    List<BookCategory> categoryList = newpageService.getAllCategories();

    // Gán dữ liệu vào request để chuyển đến JSP
    request.setAttribute("bookList", booksList);
    request.setAttribute("currentPage", page);
    request.setAttribute("totalPages", totalPages);
    request.setAttribute("sortType", sortType);
    request.setAttribute("selectedCategory", categoryId);
    request.setAttribute("minPrice", minPrice);
    request.setAttribute("maxPrice", maxPrice);
    request.setAttribute("categoryList", categoryList);

    // Chuyển hướng đến display.jsp
    request.getRequestDispatcher("/newpage/display.jsp").forward(request, response);
}

    

    
  

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}