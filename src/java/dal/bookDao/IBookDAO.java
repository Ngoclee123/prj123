/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.bookDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import java.sql.SQLException;
import java.util.ArrayList;
import model.book.Book;

/**
 *
 * @author ASUS
 */
public interface IBookDAO {
public void insertBook (Book pro) throws SQLException;

public Book selectBook (int id);

public List<Book> selectAllBooks();

public boolean deleteBook (int id) throws SQLException;

public boolean updateBook (Book pro) throws SQLException;

public List<Book> searchBooks(String keyword) ;
    
public List<Book> searchBooks(String keyword ,String sortType, Integer categoryId,Double minPrice, Double maxPrice) ;
List<Book> getRelatedBooks(int categoryId, int currentBookId); // Thêm khai báo này
}