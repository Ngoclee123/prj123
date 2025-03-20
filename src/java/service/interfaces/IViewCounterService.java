package service.interfaces;

import java.util.Map;
import model.book.Book;

public interface IViewCounterService {
    Map<Book, Integer> getUserClicks(int userId);
}
