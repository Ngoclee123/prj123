package controller.seller;

import service.interfaces.ProductService;
import service.interfaces.BookCategoryService;
import service.ProductServiceImpl;
import service.BookCategoryServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.product.Product;
import model.bookCategory.BookCategory;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/SellerServlet")
public class SellerServlet extends HttpServlet {
    private ProductService bookService = new ProductServiceImpl();
    private BookCategoryService categoryService = new BookCategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("editProduct".equals(action)) {
            showEditProductForm(request, response);
        } else if ("toggleProductStatus".equals(action)) {
            toggleProductStatus(request, response);
        } else if ("addProductForm".equals(action)) {
            showAddProductForm(request, response);
        } else {
            listBooks(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("addProduct".equals(action)) {
                addProduct(request, response);
            } else if ("updateProduct".equals(action)) {
                updateProduct(request, response);
            } else if ("toggleProductStatus".equals(action)) {
                toggleProductStatus(request, response);
            } else {
                listBooks(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Đã xảy ra lỗi: " + e.getMessage());
            listBooks(request, response);
        }
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            double price = parseDouble(request.getParameter("price"), 0.0);
            String description = request.getParameter("description");
            int stock = parseInt(request.getParameter("stock"), 0);
            LocalDate publishDate = parseDate(request.getParameter("publish_date"));
            String imgUrl = request.getParameter("img_url");
            Integer categoryId = parseInteger(request.getParameter("category_id"));
            boolean status = request.getParameter("status") != null;

            Product book = new Product(0, title, author, price, description, stock, publishDate, imgUrl, categoryId, status);
            bookService.addBook(book);
            request.setAttribute("message", "Thêm sản phẩm thành công!");
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi khi thêm sản phẩm: " + e.getMessage());
        }
        listBooks(request, response);
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = parseInt(request.getParameter("id"), -1);
            if (id == -1) throw new IllegalArgumentException("ID sản phẩm không hợp lệ");

            String title = request.getParameter("title");
            String author = request.getParameter("author");
            double price = parseDouble(request.getParameter("price"), 0.0);
            String description = request.getParameter("description");
            int stock = parseInt(request.getParameter("stock"), 0);
            LocalDate publishDate = parseDate(request.getParameter("publish_date"));
            String imgUrl = request.getParameter("img_url");
            Integer categoryId = parseInteger(request.getParameter("category_id"));
            boolean status = request.getParameter("status") != null;

            Product book = new Product(id, title, author, price, description, stock, publishDate, imgUrl, categoryId, status);
            bookService.updateBook(book);
            request.setAttribute("message", "Cập nhật sản phẩm thành công!");
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi khi cập nhật sản phẩm: " + e.getMessage());
        }
        listBooks(request, response);
    }

    private void toggleProductStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = parseInt(request.getParameter("id"), -1);
            if (id == -1) throw new IllegalArgumentException("ID sản phẩm không hợp lệ");

            bookService.toggleBookStatus(id);
            request.setAttribute("message", "Thay đổi trạng thái sản phẩm thành công!");
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi khi thay đổi trạng thái sản phẩm: " + e.getMessage());
        }
        listBooks(request, response);
    }

    private void showEditProductForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = parseInt(request.getParameter("id"), -1);
        Product book = bookService.getBookById(id);
        if (book == null) {
            request.setAttribute("error", "Sản phẩm không tồn tại!");
            listBooks(request, response);
        } else {
            List<BookCategory> categories = categoryService.getAllCategories();
            request.setAttribute("product", book);
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/seller/editProduct.jsp").forward(request, response);
        }
    }

    private void showAddProductForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BookCategory> categories = categoryService.getAllCategories();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/seller/addProduct.jsp").forward(request, response);
    }

    private void listBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productPage = parseInt(request.getParameter("productPage"), 1);

        List<Product> books = bookService.getBooksByPage(productPage);
        int totalBooks = bookService.getTotalBooks();
        int productPageCount = (int) Math.ceil((double) totalBooks / 10);

        if (productPage > productPageCount) productPage = productPageCount;
        if (productPage < 1) productPage = 1;

        request.setAttribute("products", books);
        request.setAttribute("productPage", productPage);
        request.setAttribute("productPageCount", productPageCount);

        request.getRequestDispatcher("/seller/sellerpage.jsp").forward(request, response);
    }

    private double parseDouble(String value, double defaultValue) {
        try {
            return value != null && !value.isEmpty() ? Double.parseDouble(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return value != null && !value.isEmpty() ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private Integer parseInteger(String value) {
        try {
            return value != null && !value.isEmpty() ? Integer.parseInt(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private LocalDate parseDate(String value) {
        try {
            return value != null && !value.isEmpty() ? LocalDate.parse(value) : null;
        } catch (Exception e) {
            return null;
        }
    }
}