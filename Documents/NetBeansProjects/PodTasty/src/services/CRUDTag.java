/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import DBConnection.MyConnection;
import entities.Podcast;
import entities.Tag;
import interfaces.ITag;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;

/**
 *
 * @author MedAmine
 */
public class CRUDTag implements ITag<Tag> {
    

    @Override
    public boolean addTag(Tag tag) {
        try{
            String requete= "INSERT INTO tag (name, tag_style)"+ "VALUES (?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
           // pst.setInt(1, t.getId());
            pst.setString(1,tag.getName());
            pst.setString(2,tag.getTagStyle());
            //executeupdate
            pst.executeUpdate();
            return true;
            
            
        }catch(Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean deleteTag(int id) {
        try{
        String requete= "delete from tag where id = ?";
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
    public boolean updateTag(Tag tag, int id) {
        if(getTagById(id) != null) {
            try{
        String requete= "update tag set name=?, tag_style=? where id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setString(1, tag.getName());
            pst.setString(2, tag.getTagStyle());
            pst.setInt(3, id);
            pst.executeUpdate();
            return true;
        }catch(Exception e) {
            System.out.println(e);
            return false;
        } 
        } else {
            return  false;
        }
    }

    @Override
    public Tag getTagById(int id) {
        try{
        String requete= "select * from tag where id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet res = pst.executeQuery();
            while(res.next()) { 
            Tag tag = new Tag();
            tag.setId(res.getInt("id"));
            tag.setName(res.getString("name"));
            tag.setTagStyle(res.getString("tag_style"));
            return tag;
            }
            return null;
        }catch(Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public ObservableList<Tag> getTagsByPodcast(Podcast pod) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<Tag> getTags() {
       ObservableList<Tag> tags = FXCollections.observableArrayList();

        try {
            String requete = "SELECT * FROM tag";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            //contenaire
            ResultSet rs =  st.executeQuery(requete);
            while(rs.next()){
                Tag tag = new Tag();
                tag.setId(rs.getInt("id"));
                tag.setName(rs.getString("name"));
                tag.setTagStyle(rs.getString("tag_style"));
               
                BufferedImage image = ImageIO.read(new File("src/images/"+tag.getTagStyle()+".png"));
                WritableImage img = SwingFXUtils.toFXImage(image, null);
                ImageView iv = new ImageView();
                iv.setImage(img);
                tag.setTagColor(iv);
                    
          
                tags.add(tag);
                
        }
            return tags;
        }catch(Exception e) {
            System.out.println("__________________________________________");
            System.out.println(e);
            System.out.println("__________________________________________");
            return null;
        }
    }
    
}
