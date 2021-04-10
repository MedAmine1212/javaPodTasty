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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateTag(Tag tag, int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tag getTagById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                    
                if (tag.getTagStyle() == "primary") {
                    tag.setTagColor(Color.BLUE);
                    
                }else if (tag.getTagStyle() == "secondary") {
                    tag.setTagColor(Color.GRAY);
                    
                }else if (tag.getTagStyle() == "success") {
                    tag.setTagColor(Color.GREEN);
                    
                }else if (tag.getTagStyle() == "danger") {
                    tag.setTagColor(Color.RED);
                    
                }else if (tag.getTagStyle() == "warning") {
                    tag.setTagColor(Color.YELLOW);
                    
                }else if (tag.getTagStyle() == "info") {
                    tag.setTagColor(Color.CYAN);
                    
                }else{
                    tag.setTagColor(Color.BLACK);
                    
                }
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
