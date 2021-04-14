/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.User;
import javafx.collections.ObservableList;

/**
 *
 * @author khail
 */
public interface IUser<User> {
    
     public boolean addUser(User tag);
    public ObservableList<User> getUser();
    public boolean deleteUser(int id);
    public boolean updateUser(User user, int id);
    public User getUserById(int id);
    
    
}
