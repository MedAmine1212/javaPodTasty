/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import DBConnection.MyConnection;
import entities.Podcast;
import interfaces.IPod;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author LENOVO
 */
public class CRUDPodcast implements IPod<Podcast> {
    
    
    @Override
    public void addPodcast(Podcast podcast){
        try{
            String rq = "INSERT INTO podcast(id, podcast_name, podcast_description, podcast_date, podcast_image ,podcast_source)"+ "VALUES (?,?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
             pst.setInt(1,podcast.getId());
             pst.setString(2,podcast.getPodcastName());
             pst.setString(3,podcast.getPodcastDescription());
             pst.setDate(4, (Date) podcast.getPodcastDate());
             pst.setString(5, podcast.getPodcastImage());
             pst.setString(6, podcast.getPodcastSource());
             
             pst.executeUpdate();            
             
             System.out.println("Podcast ajouté");
           
        }catch(SQLException ex) {
            ex.printStackTrace();
            System.out.println("error");

        }
    }
    
    
    @Override
    public void deletePodcast(int id){
        try{
        String requete= "delete from podcast where id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("podcast supprimé");
        }
        catch(SQLException e) {
            System.out.println("erreur");

        }
    }
    
    
    @Override
    public Podcast getPodcastBYId(int id){
        try{
        String requete= "select * from podcast where id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet res = pst.executeQuery();
            
            
            while(res.next()) 
            { 
            Podcast pod = new Podcast();
            pod.setId(res.getInt("id"));
            pod.setPodcastName(res.getString("podcast_name"));
            pod.setPodcastDescription(res.getString("podcast_description"));
            pod.setPodcastDate(res.getDate("podcast_date"));
            pod.setPodcastImage(res.getString("podcast_image"));
            pod.setPodcastSource(res.getString("podcast_source"));
            return pod;
            }
          
        }catch(SQLException e) {
              e.printStackTrace();
            return null;

        }
        return null;
       
    }
}
