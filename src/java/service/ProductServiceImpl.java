package service;

import dal.productDao.ProductkDAO;
import service.interfaces.ProductService;
import model.product.Product;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private ProductkDAO bookDAO = new ProductkDAO();

    @Override
    public void addBook(Product product) {
        bookDAO.addBook(product);
    }

    @Override
    public void updateBook(Product product) {
        bookDAO.updateBook(product);
    }

    @Override
    public void toggleBookStatus(int id) {
        bookDAO.toggleBookStatus(id);
    }

    @Override
    public Product getBookById(int id) {
        return bookDAO.getBookById(id);
    }

    @Override
    public List<Product> getBooksByPage(int page) {
        return bookDAO.getBooksByPage(page);
    }

    @Override
    public int getTotalBooks() {
        return bookDAO.getTotalBooks();
    }
}