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
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Douiri Amine
 */
public class CRUDUser implements IUser<User>{
    @Override
    public boolean validate(String email, String password) {
        try{
       String SELECT_QUERY = "SELECT * FROM user WHERE user_email = ? and user_password = ?";
       PreparedStatement preparedStatement  = MyConnection.getInstance().getCnx().prepareStatement(SELECT_QUERY);
       preparedStatement.setString(1, email);
       preparedStatement.setString(2, password);
       ResultSet resultSet = preparedStatement.executeQuery();
       if (resultSet.next()) {
                return true;
            }
       else {
           return false;
       }
            
        }catch(Exception e) {
            System.out.println(e);
            return false;
        }
    }
    @Override
    public boolean addUser(User user, UserInfo info) {
          try{
            String requeteUserInfo = "INSERT INTO user_info (user_last_name,user_first_name,user_gender,user_birth_date)"+"VALUES (?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requeteUserInfo);
            pst.setString(1,info.getUserLastName());
            pst.setString(2,info.getUserFirstName());
            pst.setString(3,info.getUserGender());
            java.sql.Date sDate = new java.sql.Date(info.getUserBirthDate().getTime());
            pst.setDate(4,sDate);
            pst.executeUpdate();
            String requeteUser= "INSERT INTO user (user_email, user_password,is_admin,desactive_account,user_info_id_id)"+ "VALUES (?,?,?,?,(select id from user_info where id = (Select MAX(id) from user_info)))";
            PreparedStatement pst1 = MyConnection.getInstance().getCnx()
                    .prepareStatement(requeteUser);
            pst1.setString(1, user.getUserEmail());
            pst1.setString(2, user.getUserPassword());
            pst1.setBoolean(3, false);
            pst1.setBoolean(4, false);            
            pst1.executeUpdate();
            return true;   
          }
          catch(Exception e) {
            System.out.println(e);
            return false;
             }
    }

    @Override
    public ObservableList<User> getUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
      try {
            String requete = "SELECT * FROM user";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            //contenaire
            ResultSet rs =  st.executeQuery(requete);
    
      return users;
      }
        catch(Exception e){
                        System.out.println(e);

        return null;
        }
    }

    @Override
    public boolean deleteUser(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateUser(User user, UserInfo info, int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User getUserById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
