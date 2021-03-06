/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import DBConnection.MyConnection;
import at.favre.lib.crypto.bcrypt.BCrypt;
import entities.Channel;
import entities.User;
import entities.UserInfo;
import interfaces.IUser;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Douiri Amine
 */
public class CRUDUser implements IUser<User> {

    @Override
    public boolean validate(String email, String password) {
        try {
            String SELECT_QUERY = "SELECT * FROM user WHERE user_email = ?";
            PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(SELECT_QUERY);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), resultSet.getString("user_password"));
                if (result.verified) {
                    return true;
                } else {
                    return false;
                }

            }
            return false;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean addUser(User user, UserInfo info) {
        if (this.getUserByEmail(user.getUserEmail()) != null) {
            return false;
        } else {
            try {
                String requeteUserInfo = "INSERT INTO user_info (user_last_name,user_first_name,user_gender,user_birth_date)" + "VALUES (?,?,?,?)";
                PreparedStatement pst = MyConnection.getInstance().getCnx()
                        .prepareStatement(requeteUserInfo);
                pst.setString(1, info.getUserLastName());
                pst.setString(2, info.getUserFirstName());
                pst.setString(3, info.getUserGender());
                java.sql.Date sDate = new java.sql.Date(info.getUserBirthDate().getTime());
                pst.setDate(4, sDate);
                pst.executeUpdate();
                String requeteUser = "INSERT INTO user (user_email, user_password,is_admin,desactive_account,user_info_id_id)" + "VALUES (?,?,?,?,(select id from user_info where id = (Select MAX(id) from user_info)))";
                PreparedStatement pst1 = MyConnection.getInstance().getCnx()
                        .prepareStatement(requeteUser);
                pst1.setString(1, user.getUserEmail());
                String bcryptHashString = BCrypt.withDefaults().hashToString(12, user.getUserPassword().toCharArray());

                pst1.setString(2, bcryptHashString);
                pst1.setBoolean(3, false);
                pst1.setBoolean(4, false);
                pst1.executeUpdate();
                return true;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        }
    }

    @Override
    public ObservableList<User> getUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        try {
            String requete = "SELECT * FROM user";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            //contenaire
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserEmail(rs.getString("user_email"));
                user.setUserInfoIdId(getUserInfoById(rs.getInt("user_info_id_id")));
                user.setDesactiveAccount(rs.getBoolean("desactive_account"));
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            System.out.println(e);

            return null;
        }
    }

    @Override
    public boolean deleteUser(int id) {

        try {
            String requeteUserInfo = "delete from user_info where id = ?";
            PreparedStatement pstInfo = MyConnection.getInstance().getCnx()
                    .prepareStatement(requeteUserInfo);
            pstInfo.setString(1, "select user_info_id_id from user where id = " + id + "");
            String requete = "delete from user where id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean updateUser(User user, UserInfo info, int id) {
        if (this.getUserById(id) != null) {
            try {
                String requeteUser = "update user set user_email=? where id = ?";
                PreparedStatement pst = MyConnection.getInstance().getCnx()
                        .prepareStatement(requeteUser);
                pst.setString(1, user.getUserEmail());
                pst.setInt(2, user.getId());
                pst.executeUpdate();
                String requeteUserInfo = "update user_info set user_first_name=?,user_last_name=?,user_bio=? where id = ?";
                PreparedStatement pst1 = MyConnection.getInstance().getCnx()
                        .prepareStatement(requeteUserInfo);
                pst1.setString(1, info.getUserFirstName());
                pst1.setString(2, info.getUserLastName());
                pst1.setString(3, info.getUserBio());

                pst1.setInt(4, user.getUserInfoIdId().getId());
                pst1.executeUpdate();
                return true;

            } catch (Exception e) {
                System.out.println(e);
                return false;

            }

        } else {
            return false;
        }
    }

    @Override
    public User getUserById(int id) {
        try {
            String requete = "select * from user where id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserEmail(rs.getString("user_email"));
                user.setUserInfoIdId(getUserInfoById(rs.getInt("user_info_id_id")));
                user.setDesactiveAccount(rs.getBoolean("desactive_account"));
                user.setIsAdmin(rs.getBoolean("is_admin"));
                
               
                
               /* user.setChannelIdId((Channel) rs.getObject("channel_id_id",<Channel> type));*/
                return user;
            }
            return null;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public UserInfo getUserInfoById(int id) {
        try {
            String requete = "SELECT * FROM user_info where id = ?";
            PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(requete);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserInfo userInfo = new UserInfo();
                userInfo.setId(resultSet.getInt("id"));
                userInfo.setUserFirstName(resultSet.getString("user_first_name"));
                userInfo.setUserLastName(resultSet.getString("user_last_name"));
                userInfo.setUserGender(resultSet.getString("user_gender"));
                userInfo.setUserBirthDate(resultSet.getDate("user_birth_date"));
                userInfo.setUserImage(resultSet.getString("user_image"));
                userInfo.setUserBio(resultSet.getString("user_bio"));
                return userInfo;
            }
            return null;

        } catch (Exception e) {
            System.out.println(e);
            return null;

        }

    }

    @Override
    public boolean switchStatusAccount(int id) {

        if (this.getUserById(id) != null) {
            try {
                String requete = "update user set desactive_account=? where id = ?";
                PreparedStatement pst = MyConnection.getInstance().getCnx()
                        .prepareStatement(requete);
                if (this.getUserById(id).getDesactiveAccount() == true) {
                    pst.setBoolean(1, false);
                } else {
                    pst.setBoolean(1, true);
                }
                pst.setInt(2, id);
                pst.executeUpdate();
                return true;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        }

        return false;
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            String requete = "select * from user where user_email = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserEmail(rs.getString("user_email"));
                user.setUserInfoIdId(getUserInfoById(rs.getInt("user_info_id_id")));
                user.setDesactiveAccount(rs.getBoolean("desactive_account"));
                user.setIsAdmin(rs.getBoolean("is_admin"));
                return user;
            }
            return null;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public boolean changePassword(int id, String pwd) {
        if (this.getUserById(id) != null) {
            try {
                String requete = "update user set user_password=? where id = ?";
                PreparedStatement pst = MyConnection.getInstance().getCnx()
                        .prepareStatement(requete);
                pst.setString(1, pwd);

                pst.setInt(2, id);
                pst.executeUpdate();
                return true;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        }

        return false;

    }

    @Override
    public boolean updatePic(String pic,int id) {
            try {
                String requete = "update user_info set user_image=? where id = ?";
                PreparedStatement pst = MyConnection.getInstance().getCnx()
                        .prepareStatement(requete);
                pst.setString(1, pic);

                pst.setInt(2, id);
                pst.executeUpdate();
                return true;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
    }
}
