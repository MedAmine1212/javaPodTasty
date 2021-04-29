/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import DBConnection.MyConnection;
import entities.Post;
import entities.User;
import entities.UserInfo;
import interfaces.IPost;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import podtasty.PodTasty;

/**
 *
 * @author Issam
 */
public class CRUDPost implements IPost<Post> {

    @Override
    public boolean addPost(Post post) {

        try {
            String requete = "INSERT INTO post (user_id,text, created_at,privacy)" + "VALUES (?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);

// ya amiiiiiiiine
            
            
    
            pst.setInt(1, PodTasty.getUserInfo().getId());
            pst.setString(2, post.getText());
            java.util.Date utilDate = new java.util.Date();
            
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
           
           
           
            //sqlDate.setMinutes(utilDate.getMinutes());
            //sqlDate.setDate(utilDate.getDate());
            //System.out.println(utilDate.getHours()+"aaaaaaaaaaaaa");
            pst.setDate(3, sqlDate);
            pst.setInt(4, 0);
            //executeupdate
            pst.executeUpdate();
            System.out.println("c'est bon ya m3alem");
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }

    @Override
    public ObservableList<Post> getPosts() {
       
        
            ObservableList<Post> posts = FXCollections.observableArrayList();

        try {
            String requete = "SELECT * FROM post";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            //contenaire
            ResultSet rs =  st.executeQuery(requete);
            while(rs.next()){
                Post post = new Post();
                post.setId(rs.getInt("id"));
                post.setText(rs.getString("text"));
                post.setUserId(rs.getInt("user_id"));
               
             
          
                posts.add(post);
                
        }
            return posts;
        }catch(Exception e) {
            System.out.println("__________________________________________");
            System.out.println(e);
            System.out.println("__________________________________________");
            return null;
        }
        
    }

    @Override
    public boolean deletePost(int id) {
            try{
        String requete= "delete from post where id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            return true;
        }catch(Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean updatePost(Post post, int id) {
        if(getPostById(id) != null) {
            try{
        String requete= "update post set text=? where id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setString(1, post.getText());   
            pst.setInt(2,id);   
            pst.executeUpdate();
            return true;
        }catch(Exception e) {
            System.out.println(e);
            return false;
        } 
        } else {
            return  false;
        }
    }

    @Override
    public Post getPostById(int id) {
         try{
        String requete= "select * from post where id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet res = pst.executeQuery();
            while(res.next()) { 
            Post post = new Post();
            post.setId(res.getInt("id"));
            post.setText(res.getString("text"));
            post.setCreatedAt(res.getDate("created_at"));
            post.setUserId(res.getInt("user_id"));
            return post;
            }
            return null;
        }catch(Exception e) {
            System.out.println(e);
            return null;
        }
    }

    
    
    
    
    
    
    
    @Override
    public ObservableList<Post> getPostByOwner(Post post) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<Post> getPostByText(Post post) {
        
          ObservableList<Post> posts = FXCollections.observableArrayList();

        try {
            String requete = "SELECT * FROM post where text LIKE '%"+post.getText()+"'  ";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            //contenaire
            ResultSet rs =  st.executeQuery(requete);
            while(rs.next()){
                Post post1 = new Post();
                post.setId(rs.getInt("id"));
                post.setText(rs.getString("text"));
                post.setUserId(rs.getInt("user_id"));
               
             
          
                posts.add(post);
                
        }
            return posts;
        }catch(Exception e) {
            System.out.println("__________________________________________");
            System.out.println(e);
            System.out.println("__________________________________________");
            return null;
        }
      
    }
    
    
        public UserInfo getOwner(int id) {
         try{
        String requete= "select * from user_info where id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet res = pst.executeQuery();
            while(res.next()) { 
            
            
            UserInfo user = new UserInfo();
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
