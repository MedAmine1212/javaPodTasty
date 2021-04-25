/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import DBConnection.MyConnection;
import entities.User;
import entities.UserInfo;
import interfaces.IUser;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.ObservableList;

/**
 *
 * @author khail
 */
public class CRUDUser implements IUser<User>{

    @Override
    public boolean addUser(User tag) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<User> getUser() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteUser(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateUser(User user, int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User getUserById(int id) {
         try{
        String requete= "select * from user where id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet res = pst.executeQuery();
            while(res.next()) { 
            User user = new User();
            user.setId(res.getInt("id"));
            user.setUserInfoIdId(geUserInfoById(user.getId()));
            return user;
            }
            return null;
        }catch(Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public UserInfo geUserInfoById(int id) {
          try{
        String requete= "select * from user_info where id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet res = pst.executeQuery();
            while(res.next()) { 
            UserInfo user = new UserInfo();
            user.setId(res.getInt("id"));
            user.setUserFirstName(res.getString("user_first_name"));
            user.setUserLastName(res.getString("user_last_name"));
            return user;
            }
            return null;
        }catch(Exception e) {
            System.out.println(e);
            return null;
        }
    }
    
}
