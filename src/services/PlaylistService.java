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
    
    
    public void AddPlaylist(Playlist p){
        String req="insert into playlist (playlist_name) values ('"+p.getPlaylistName()+"')";
        try {
            ste=connection.createStatement();
            ste.executeUpdate(req);
            
        } catch (SQLException ex) {
            Logger.getLogger(PlaylistService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
