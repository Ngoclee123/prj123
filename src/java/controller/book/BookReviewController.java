package controller.book;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.book.Book;
import model.review.Review;
import service.BookService;
import service.ReviewService;

import java.io.IOException;
import java.util.List;

@WebServlet("/BookReviewController")
public class BookReviewController extends HttpServlet {

    private final ReviewService reviewService;
    private final BookService bookService;

    public BookReviewController() {
        this.reviewService = new ReviewService();
        this.bookService = new BookService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bookIdStr = request.getParameter("bookId");
        String pageStr = request.getParameter("page");
        String pageSizeStr = request.getParameter("pageSize");

        int page = (pageStr != null && !pageStr.isEmpty()) ? Integer.parseInt(pageStr) : 1;
        int pageSize = (pageSizeStr != null && !pageSizeStr.isEmpty()) ? Integer.parseInt(pageSizeStr) : 5;

        if (bookIdStr != null) {
            try {
                int bookId = Integer.parseInt(bookIdStr);
                List<Review> reviews = reviewService.getReviewsByBookId(bookId, pageSize, page);
                int totalPages = reviewService.getTotalPages(bookId, pageSize);

                request.setAttribute("reviews", reviews);
                request.setAttribute("bookId", bookId);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("pageSize", pageSize);
                request.getRequestDispatcher("detail/detail.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid book ID or pagination parameters");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Book ID is required");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bookIdStr = request.getParameter("bookId");
        String userIdStr = request.getParameter("userId");
        String ratingStr = request.getParameter("rating");
        String comment = request.getParameter("comment");
        String pageStr = request.getParameter("page");
        String pageSizeStr = request.getParameter("pageSize");

        int page = (pageStr != null && !pageStr.isEmpty()) ? Integer.parseInt(pageStr) : 1;
        int pageSize = (pageSizeStr != null && !pageSizeStr.isEmpty()) ? Integer.parseInt(pageSizeStr) : 5;

        if (bookIdStr != null && userIdStr != null && ratingStr != null && comment != null) {
            try {
                int bookId = Integer.parseInt(bookIdStr);
                int userId = Integer.parseInt(userIdStr);
                int rating = Integer.parseInt(ratingStr);

                boolean success = reviewService.addReview(userId, bookId, rating, comment);
                if (success) {
                    Book book = bookService.getBookById(bookId);
                    List<Review> reviews = reviewService.getReviewsByBookId(bookId, pageSize, page);
                    int totalPages = reviewService.getTotalPages(bookId, pageSize);

                    request.setAttribute("book", book);
                    request.setAttribute("reviews", reviews);
                    request.setAttribute("message", "Đánh giá của bạn đã được gửi thành công!");
                    request.setAttribute("currentPage", page);
                    request.setAttribute("totalPages", totalPages);
                    request.setAttribute("pageSize", pageSize);
                    request.getRequestDispatcher("detail/detail.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to add review");
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input data");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required parameters");
        }
    }
}