/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import DBConnection.MyConnection;
import entities.PodcastReview;
import interfaces.IReview;
import java.sql.PreparedStatement;

/**
 *
 * @author khail
 */
public class CRUDReview implements IReview{

    @Override
    public boolean addReview(PodcastReview review) {
        try{
            String requete= "INSERT INTO podcast_review (podcast_id_id, user_id_id, rating)"+ "VALUES (?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
           // pst.setInt(1, t.getId());
            pst.setInt(1,review.getPodcastIdId().getId());
            pst.setInt(2,review.getUserIdId().getId());
            pst.setFloat(3,review.getRating()); 
            //executeupdate
            pst.executeUpdate();
            return true;
            
            
        }catch(Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean deleteReview(int id) {
 try{
        String requete= "delete from podcast_review where id = ?";
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
    
}
