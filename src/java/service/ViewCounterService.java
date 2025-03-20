/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dal.viewDao.ViewCounterDAO;
import java.util.Map;
import model.book.Book;
import service.interfaces.IViewCounterService;

/**
 *
 * @author ASUS
 */
public class ViewCounterService implements IViewCounterService{
    private ViewCounterDAO viewCounterDAO = new ViewCounterDAO();

    @Override
    public Map<Book, Integer> getUserClicks(int userId) {
        return viewCounterDAO.getUserClicks(userId);
    }
}
