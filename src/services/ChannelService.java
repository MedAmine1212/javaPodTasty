/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import DBConnection.MyConnection;
import entities.Channel;
import entities.Playlist;
import entities.User;
import entities.UserInfo;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Lwiss
 */
public class ChannelService {
    private Statement ste;
    private PreparedStatement pst;
    
    private Connection connection;
    
    public ChannelService (){
        connection=MyConnection.getInstance().getCnx();
}

    public int getSize() throws SQLException{
        String query ="select * from channel;";
            
        Statement st;
        ResultSet rs;
            st=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
    ResultSet.CONCUR_READ_ONLY);
            rs=st.executeQuery(query);
            int size = 0;
try {
    rs.last();
    size = rs.getRow();
    rs.beforeFirst();
}
catch(Exception ex) {
    return 0;
}
return size;
            
   }



    public List<Playlist> getPlaylistsbyChannelId(int id) throws SQLException{
        List<Playlist> pl=new ArrayList<>();
        PreparedStatement pre = connection.prepareStatement("select * from playlist as pl where pl.channel_id_id=?;");
        pre.setInt(1, id);
        
           
         ResultSet rs = pre.executeQuery();
        while (rs.next()) {
                Playlist p = new Playlist();
                
                 p.setId(rs.getInt("id"));
                 p.setPlaylistName(rs.getString("playlist_name"));
                 p.setPlaylistDescription(rs.getString("playlist_description"));
                 p.setPlaylistCreationDate(rs.getDate("playlist_creation_date"));
                 p.setImageName(rs.getString("image_name"));
                  
                 
                pl.add(p);
        }
        return pl;
    }
    public List<Channel> getChannelList(){
            List<Channel> l=new ArrayList<>();
            try {
            String query ="select * from channel;";
            
        Statement st;
        ResultSet rs;
            st=connection.createStatement();
            rs=st.executeQuery(query);
           
            while (rs.next()) {
                Channel channel= new Channel();
                
                
                
                 channel.setId(rs.getInt("id"));
                 channel.setChannel_Name(rs.getString("channel_name"));
                 channel.setChannel_Description(rs.getString("channel_descriptiosn"));
                 channel.setChannel_CreationDate(rs.getDate("channel_creation_date"));
                 channel.setChannel_Status(rs.getInt("channel_status"));
                  
                 
                l.add(channel);
              
            }
        } catch (Exception e) {
            
        }
       return l;
    }
    
    
    
    @SuppressWarnings("unchecked")
    public ObservableList<Channel> getAllChannels(){
        ObservableList<Channel> ChannelList = FXCollections.observableArrayList();
        
        
        try {
            String query ="select * from channel;";
            
        Statement st;
        ResultSet rs;
            st=connection.createStatement();
            rs=st.executeQuery(query);
           
            while (rs.next()) {
                Channel channel= new Channel();
                
                
                
                 channel.setId(rs.getInt("id"));
                 channel.setChannel_Name(rs.getString("channel_name"));
                 channel.setChannel_Description(rs.getString("channel_description"));
                 channel.setChannel_CreationDate(rs.getDate("channel_creation_date"));
                 channel.setChannel_Status(rs.getInt("channel_status"));
                  
                 
                ChannelList.add(channel);
              
            }
        } catch (Exception e) {
            
        }
       return ChannelList;
    }
    
   public Channel findById(int idch) throws SQLException {
        Channel p=new Channel();
        PreparedStatement pre = connection.prepareStatement("select * from channel WHERE id=? ");
        pre.setInt(1, idch);
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {
            int id = rs.getInt(1);
            String channel_name = rs.getString(2);
            String channel_description = rs.getString(3);
            Date channel_creation_date = rs.getDate(4);
            int channel_status = rs.getInt(5);
           
            

            p = new Channel(id, channel_name, channel_description, channel_creation_date, channel_status);
        }
        return p;

    }
    public void addChannel(Channel c){
    
    String req;
        req = "insert into channel (channel_name,channel_description,channel_creation_date,channel_status) values (?,?,?,?)";
        try {
            pst=connection.prepareStatement(req);
            pst.setString(1,c.getChannel_Name());
            pst.setString(2,c.getChannel_Description());
            pst.setDate(3, (Date) c.getChannel_CreationDate());
            pst.setInt(4,c.getChannel_Status());
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(PlaylistService.class.getName()).log(Level.SEVERE, null, ex);
        }}

    
    
    public boolean deleteChannel(int id){
        String req= "delete from channel where id = ?";
        try {
            pst=connection.prepareStatement(req);
            pst.setInt(1, id);
             pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }      
    }
    
    public boolean updateOwnChannel(int id, Channel c){
     String req;
        req = "update channel set channel_name=?, channel_description=? where id = ?";
        try {
            pst=connection.prepareStatement(req);
            pst.setString(1,c.getChannel_Name());
            pst.setString(2,c.getChannel_Description());
            pst.setInt(3, id);
            pst.executeUpdate();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(PlaylistService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    public boolean updateChannel(int id , Channel c){
    String req;
        req = "update channel set channel_name=?, channel_description=? , channel_status=? where id = ?";
        try {
            pst=connection.prepareStatement(req);
            pst.setString(1,c.getChannel_Name());
            pst.setString(2,c.getChannel_Description());
            pst.setInt(3, c.getChannel_Status());
            pst.setInt(4, id);
            pst.executeUpdate();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(PlaylistService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    


}
