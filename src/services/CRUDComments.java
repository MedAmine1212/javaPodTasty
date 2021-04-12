/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import DBConnection.MyConnection;
import entities.Podcast;
import entities.PodcastComment;
import interfaces.IComments;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author khail
 */
public class CRUDComments implements IComments<PodcastComment> {

    @Override
    public boolean addComment(PodcastComment com) {
        try{
            String requete= "INSERT INTO podcast_comment (podcast_id_id, user_id_id, comment_text, comment_date)"+ "VALUES (?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
           // pst.setInt(1, t.getId());
            pst.setInt(1,com.getPodcastIdId().getId());
            pst.setInt(2,com.getUserIdId().getId());
            pst.setString(3,com.getCommentText()); 
            pst.setDate(4,java.sql.Date.valueOf(LocalDate.now()));
            //executeupdate
            pst.executeUpdate();
            return true;
            
            
        }catch(Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean deleteComment(int id) {
        
        try{
        String requete= "delete from podcast_comment where id = ?";
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
    public boolean updateComment(PodcastComment tag, int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PodcastComment getCommentById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<PodcastComment> getCommentsByPodcast(Podcast pod) {
        
         ObservableList<PodcastComment> comments = FXCollections.observableArrayList();

        try {
            String requete = "SELECT * FROM podcast_comment p WHERE p.podcast_id_id=? ";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1, pod.getId());
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                PodcastComment comment = new PodcastComment();
                comment.setId(rs.getInt("id"));
                comment.setCommentDate(rs.getDate("comment_date"));
                comment.setCommentText(rs.getString("comment_text"));
                CRUDUser cu = new CRUDUser();
                comment.setUserIdId(cu.getUserById(rs.getInt("user_id_id")));
                comment.setUserName("Test");
                comment.setPodcastIdId(pod);
                comments.add(comment);
                
        }
            return comments;
        }catch(Exception e) {
            System.out.println("__________________________________________");
            System.out.println(e);
            System.out.println("__________________________________________");
            return null;
        }
        
    }
    
}
