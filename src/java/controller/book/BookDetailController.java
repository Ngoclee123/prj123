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

@WebServlet("/BookDetailController")
public class BookDetailController extends HttpServlet {

    private final BookService bookService;
    private final ReviewService reviewService;

    public BookDetailController() {
        this.bookService = new BookService();
        this.reviewService = new ReviewService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bookId = request.getParameter("bookId");
        String pageStr = request.getParameter("page");
        String pageSizeStr = request.getParameter("pageSize");

        int page = (pageStr != null && !pageStr.isEmpty()) ? Integer.parseInt(pageStr) : 1;
        int pageSize = (pageSizeStr != null && !pageSizeStr.isEmpty()) ? Integer.parseInt(pageSizeStr) : 5;

        if (bookId != null) {
            try {
                int id = Integer.parseInt(bookId);
                Book book = bookService.getBookById(id);

                if (book != null) {
                    List<Review> reviews = reviewService.getReviewsByBookId(id, pageSize, page);
                    int totalPages = reviewService.getTotalPages(id, pageSize);

                    double averageRating = 0.0;
                    int reviewCount = reviewService.getTotalReviewsByBookId(id);
                    if (reviewCount > 0) {
                        List<Review> allReviews = reviewService.getReviewsByBookId(id);
                        int totalRating = 0;
                        for (Review review : allReviews) {
                            totalRating += review.getRating();
                        }
                        averageRating = (double) totalRating / reviewCount;
                    }

                    List<Book> relatedBooks = bookService.getRelatedBooks(book.getCategoryId(), id);
                    request.setAttribute("book", book);
                    request.setAttribute("reviews", reviews);
                    request.setAttribute("averageRating", averageRating);
                    request.setAttribute("reviewCount", reviewCount);
                    request.setAttribute("relatedBooks", relatedBooks);
                    request.setAttribute("currentPage", page);
                    request.setAttribute("totalPages", totalPages);
                    request.setAttribute("pageSize", pageSize);
                    request.getRequestDispatcher("detail/detail.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Book not found");
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid book ID or pagination parameters");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Book ID is required");
        }
    }
}