/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import DBConnection.MyConnection;
import entities.Podcast;
import entities.PodcastReview;
import entities.User;
import interfaces.IReview;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    @Override
    public ObservableList<PodcastReview> getReviewsByPodcast(Podcast pod) {
        
         ObservableList<PodcastReview> reviews = FXCollections.observableArrayList();
        try {
            String requete = "SELECT * FROM podcast_review p WHERE p.podcast_id_id=? ";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete); 
            pst.setInt(1, pod.getId());
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                PodcastReview review = new PodcastReview();
                review.setId(rs.getInt("id"));
                review.setRating(rs.getFloat("rating"));
                CRUDUser cu = new CRUDUser();
                review.setUserIdId(cu.getUserById(rs.getInt("user_id_id")));
                review.setPodcastIdId(pod);
                reviews.add(review);
                
        }
            return reviews;
        }catch(Exception e) {
            System.out.println("__________________________________________");
            System.out.println(e);
            System.out.println("__________________________________________");
            return null;
        }
        
    }

    @Override
    public PodcastReview getReviewByUserAndPodcast(User user, Podcast pod) {
        try {
            String requete = "SELECT * FROM podcast_review p WHERE p.podcast_id_id=? AND p.user_id_id=? ";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete); 
            pst.setInt(1, pod.getId()); 
            pst.setInt(2, user.getId());
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                PodcastReview review = new PodcastReview();
                review.setId(rs.getInt("id"));
                review.setRating(rs.getFloat("rating"));
                review.setUserIdId(user);
                review.setPodcastIdId(pod);
                return review;
        }
        }catch(Exception e) {
            System.out.println("__________________________________________");
            System.out.println(e);
            System.out.println("__________________________________________");
            return null;
        }
        return null;
    }
    
}
