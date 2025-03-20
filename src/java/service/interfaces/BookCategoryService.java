package service.interfaces;

import model.bookCategory.BookCategory;
import java.util.List;

public interface BookCategoryService {
    List<BookCategory> getAllCategories();
}