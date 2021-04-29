/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import DBConnection.MyConnection;
import entities.Podcast;
import entities.Podcast;
import entities.Reclamation;
import interfaces.IPodcast;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import interfaces.IPodcast;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author khail
 */
public class CRUDPodcast implements IPodcast<Podcast> {


    @Override
    public boolean addPodcast(Podcast podcast) {
        try{
            String requete=
"INSERT INTO podcast (playlist_id_id, podcast_name, currently_live, is_blocked,comments_allowed,podcast_description,podcast_views,currently_watching,podcast_date,podcast_source,podcast_image) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pst = MyConnection.getInstance().getCnx()
                .prepareStatement(requete);
        // pst.setInt(1, t.getId());
        pst.setInt(1,1);
        pst.setString(2,podcast.getPodcastName());
        pst.setInt(3,podcast.getCurrentlyLive());
        pst.setInt(4,podcast.getIsBlocked());
        pst.setInt(5,podcast.getCommentsAllowed());
        pst.setString(6,podcast.getPodcastDescription());
        pst.setInt(6,0);
        pst.setInt(7,0);
        pst.setInt(8,0);
        pst.setDate(9,java.sql.Date.valueOf(LocalDate.now()));
        pst.setString(10,podcast.getPodcastSource());
        pst.setString(11,podcast.getPodcastImage());
        //executeupdate
        pst.execute();
        }
        catch(Exception e ) {
            System.out.println("error begin");
            e.printStackTrace();
            System.out.println("error end ");

            return false;
        }
        return true;
    }

    @Override
    public List<Podcast> getAll()  {
        String sql = "select * from podcast";
        ArrayList<Podcast> podcasts=new ArrayList();
        try {

            PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(sql);

            ResultSet result = statement.executeQuery();
            entities.Podcast p = null;

            while (result.next()) {
                p = new entities.Podcast(); // Nouvelle instance de personne

                p.setId(result.getInt(1));
              //  p.setPlaylistIdId( result.getInt(2) );
                p.setPodcastName(result.getString(3));
                p.setCurrentlyLive(result.getInt(4));
                p.setIsBlocked(result.getInt(5));
                p.setCommentsAllowed(result.getInt(6));
                p.setPodcastDescription(result.getString(7));
                p.setPodcastImage(result.getString(8));
                p.setPodcastViews(result.getInt(9));
                p.setCurrentlyWatching(result.getInt(10));
                p.setPodcastDate(result.getDate(11));
                p.setPodcastSource(result.getString(12));
                // Autres champs

                podcasts.add( p ); // Ajout à la collection
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        System.out.println("crud podcast : "+podcasts.size());
        return podcasts;
    }

    @Override
    public boolean deletePodcast(int id) throws SQLException {

        String requete="delete from podcast where id =?";
       PreparedStatement pst = MyConnection.getInstance().getCnx()
                .prepareStatement(requete);
        pst.setInt(1,id);
        pst.executeQuery();

    return true;
    }

    @Override
    public boolean updatePodcast(Podcast podcast, int id) {
        try{
        String requete=
"update podcast set playlist_id_id=?, podcast_name=?, currently_live=?, is_blocked=?,podcasts_allowed=?,podcast_description=?,podcast_views=?,currently_watching=?,podcast_date=?,podcast_source=?,podcast_image=? where id=?";  ;

        PreparedStatement pst = MyConnection.getInstance().getCnx()
                .prepareStatement(requete);
        // pst.setInt(1, t.getId());
        pst.setInt(1,1);
        pst.setString(2,podcast.getPodcastName());
        pst.setInt(3,podcast.getCurrentlyLive());
        pst.setInt(4,podcast.getIsBlocked());
        pst.setInt(5,podcast.getCommentsAllowed());
        pst.setString(6,podcast.getPodcastDescription());
        pst.setInt(6,0);
        pst.setInt(7,0);
        pst.setInt(8,0);
        pst.setDate(9,java.sql.Date.valueOf(LocalDate.now()));
        pst.setString(10,podcast.getPodcastSource());
        pst.setString(11,podcast.getPodcastImage());
        pst.setInt(12,id);
        //executeupdate
        pst.execute();
    }
        catch(Exception e ) {
        System.out.println("error begin");
        e.printStackTrace();
        System.out.println("error end ");

        return false;
    }
        return true;
}


    @Override
    public Podcast getPodcastById(int id) {

        try {
            String requete = "SELECT * FROM podcast p WHERE p.id=? ";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                entities.Podcast podcast = new entities.Podcast();
                podcast.setPodcastName(rs.getString("podcast_name"));
                podcast.setIsBlocked(rs.getInt("is_blocked"));
                podcast.setCurrentlyWatching(rs.getInt("currently_watching"));
                podcast.setCurrentlyLive(rs.getInt("currently_live"));
                podcast.setCommentsAllowed(rs.getInt("comments_allowed"));
                podcast.setPodcastDescription(rs.getString("podcast_description"));
                podcast.setPodcastViews(rs.getInt("podcast_views"));
                podcast.setPodcastSource(rs.getString("podcast_source"));
                podcast.setPodcastImage(rs.getString("podcast_image"));
                podcast.setPodcastDate(rs.getDate("podcast_date"));

                return podcast;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public boolean block(Reclamation reclamation) {
        try {
            String requete =
                    "update podcast set is_blocked=1 where id=?";
            ;

            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            // pst.setInt(1, t.getId());
            System.out.println("from block "+reclamation.getPodcastIdId());
            pst.setInt(1, reclamation.getPodcastIdId());
            pst.executeUpdate();
        }catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}