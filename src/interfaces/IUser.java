/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.User;
import entities.UserInfo;
import javafx.collections.ObservableList;

/**
 *
 * @author Douiri Amine
 */
public interface IUser<User> {
        public boolean addUser(User user,UserInfo info);
        public ObservableList<User> getUsers();
        public boolean deleteUser(int id);
        public boolean updateUser(User user,UserInfo info, int id);
        public boolean switchStatusAccount(int user);
        public User getUserById(int id);
        public User getUserByEmail(String email);
        public boolean validate(String email, String password); 
        public UserInfo getUserInfoById(int id);



}
