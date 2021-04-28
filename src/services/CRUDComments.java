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
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
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
            String requete= "INSERT INTO podcast_comment (podcast_id_id, user_id_id, comment_text, comment_date) VALUES (?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
           // pst.setInt(1, t.getId());
            pst.setInt(1,com.getPodcastIdId().getId());
            pst.setInt(2,com.getUserIdId().getId());
            pst.setString(3,com.getCommentText()); 
            pst.setDate(4,java.sql.Date.valueOf(LocalDate.now()));
            //executeupdate
            pst.executeUpdate();
            String rq = "Select MAX(id) from podcast_comment";
            PreparedStatement st = MyConnection.getInstance().getCnx()
                    .prepareStatement(rq);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                URL url = new URL("http://127.0.0.1:8000/callMercure/comments/"+rs.getInt(1)+"/"+com.getPodcastIdId().getId());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                int status = con.getResponseCode();
                System.out.println(status);
            }
            
            
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
    public boolean updateComment(String commentText, int id) {
 try{
        String requete= "update podcast_comment set comment_text=? where id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setString(1,commentText);
            pst.setInt(2,id); 
            //executeupdate
            pst.executeUpdate();
            return true;
            
            
        }catch(Exception e) {
            System.out.println(e);
            return false;
        }    }

    @Override
    public PodcastComment getCommentById(int id) {
        try {
            String requete = "SELECT * FROM podcast_comment p WHERE p.id=? ";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                PodcastComment comment = new PodcastComment();
                comment.setId(rs.getInt("id"));
                comment.setCommentDate(rs.getDate("comment_date"));
                comment.setCommentText(rs.getString("comment_text"));
                CRUDUser cu = new CRUDUser();
                comment.setUserIdId(cu.getUserById(rs.getInt("user_id_id")));
                comment.setUserName(comment.getUserIdId().getUserInfoIdId().getUserFirstName()+" "+comment.getUserIdId().getUserInfoIdId().getUserLastName());
                return comment;
                
        }
            return null;
        }catch(Exception e) {
            System.out.println("__________________________________________");
            System.out.println(e);
            System.out.println("__________________________________________");
            return null;
        }
        

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
                comment.setUserName(comment.getUserIdId().getUserInfoIdId().getUserFirstName()+" "+comment.getUserIdId().getUserInfoIdId().getUserLastName());
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

    @Override
    public ObservableList<PodcastComment> getCommentsByComText(Podcast pod,String text) {
         ObservableList<PodcastComment> comments = FXCollections.observableArrayList();

        try {
            String requete = "SELECT * FROM podcast_comment p WHERE p.podcast_id_id=? AND UPPER(comment_text) LIKE  UPPER('%"+text+"%')";
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

    @Override
    public ObservableList<Podcast> getPodcastByPlaylist(int id, int podId) {
        
        ObservableList<Podcast> podcasts = FXCollections.observableArrayList();

        try {
            String requete = "SELECT * FROM podcast p WHERE p.playlist_id_id=? AND p.id <> ? AND currently_live=0 AND is_blocked=0";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1, id);
            pst.setInt(2, podId);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Podcast pod = new Podcast();
                pod.setId(rs.getInt("id"));
                pod.setCommentsAllowed(rs.getInt("comments_allowed"));
                pod.setCurrentlyLive(rs.getInt("currently_live"));
                pod.setCurrentlyWatching(rs.getInt("currently_watching"));
                pod.setIsBlocked(rs.getInt("is_blocked"));
                pod.setPodcastDate(rs.getDate("podcast_date"));
                pod.setPodcastImage(rs.getString("podcast_image"));
                pod.setPodcastSource(rs.getString("podcast_source"));
                pod.setPodcastViews(rs.getInt("podcast_views"));
                pod.setPodcastName(rs.getString("podcast_name"));
                pod.setPodcastDescription(rs.getString("podcast_description"));
                podcasts.add(pod);
                
        }
            return podcasts;
        }catch(Exception e) {
            System.out.println("__________________________________________");
            System.out.println(e);
            System.out.println("__________________________________________");
            return null;
        }
    }

    @Override
    public int getCommentsAllowedForPod(Podcast pod) {
        try {
            String requete = "SELECT comments_allowed FROM podcast p WHERE p.id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1, pod.getId());
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
               return rs.getInt("comments_allowed");
            }
            return 0;
        }catch(Exception e) {
            System.out.println("__________________________________________");
            System.out.println(e);
            System.out.println("__________________________________________");
            return 0;
        }
}
    
    
    
           @Override
    public ObservableList<Podcast> getPodcasts() {
        
        ObservableList<Podcast> pods = FXCollections.observableArrayList();

        try {
            String requete = "SELECT * FROM podcast p WHERE currently_live=0 AND is_blocked=0";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Podcast pod = new Podcast();
                pod.setId(rs.getInt("id"));
                pod.setCommentsAllowed(rs.getInt("comments_allowed"));
                pod.setCurrentlyLive(rs.getInt("currently_live"));
                pod.setCurrentlyWatching(rs.getInt("currently_watching"));
                pod.setIsBlocked(rs.getInt("is_blocked"));
                pod.setPodcastDate(rs.getDate("podcast_date"));
                pod.setPodcastImage(rs.getString("podcast_image"));
                pod.setPodcastSource(rs.getString("podcast_source"));
                pod.setPodcastViews(rs.getInt("podcast_views"));
                pod.setPodcastName(rs.getString("podcast_name"));
                pod.setPodcastDescription(rs.getString("podcast_description"));
                pods.add(pod);
                
        }
            
            
            return pods;
        }catch(Exception e) {
            System.out.println("__________________________________________");
            System.out.println(e);
            System.out.println("__________________________________________");
            return null;
        }
    }

    @Override
    public ObservableList<Podcast> getPodcastsByTag(int id) {
        ObservableList<Podcast> pods = FXCollections.observableArrayList();

        try {
            
            String req = "SELECT podcast_id from tag_podcast WHERE tag_id = ?";
             PreparedStatement ps = MyConnection.getInstance().getCnx()
                    .prepareStatement(req);
            
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            while(res.next()){
                
            String requete = "SELECT * FROM podcast p WHERE id = ? AND currently_live=0 AND is_blocked=0 ";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1, res.getInt(1));
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Podcast pod = new Podcast();
                pod.setId(rs.getInt("id"));
                pod.setCommentsAllowed(rs.getInt("comments_allowed"));
                pod.setCurrentlyLive(rs.getInt("currently_live"));
                pod.setCurrentlyWatching(rs.getInt("currently_watching"));
                pod.setIsBlocked(rs.getInt("is_blocked"));
                pod.setPodcastDate(rs.getDate("podcast_date"));
                pod.setPodcastImage(rs.getString("podcast_image"));
                pod.setPodcastSource(rs.getString("podcast_source"));
                pod.setPodcastViews(rs.getInt("podcast_views"));
                pod.setPodcastName(rs.getString("podcast_name"));
                pod.setPodcastDescription(rs.getString("podcast_description"));
                pods.add(pod);
                
        }
            
            }
            return pods;
        }catch(Exception e) {
            System.out.println("__________________________________________");
            System.out.println(e);
            System.out.println("__________________________________________");
            return null;
        }
    }

    @Override
    public boolean changeCommentingStatus(int id, int status) {
        try{
         String requete= "update podcast set comments_allowed=? where id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1,status);
            pst.setInt(2,id); 
            //executeupdate
            pst.executeUpdate();
            return true;
            
            
        }catch(Exception e) {
            System.out.println(e);
            return false;
        }   
    }
    

}
