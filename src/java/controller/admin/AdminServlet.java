package controller.admin;

import service.interfaces.AccountService;
import service.interfaces.ProductService;
import service.interfaces.BookCategoryService;
import service.AccountServiceImpl;
import service.ProductServiceImpl;
import service.BookCategoryServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.account.Account;
import model.product.Product;
import model.bookCategory.BookCategory;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
    private ProductService bookService = new ProductServiceImpl();
    private AccountService accountService = new AccountServiceImpl();
    private BookCategoryService bookCategoryService = new BookCategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("toggleUserStatus".equals(action)) {
            toggleUserStatus(request, response);
        } else if ("editProduct".equals(action)) {
            showEditProductForm(request, response);
        } else if ("editUser".equals(action)) {
            showEditUserForm(request, response);
        } else if ("toggleProductStatus".equals(action)) {
            toggleProductStatus(request, response);
        } else if ("addProductForm".equals(action)) {
            showAddProductForm(request, response);
        } else {
            listBooksAndUsers(request, response);
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
            } else if ("addUser".equals(action)) {
                addUser(request, response);
            } else if ("updateUser".equals(action)) {
                updateUser(request, response);
            } else if ("toggleUserStatus".equals(action)) {
                toggleUserStatus(request, response);
            } else {
                listBooksAndUsers(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Đã xảy ra lỗi: " + e.getMessage());
            listBooksAndUsers(request, response);
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
            request.setAttribute("activeTab", "products");
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi khi thêm sản phẩm: " + e.getMessage());
            List<BookCategory> categories = bookCategoryService.getAllCategories();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/admin/createProduct.jsp").forward(request, response);
            return;
        }
        listBooksAndUsers(request, response);
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
            request.setAttribute("activeTab", "products");
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi khi cập nhật sản phẩm: " + e.getMessage());
            List<BookCategory> categories = bookCategoryService.getAllCategories();
            request.setAttribute("categories", categories);
            request.setAttribute("product", bookService.getBookById(parseInt(request.getParameter("id"), -1)));
            request.getRequestDispatcher("/admin/editProduct.jsp").forward(request, response);
            return;
        }
        listBooksAndUsers(request, response);
    }

    private void toggleProductStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = parseInt(request.getParameter("id"), -1);
            if (id == -1) throw new IllegalArgumentException("ID sản phẩm không hợp lệ");

            bookService.toggleBookStatus(id);
            request.setAttribute("message", "Thay đổi trạng thái sản phẩm thành công!");
            request.setAttribute("activeTab", "products");
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi khi thay đổi trạng thái sản phẩm: " + e.getMessage());
        }
        listBooksAndUsers(request, response);
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            int roleId = parseInt(request.getParameter("roleid"), -1);
            boolean status = request.getParameter("status") != null;
            String fullName = request.getParameter("fullName");
            String phoneNumber = request.getParameter("phoneNumber");
            String address = request.getParameter("address");

            if (roleId == -1) throw new IllegalArgumentException("Vai trò không hợp lệ");

            if (accountService.isUsernameExists(username)) {
                throw new IllegalArgumentException("Tên người dùng '" + username + "' đã tồn tại!");
            }

            Account account = new Account(0, username, email, roleId, status, fullName, phoneNumber, address);
            account.setPassword(password);
            accountService.addUser(account);
            request.setAttribute("message", "Thêm người dùng thành công!");
            request.setAttribute("activeTab", "users");
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi khi thêm người dùng: " + e.getMessage());
            Account account = new Account(
                0,
                request.getParameter("username"),
                request.getParameter("email"),
                parseInt(request.getParameter("roleid"), -1),
                request.getParameter("status") != null,
                request.getParameter("fullName"),
                request.getParameter("phoneNumber"),
                request.getParameter("address")
            );
            account.setPassword(request.getParameter("password"));
            request.setAttribute("account", account);
            request.getRequestDispatcher("/admin/createUser.jsp").forward(request, response);
            return;
        }
        listBooksAndUsers(request, response);
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int userId = parseInt(request.getParameter("userid"), -1);
            if (userId == -1) throw new IllegalArgumentException("ID người dùng không hợp lệ");

            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            int roleId = parseInt(request.getParameter("roleid"), -1);
            boolean status = Boolean.parseBoolean(request.getParameter("status"));
            String fullName = request.getParameter("fullName");
            String phoneNumber = request.getParameter("phoneNumber");
            String address = request.getParameter("address");

            if (roleId == -1) throw new IllegalArgumentException("Vai trò không hợp lệ");

            Account existingAccount = accountService.getAccountById(userId);
            if (!existingAccount.getUsername().equals(username) && accountService.isUsernameExists(username)) {
                throw new IllegalArgumentException("Tên người dùng '" + username + "' đã tồn tại!");
            }

            Account account = new Account(userId, username, email, roleId, status, fullName, phoneNumber, address);
            account.setPassword(password);
            accountService.updateUser(account);
            request.setAttribute("message", "Cập nhật người dùng thành công!");
            request.setAttribute("activeTab", "users");
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi khi cập nhật người dùng: " + e.getMessage());
            Account account = new Account(
                parseInt(request.getParameter("userid"), -1),
                request.getParameter("username"),
                request.getParameter("email"),
                parseInt(request.getParameter("roleid"), -1),
                Boolean.parseBoolean(request.getParameter("status")),
                request.getParameter("fullName"),
                request.getParameter("phoneNumber"),
                request.getParameter("address")
            );
            account.setPassword(request.getParameter("password"));
            request.setAttribute("user", account);
            request.getRequestDispatcher("/admin/editUser.jsp").forward(request, response);
            return;
        }
        listBooksAndUsers(request, response);
    }

    private void toggleUserStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int userId = parseInt(request.getParameter("userid"), -1);
            if (userId == -1) throw new IllegalArgumentException("ID người dùng không hợp lệ");
            accountService.toggleUserStatus(userId);
            request.setAttribute("message", "Thay đổi trạng thái người dùng thành công!");
            request.setAttribute("activeTab", "users");
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi khi thay đổi trạng thái: " + e.getMessage());
        }
        listBooksAndUsers(request, response);
    }

    private void showEditProductForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = parseInt(request.getParameter("id"), -1);
        Product book = bookService.getBookById(id);
        List<BookCategory> categories = bookCategoryService.getAllCategories();
        request.setAttribute("product", book);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/admin/editProduct.jsp").forward(request, response);
    }

    private void showAddProductForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BookCategory> categories = bookCategoryService.getAllCategories();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/admin/createProduct.jsp").forward(request, response);
    }

    private void showEditUserForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = parseInt(request.getParameter("userid"), -1);
        Account account = accountService.getAccountById(id);
        if (account == null) {
            request.setAttribute("error", "Người dùng không tồn tại!");
            listBooksAndUsers(request, response);
        } else {
            request.setAttribute("user", account);
            request.getRequestDispatcher("/admin/editUser.jsp").forward(request, response);
        }
    }

    private void listBooksAndUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productPage = parseInt(request.getParameter("productPage"), 1);
        int userPage = parseInt(request.getParameter("userPage"), 1);
        String roleFilter = request.getParameter("roleFilter");

        List<Product> books = bookService.getBooksByPage(productPage);
        List<Account> accounts;
        int totalAccounts;
        int userPageCount;

        if (roleFilter != null && !roleFilter.equals("all")) {
            int roleId = parseInt(roleFilter, -1);
            if (roleId == -1) {
                accounts = accountService.getAccountsByPage(userPage);
                totalAccounts = accountService.getTotalAccounts();
            } else {
                accounts = accountService.getAccountsByRoleAndPage(roleId, userPage);
                totalAccounts = accountService.getTotalAccountsByRole(roleId);
            }
        } else {
            accounts = accountService.getAccountsByPage(userPage);
            totalAccounts = accountService.getTotalAccounts();
        }

        int totalBooks = bookService.getTotalBooks();
        int productPageCount = (int) Math.ceil((double) totalBooks / 10);
        userPageCount = (int) Math.ceil((double) totalAccounts / 10);

        if (productPage > productPageCount) productPage = productPageCount;
        if (userPage > userPageCount) userPage = userPageCount;
        if (productPage < 1) productPage = 1;
        if (userPage < 1) userPage = 1;

        request.setAttribute("products", books);
        request.setAttribute("users", accounts);
        request.setAttribute("productPage", productPage);
        request.setAttribute("userPage", userPage);
        request.setAttribute("productPageCount", productPageCount);
        request.setAttribute("userPageCount", userPageCount);
        request.setAttribute("roleFilter", roleFilter);

        String activeTab = (String) request.getAttribute("activeTab");
        if (activeTab == null) {
            activeTab = request.getParameter("activeTab");
        }
        request.setAttribute("activeTab", activeTab != null ? activeTab : "products");

        request.getRequestDispatcher("/admin/adminpage.jsp").forward(request, response);
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