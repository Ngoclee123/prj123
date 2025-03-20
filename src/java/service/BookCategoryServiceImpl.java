package service;

import dal.BookCategoryDao.BookCategoryDAO;
import service.interfaces.BookCategoryService;
import model.bookCategory.BookCategory;
import java.util.List;

public class BookCategoryServiceImpl implements BookCategoryService {
    private BookCategoryDAO bookCategoryDAO = new BookCategoryDAO();

    @Override
    public List<BookCategory> getAllCategories() {
        return bookCategoryDAO.getAllCategories();
    }
}