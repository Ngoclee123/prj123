/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.book;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import model.book.Book;
import service.BookService;

/**
 *
 * @author ASUS
 */
@WebServlet(name="BookController", urlPatterns={"/bookc"})
public class BookController extends HttpServlet {

    BookService bookService;

    public void init() {
        bookService = new BookService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "search":
                    searchBook(request, response);
                    break;
                case "LoadMore":
                    loadMore(request, response);
                    break;
                // Thêm các action khác nếu cần
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

private void searchBook(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, IOException, ServletException {
    String keyword = request.getParameter("searchKeyword");

    // Get the search results based on the keyword
    List<Book> searchResults = bookService.searchBooks(keyword);

    // If no results, fetch suggested books
    
    List<Book> suggestedBooks = bookService.getSuggestedBooks(); // Assuming you have a method for getting random books
System.out.println("Suggested Books: " + suggestedBooks.size()); // This helps you debug the size of the list
request.setAttribute("suggestedBooks", suggestedBooks);
    // Set attributes for the JSP
    request.setAttribute("bookList", searchResults); // Set search results
    request.setAttribute("suggestedBooks", suggestedBooks); // Set suggested books
    request.setAttribute("searchKeyword", keyword); // Set the search keyword

    // Forward the request to display.jsp
    RequestDispatcher dispatcher = request.getRequestDispatcher("/newpage/display.jsp");
    dispatcher.forward(request, response);
}


    private void loadMore(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String keyword = request.getParameter("searchKeyword");
        String sortType = request.getParameter("sort");
        String categoryIdStr = request.getParameter("category");
        Integer categoryId = (categoryIdStr != null && !categoryIdStr.isEmpty()) ? Integer.parseInt(categoryIdStr) : null;
        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");
        Double minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? Double.parseDouble(minPriceStr) : null;
        Double maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty()) ? Double.parseDouble(maxPriceStr) : null;

        // Tìm kiếm sách theo các bộ lọc
        List<Book> searchResults = bookService.searchBooks(keyword, sortType, categoryId, minPrice, maxPrice);

        // Nếu không có kết quả tìm kiếm, lấy sản phẩm gợi ý
        List<Book> suggestedBooks = new ArrayList<>();
        if (searchResults.isEmpty()) {
            suggestedBooks = bookService.getSuggestedBooks(); // Phương thức gợi ý sản phẩm
        }

        // Lưu dữ liệu vào request
        request.setAttribute("bookList", searchResults);
        request.setAttribute("suggestedBooks", suggestedBooks);
        request.setAttribute("searchKeyword", keyword);
        request.setAttribute("sortType", sortType);
        request.setAttribute("selectedCategory", categoryId);
        request.setAttribute("minPrice", minPrice);
        request.setAttribute("maxPrice", maxPrice);

        // Chuyển hướng đến `display.jsp`
        RequestDispatcher dispatcher = request.getRequestDispatcher("/newpage/display.jsp");
        dispatcher.forward(request, response);
    }
}

