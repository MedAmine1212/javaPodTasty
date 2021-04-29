/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import DBConnection.MyConnection;
import entities.Podcast;
import entities.User;
import java.sql.PreparedStatement;
import interfaces.IFavorite;
import java.sql.ResultSet;

/**
 *
 * @author khail
 */
public class CRUDFavorite implements IFavorite{

    @Override
    public boolean addFavorite(Podcast p, User u) {
         try{
            String requete= "INSERT INTO user_podcast (user_id, podcast_id) VALUES (?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
           // pst.setInt(1, t.getId());
            pst.setInt(1,u.getId());
            pst.setInt(2,p.getId());
            //executeupdate
            pst.executeUpdate();
            return true;
            
            
        }catch(Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean removeFavorite(Podcast p, User u) {
       try{
        String requete= "delete from user_podcast where user_id = ? AND podcast_id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1,u.getId());
            pst.setInt(2,p.getId());
            pst.executeUpdate();
            return true;
        }catch(Exception e) {
            System.out.println(e);
            return false;
        }    
    }

    @Override
    public boolean getFavoriteByPodcastAnduser(Podcast p, User u) {
         try {
            String requete = "SELECT * FROM user_podcast p WHERE p.podcast_id=? AND p.user_id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1, p.getId());
            pst.setInt(2, u.getId());
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
               return true;
            }
            return false;
        }catch(Exception e) {
            System.out.println("__________________________________________");
            System.out.println(e);
            System.out.println("__________________________________________");
            return false;
        }
    }
}
  
