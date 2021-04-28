/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;


import DBConnection.MyConnection;
import entities.Playlist;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Lwiss
 */
public class PlaylistService {
    private Statement ste;
    private PreparedStatement pst;
    
    private Connection connection;
    
    public PlaylistService (){
        connection=MyConnection.getInstance().getCnx();
    }
    
    
    
    public void AddOwnPlaylist(int id,Playlist p){
        String req;
        req = "insert into playlist (playlist_name,playlist_description,playlist_creation_date,channel_id_id,image_name) values (?,?,?,?,?)";
        try {
            pst=connection.prepareStatement(req);
            pst.setString(1,p.getPlaylistName());
            pst.setString(2,p.getPlaylistDescription());
            pst.setDate(3, (Date) p.getPlaylistCreationDate());
            pst.setInt(4, id);
            pst.setString(5, p.getImageName());
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(PlaylistService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void AddPlaylist(Playlist p){
        String req;
        req = "insert into playlist (playlist_name,playlist_description,playlist_creation_date) values (?,?,?)";
        try {
            pst=connection.prepareStatement(req);
            pst.setString(1,p.getPlaylistName());
            pst.setString(2,p.getPlaylistDescription());
            pst.setDate(3, (Date) p.getPlaylistCreationDate());
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(PlaylistService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean updatePlaylist(int id, Playlist p){
    String req;
        req = "update playlist set playlist_name=?, playlist_description=? where id = ?";
        try {
            pst=connection.prepareStatement(req);
            pst.setString(1,p.getPlaylistName());
            pst.setString(2,p.getPlaylistDescription());
            pst.setInt(3, id);
            pst.executeUpdate();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(PlaylistService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    
    
    
    public boolean deletePlaylist(int id){
        String req= "delete from playlist where id = ?";
        try {
            pst=connection.prepareStatement(req);
            pst.setInt(1, id);
             pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }      
    }
    
    
    
    
    public ObservableList<Playlist> getAllPlaylists(){
        ObservableList<Playlist> PlaylistList = FXCollections.observableArrayList();
        String query ="SELECT * FROM playlist";
        Statement st;
        ResultSet rs;
        
        try {
            st=connection.createStatement();
            rs=st.executeQuery(query);
            Playlist playlist;
            while (rs.next()) {
                playlist= new Playlist(rs.getInt("id"),rs.getString("playlist_name"),rs.getString("playlist_description"),rs.getDate("playlist_creation_date"));
            
                
                PlaylistList.add(playlist);
            }
        } catch (Exception e) {
            
        }
       return PlaylistList;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
